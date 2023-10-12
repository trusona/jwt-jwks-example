package com.trusona.labs.examples.service;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class JwksService {

  private final JWKSet jwkSet;

  public JwksService() throws NoSuchAlgorithmException {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(2048);

    KeyPair keyPair = keyPairGenerator.generateKeyPair();

    JWK jwk = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
      .privateKey((RSAPrivateKey) keyPair.getPrivate())
      .keyUse(KeyUse.SIGNATURE)
      .keyID(UUID.randomUUID().toString())
      .issueTime(Date.from(Instant.now()))
      .notBeforeTime(Date.from(Instant.now()))
      .expirationTime(Date.from(Instant.now().plus(Duration.ofDays(365))))
      .build();

    jwkSet = new JWKSet(jwk);
  }

  public Optional<JWK> jwk() {
    return jwkSet.getKeys().stream().findAny();
  }

  public Map<String, Object> publicJwks() {
    return jwkSet.toPublicJWKSet().toJSONObject(true);
  }
}
