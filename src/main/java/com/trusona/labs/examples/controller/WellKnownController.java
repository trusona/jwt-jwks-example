package com.trusona.labs.examples.controller;

import com.codahale.metrics.annotation.Timed;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/.well-known/discovery")
@Produces(MediaType.APPLICATION_JSON)
public class WellKnownController {

  @GET
  @Timed
  public Map<String, Object> discovery(@Context HttpServletRequest request) {
    return Map.of("issuer", String.format("https://%s", request.getServerName()),
      "jwks_uri", String.format("https://%s/.well-known/jwks", request.getServerName()),
      "response_types_supported", List.of("id_token"),
      "subject_types_supported", List.of("pairwise"),
      "id_token_signing_alg_values_supported", List.of("RS256")
    );
  }
}
