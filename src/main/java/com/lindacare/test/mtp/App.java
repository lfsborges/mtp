package com.lindacare.test.mtp;

import com.lindacare.test.mtp.configurations.AppConfiguration;
import com.lindacare.test.mtp.consumer.Consumer;
import com.lindacare.test.mtp.producer.Producer;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application<AppConfiguration>  {

  private static final Logger LOG = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) throws Exception {
       new App().run(args);
  }

  @Override
  public String getName() {
    return "market-trade-processor";
  }

  @Override
  public void initialize(Bootstrap<AppConfiguration> bootstrap) {
    bootstrap.addBundle(new AssetsBundle("/assets/index.html", "/"));
    bootstrap.setConfigurationSourceProvider(
            new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                    new EnvironmentVariableSubstitutor()
            )
    );
  }

  @Override
  public void run(AppConfiguration config, Environment env) throws Exception {
    Producer producer = new Producer(config.getKafkaConfig());
    Consumer consumer = new Consumer(config.getKafkaConfig());

    env.lifecycle().manage(producer);
    env.lifecycle().manage(consumer);

    env.jersey().register(DeclarativeLinkingFeature.class);
    env.jersey().register(new Resource(producer, consumer));
  }
}