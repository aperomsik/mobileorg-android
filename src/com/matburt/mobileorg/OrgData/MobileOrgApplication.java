package com.matburt.mobileorg.OrgData;

import net.sqlcipher.database.SQLiteDatabase;
import android.app.Application;

import com.matburt.mobileorg.Services.SyncService;

public class MobileOrgApplication extends Application {
    
    @Override
    public void onCreate() {
    	SQLiteDatabase.loadLibs(this);
		SyncService.startAlarm(getApplicationContext());
    }
}
