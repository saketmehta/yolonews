package yolonews;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import yolonews.api.UserResource;

/**
 * @author saket.mehta
 */
public class YOLONews extends Application<Configuration> {
    public static void main(String[] args) throws Exception {
        new YOLONews().run(args);
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        environment.jersey().register(new UserResource());
    }
}
