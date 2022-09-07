package com.example.formapp.app;

import android.app.Application;

public class AppContext extends Application {
    private static AppContext sInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static synchronized AppContext getInstance() {
        return sInstance;
    }
}
