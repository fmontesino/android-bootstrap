package me.fmon.android;

import android.app.Activity;
import android.app.Application;

import org.androidannotations.annotations.EApplication;

@EApplication
public class MyApplication extends Application {

    /* CURRENT ACTIVITY */
    Activity currentActivity;

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }
}
