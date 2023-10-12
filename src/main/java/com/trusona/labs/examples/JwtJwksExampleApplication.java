package com.trusona.labs.examples;

import com.nimbusds.jose.JOSEException;
import com.trusona.labs.examples.config.BasicConfiguration;
import com.trusona.labs.examples.controller.HealthController;
import com.trusona.labs.examples.controller.JwksController;
import com.trusona.labs.examples.controller.JwtController;
import com.trusona.labs.examples.controller.RootController;
import com.trusona.labs.examples.controller.WellKnownController;
import com.trusona.labs.examples.service.JwksService;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import java.security.GeneralSecurityException;

public class JwtJwksExampleApplication extends Application<BasicConfiguration> {

  public static void main(String[] args) throws Exception {
    new JwtJwksExampleApplication().run(args);
  }

  @Override
  public void run(BasicConfiguration configuration, Environment environment) throws GeneralSecurityException, JOSEException {
    JwksService jwksService = new JwksService();

    environment.jersey().register(new HealthController());
    environment.jersey().register(new RootController());
    environment.jersey().register(new WellKnownController());
    environment.jersey().register(new JwksController(jwksService));
    environment.jersey().register(new JwtController(jwksService));

    environment.healthChecks().register("default", new HealthController());
    environment.health().addHealthStateListener(new HealthController.DefaultHealthStateListener());
  }
}
