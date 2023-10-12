package com.trusona.labs.examples.controller;

import com.codahale.metrics.annotation.Timed;
import com.trusona.labs.examples.service.JwksService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/.well-known/jwks")
@Produces(MediaType.APPLICATION_JSON)
public class JwksController {
  private final JwksService jwksService;

  public JwksController(JwksService jwksService) {
    this.jwksService = jwksService;
  }

  @GET
  @Timed
  public Map<String, Object> jwks(@Context HttpServletRequest request) {
    return jwksService.publicJwks();
  }
}
