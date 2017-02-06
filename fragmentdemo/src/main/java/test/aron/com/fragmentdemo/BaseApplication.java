package test.aron.com.fragmentdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by Aron on 2016/12/28.
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
}
