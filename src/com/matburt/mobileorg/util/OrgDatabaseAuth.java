package com.matburt.mobileorg.util;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.matburt.mobileorg.R;

public class OrgDatabaseAuth extends Activity implements OnClickListener {
	private EditText dbPassword;
	private Button dbSubmit;

	static String sqlPassword = null;
	
	public static String getDatabasePassword() {
		return sqlPassword;
	}
	
	public static void forgetDatabasePassword() {
		sqlPassword = null;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.androidauth);
        
        //dbInfo = (TextView)findViewById(R.id.dbCurrentToken);
        EditText dbLogin = (EditText)findViewById(R.id.dbAuthLogin);
        dbLogin.setVisibility(View.GONE);
        
        dbPassword = (EditText)findViewById(R.id.dbAuthPassword);
        dbSubmit = (Button)findViewById(R.id.dbAuthSubmit);
        dbSubmit.setOnClickListener(this);
    }

    public void onClick(View v) {
    	if (v == dbSubmit) {
    		
    		String pass = dbPassword.getText().toString();
 	        if (pass.length() < 6) {
 	            showToast("Error, password too short");
 	            return;
 	        }
 	        
 	        // ideally we should check here whether it is the right password
 	        
    		sqlPassword = pass;
    		this.setResult(RESULT_OK);
    		finish();
    	}
    }

	private void showToast(String msg) {
		Toast error = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        error.show();
	}	
}