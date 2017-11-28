package com.yhb.myyouhui.utils;

import android.app.Application;

import com.pgyersdk.crash.PgyCrashManager;

/**
 * Created by Administrator on 2017/11/28.
 */

public class PgyApplication extends Application {

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        PgyCrashManager.register(this);
    }
}