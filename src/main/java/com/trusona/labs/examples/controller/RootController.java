package com.trusona.labs.examples.controller;

import com.codahale.metrics.annotation.Timed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class RootController {

  @GET
  @Timed
  public String root() {
    return "";
  }
}
