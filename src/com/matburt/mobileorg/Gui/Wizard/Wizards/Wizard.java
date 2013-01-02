package com.matburt.mobileorg.Gui.Wizard.Wizards;

import com.matburt.mobileorg.R;
import com.matburt.mobileorg.Gui.Wizard.WizardView;
import com.matburt.mobileorg.Settings.SettingsActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public abstract class Wizard {

	protected Context context;
	protected WizardView wizardView;

	protected UIHandler uiHandler;
	protected ProgressDialog progress;

	private int sourceNum = -1;
	
	public Wizard(WizardView wizardView, Context context, int source) {
		this.context = context;
		this.wizardView = wizardView;
		this.sourceNum = source;

		progress = new ProgressDialog(context);
		progress.setMessage(context.getString(R.string.please_wait));
		progress.setTitle(context.getString(R.string.signing_in));

		wizardView.removePagesAfter(1);
		setupFirstPage();
	}
	
	public void setupDoneButton(View view) {
		Button doneButton = (Button) view.findViewById(R.id.wizard_done_button);
		doneButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				saveSettings();
				((Activity) context).finish();
			}
		});
	}

	public abstract void setupFirstPage();
	
	public void refresh() {
	}

	public final class UIHandler extends Handler {
		public static final int DISPLAY_UI_TOAST = 0;

		public UIHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DISPLAY_UI_TOAST: {
				Toast t = Toast.makeText(context, (String) msg.obj,
						Toast.LENGTH_LONG);
				progress.dismiss();
				t.show();
			}
			default:
				break;
			}
		}
	}

	public void showToastRemote(String message) {
		Message msg = uiHandler.obtainMessage(UIHandler.DISPLAY_UI_TOAST);
		msg.obj = message;
		uiHandler.sendMessage(msg);
	}


	// for use in saveSettings
	protected final SharedPreferences getPreferences() {
		return SettingsActivity.getSharedPreferences(context, sourceNum);
	}
	
	public abstract void saveSettings();

	public enum TYPE {
		WebDAV, Dropbox, Ubuntu, SDCard, SSH, Null
	};

	public static Wizard getWizard(TYPE type, int source, WizardView wizardView,
			Context context) {
		Wizard wizard;

		switch (type) {
		case WebDAV:
			wizard = new WebDAVWizard(wizardView, context, source);
			break;

		case Dropbox:
			wizard = new DropboxWizard(wizardView, context, source);
			break;

		case Ubuntu:
			wizard = new UbuntuOneWizard(wizardView, context, source);
			break;

		case SDCard:
			wizard = new SDCardWizard(wizardView, context, source);
			break;

		case SSH:
			wizard = new SSHWizard(wizardView, context, source);
			break;

		case Null:
			wizard = new NullWizard(wizardView, context, source);
			break;

		default:
			wizard = new NullWizard(wizardView, context, source);
			break;
		}

		return wizard;
	}
}
