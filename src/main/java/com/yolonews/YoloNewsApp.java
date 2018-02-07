package com.yolonews;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.yolonews.auth.AuthAPI;
import com.yolonews.auth.AuthFilter;
import com.yolonews.auth.SecurityContextFilter;
import com.yolonews.auth.UserAPI;
import com.yolonews.posts.PostAPI;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

/**
 * @author saket.mehta
 */
class YoloNewsApp extends Application<YoloNewsConfig> {
    public static void main(String[] args) throws Exception {
        new YoloNewsApp().run(args);
    }

    @Override
    public void run(YoloNewsConfig configuration, Environment environment) {
        Injector injector = Guice.createInjector(new YoloNewsModule());

        // api
        environment.jersey().register(injector.getInstance(AuthAPI.class));
        environment.jersey().register(injector.getInstance(UserAPI.class));
        environment.jersey().register(injector.getInstance(PostAPI.class));

        // filters
        environment.jersey().register(injector.getInstance(SecurityContextFilter.class));
        environment.jersey().register(injector.getInstance(AuthFilter.class));
    }
}
