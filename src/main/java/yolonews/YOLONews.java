package yolonews;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

/**
 * @author saket.mehta
 */
public class YOLONews extends Application<YOLONewsConfiguration> {
    public static void main(String[] args) throws Exception {
        new YOLONews().run(args);
    }

    @Override
    public void run(YOLONewsConfiguration configuration, Environment environment) throws Exception {

    }
}
