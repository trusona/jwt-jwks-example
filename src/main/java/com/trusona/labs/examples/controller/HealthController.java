package com.trusona.labs.examples.controller;

import com.codahale.metrics.annotation.Timed;
import com.codahale.metrics.health.HealthCheck;
import io.dropwizard.health.HealthStateListener;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/health")
@Produces(MediaType.APPLICATION_JSON)
public class HealthController extends HealthCheck {

  @GET
  @Timed
  public Map<String, Long> healthy() {
    return Map.of("ping", System.currentTimeMillis());
  }

  @Override
  protected Result check() {
    return Result.healthy();
  }

  public static class DefaultHealthStateListener implements HealthStateListener {

    @Override
    public void onHealthyCheck(String healthCheckName) {

    }

    @Override
    public void onUnhealthyCheck(String healthCheckName) {

    }

    @Override
    public void onStateChanged(String healthCheckName, boolean healthy) {

    }
  }
}
