package com.aron.framedemo;

import android.app.Application;
import android.content.Context;

import com.aron.framedemo.common.Constant;
import com.aron.framedemo.util.MyUtils;


/**
 * Created by Aron on 2016/12/18.
 */
public class BaseApplication extends Application {

    private static Context sAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = this;
    }

    public static Context getAppContext() {
        return sAppContext;
    }
    public static boolean isHavePhoto() {
        return MyUtils.getSharedPreferences().getBoolean(Constant.SHOW_NEWS_PHOTO, true);
    }
}
