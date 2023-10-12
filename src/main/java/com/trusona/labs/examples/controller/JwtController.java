package com.trusona.labs.examples.controller;

import com.codahale.metrics.annotation.Timed;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.trusona.labs.examples.service.JwksService;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/jwt")
@Produces(MediaType.APPLICATION_JSON)
public class JwtController {

  private final JWSSigner signer;
  private final String keyId;

  public JwtController(JwksService jwksService) throws JOSEException {
    this.signer = new RSASSASigner(jwksService.jwk().orElseThrow().toRSAKey());
    this.keyId = jwksService.jwk().orElseThrow().getKeyID();
  }

  @GET
  @Timed
  public Optional<Map<String, String>> generateJwt(@Context HttpServletRequest request,
                                                   @QueryParam("sub") String subject) throws JOSEException {

    if (subject == null || subject.isBlank()) {
      return Optional.empty();
    }

    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
      .expirationTime(Date.from(Instant.now().plus(Duration.ofMinutes(5))))
      .issuer(String.format("https://%s", request.getServerName()))
      .notBeforeTime(Date.from(Instant.now()))
      .subject(subject.trim().toLowerCase())
      .issueTime(Date.from(Instant.now()))
      .jwtID(UUID.randomUUID().toString())
      .audience(request.getServerName())
      .build();

    return Optional.of(Map.of("jwt", signedJwt(claimsSet).serialize()));
  }

  private SignedJWT signedJwt(JWTClaimsSet claimsSet) throws JOSEException {
    JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(keyId).build();
    SignedJWT signedJwt = new SignedJWT(header, claimsSet);
    signedJwt.sign(signer);
    return signedJwt;
  }
}
