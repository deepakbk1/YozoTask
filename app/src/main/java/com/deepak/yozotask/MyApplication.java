package com.deepak.yozotask;

import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.deepak.yozotask.di.AppComponent;
import com.deepak.yozotask.di.DaggerAppComponent;
import com.deepak.yozotask.di.UtilsModule;
import com.deepak.yozotask.utils.SharedPreferenceHelper;

public class MyApplication extends MultiDexApplication {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private AppComponent appComponent;
    private Context context;

    @Override
    public void onCreate() {

        super.onCreate();

        context = MyApplication.this;
        SharedPreferenceHelper.init(this);
        appComponent = DaggerAppComponent.builder().utilsModule(new UtilsModule()).build();

    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

}
