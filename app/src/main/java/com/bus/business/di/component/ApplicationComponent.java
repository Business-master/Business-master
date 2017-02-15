package com.bus.business.di.component;

import android.content.Context;

import com.bus.business.di.module.ApplicationModule;
import com.bus.business.di.scope.ContextLife;
import com.bus.business.di.scope.PerApp;

import dagger.Component;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();
}