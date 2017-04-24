package com.ristone.businessasso.di.component;

import android.content.Context;

import com.ristone.businessasso.di.module.ApplicationModule;
import com.ristone.businessasso.di.scope.ContextLife;
import com.ristone.businessasso.di.scope.PerApp;

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