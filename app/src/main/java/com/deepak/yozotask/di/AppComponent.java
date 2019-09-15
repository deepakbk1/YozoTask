package com.deepak.yozotask.di;

import com.deepak.yozotask.ui.home.HomeFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, UtilsModule.class})

public interface AppComponent {

    void doInjection(HomeFragment homeFragment);


}
