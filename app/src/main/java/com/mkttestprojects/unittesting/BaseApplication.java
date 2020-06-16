package com.mkttestprojects.unittesting;

import com.mkttestprojects.unittesting.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class BaseApplication extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
//        return null;
        return DaggerAppComponent.builder().application(this).build();
    }
}
