/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.devonfw.fido.general.service.impl.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import com.devonfw.fido.general.util.ServerPublicKeyCredentialValidator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.webauthn4j.converter.AttestationObjectConverter;
import com.webauthn4j.converter.CollectedClientDataConverter;
import com.webauthn4j.converter.exception.DataConversionException;
import com.webauthn4j.converter.util.ObjectConverter;
import com.webauthn4j.data.attestation.AttestationObject;
import com.webauthn4j.data.client.CollectedClientData;
import com.webauthn4j.springframework.security.WebAuthnRegistrationRequestValidator;
import com.webauthn4j.springframework.security.authenticator.WebAuthnAuthenticatorImpl;
import com.webauthn4j.springframework.security.authenticator.WebAuthnAuthenticatorManager;

public class FidoServerAttestationResultEndpointFilter extends ServerEndpointFilterBase {

  /**
   * Default name of path suffix which will validate this filter.
   */
  public static final String FILTER_URL = "/webauthn/attestation/result";

  private final UserDetailsService userDetailsService;

  private final WebAuthnAuthenticatorManager webAuthnAuthenticatorManager;

  private final AttestationObjectConverter attestationObjectConverter;

  private final CollectedClientDataConverter collectedClientDataConverter;

  private final WebAuthnRegistrationRequestValidator webAuthnRegistrationRequestValidator;

  private final ServerPublicKeyCredentialValidator<ServerAuthenticatorAttestationResponse> serverPublicKeyCredentialValidator;

  private UsernameNotFoundHandler usernameNotFoundHandler = new DefaultUsernameNotFoundHandler();

  private final TypeReference<ServerPublicKeyCredential<ServerAuthenticatorAttestationResponse>> credentialTypeRef = new TypeReference<ServerPublicKeyCredential<ServerAuthenticatorAttestationResponse>>() {
  };

  public FidoServerAttestationResultEndpointFilter(ObjectConverter objectConverter,
      UserDetailsService userDetailsService, WebAuthnAuthenticatorManager webAuthnAuthenticatorManager,
      WebAuthnRegistrationRequestValidator webAuthnRegistrationRequestValidator) {

    super(FILTER_URL, objectConverter);
    this.attestationObjectConverter = new AttestationObjectConverter(objectConverter);
    this.collectedClientDataConverter = new CollectedClientDataConverter(objectConverter);
    this.serverPublicKeyCredentialValidator = new ServerPublicKeyCredentialValidator<>();

    this.userDetailsService = userDetailsService;
    this.webAuthnAuthenticatorManager = webAuthnAuthenticatorManager;
    this.webAuthnRegistrationRequestValidator = webAuthnRegistrationRequestValidator;
    checkConfig();
  }

  @Override
  public void afterPropertiesSet() {

    super.afterPropertiesSet();
    checkConfig();
  }

  @SuppressWarnings("squid:S2177")
  private void checkConfig() {

    Assert.notNull(this.webAuthnRegistrationRequestValidator, "webAuthnRegistrationRequestValidator must not be null");
  }

  @Override
  protected ServerResponse processRequest(HttpServletRequest request) {

    InputStream inputStream;
    try {
      inputStream = request.getInputStream();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
    try {
      ServerPublicKeyCredential<ServerAuthenticatorAttestationResponse> credential = this.objectConverter
          .getJsonConverter().readValue(inputStream, this.credentialTypeRef);
      this.serverPublicKeyCredentialValidator.validate(credential);
      ServerAuthenticatorAttestationResponse response = credential.getResponse();
      CollectedClientData collectedClientData = this.collectedClientDataConverter.convert(response.getClientDataJSON());
      AttestationObject attestationObject = this.attestationObjectConverter.convert(response.getAttestationObject());
      Set<String> transports = Collections.emptySet();
      this.webAuthnRegistrationRequestValidator.validate(request, response.getClientDataJSON(),
          response.getAttestationObject(), transports, credential.getClientExtensionResults());

      String loginUsername = this.serverEndpointFilterUtil.decodeUsername(collectedClientData.getChallenge());
      try {
        this.userDetailsService.loadUserByUsername(loginUsername);
      } catch (UsernameNotFoundException e) {
        this.usernameNotFoundHandler.onUsernameNotFound(loginUsername);
      }
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginUsername);
      WebAuthnAuthenticatorImpl webAuthnAuthenticator = new WebAuthnAuthenticatorImpl("Authenticator", loginUsername,
          attestationObject.getAuthenticatorData().getAttestedCredentialData(),
          attestationObject.getAttestationStatement(), attestationObject.getAuthenticatorData().getSignCount());
      this.webAuthnAuthenticatorManager.createAuthenticator(webAuthnAuthenticator);
      return new AttestationResultSuccessResponse();
    } catch (DataConversionException e) {
      throw new com.webauthn4j.springframework.security.exception.DataConversionException("Failed to convert data", e);
    }
  }

  public UsernameNotFoundHandler getUsernameNotFoundHandler() {

    return this.usernameNotFoundHandler;
  }

  public void setUsernameNotFoundHandler(UsernameNotFoundHandler usernameNotFoundHandler) {

    this.usernameNotFoundHandler = usernameNotFoundHandler;
  }

  private static class DefaultUsernameNotFoundHandler implements UsernameNotFoundHandler {
    @Override
    public void onUsernameNotFound(String loginUsername) {

      throw new UsernameNotFoundException("Username not found");
    }
  }

}
