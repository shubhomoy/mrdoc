package com.bitslate.mrdoc.MrDocUtilities;

import android.content.Context;
import android.app.Application;
/**
 * Created by vellapanti on 5/12/15.
 */
public class MyApplication extends Application{
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }
}
