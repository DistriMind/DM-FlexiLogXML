package com.distrimind.flexilogxml.android;

import android.app.Application;

public class MyApplicationTests extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ContextProvider.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ContextProvider.applicationClosed();
    }
}
