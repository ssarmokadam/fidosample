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

package com.devonfw.fido.general.util;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

import com.webauthn4j.data.AttestationConveyancePreference;
import com.webauthn4j.data.AuthenticatorSelectionCriteria;
import com.webauthn4j.data.PublicKeyCredentialDescriptor;
import com.webauthn4j.data.PublicKeyCredentialParameters;
import com.webauthn4j.data.PublicKeyCredentialRpEntity;
import com.webauthn4j.data.PublicKeyCredentialType;
import com.webauthn4j.data.PublicKeyCredentialUserEntity;
import com.webauthn4j.data.extension.client.AuthenticationExtensionsClientInputs;
import com.webauthn4j.data.extension.client.RegistrationExtensionClientInput;
import com.webauthn4j.springframework.security.authenticator.WebAuthnAuthenticatorService;
import com.webauthn4j.springframework.security.challenge.ChallengeRepository;
import com.webauthn4j.springframework.security.exception.PrincipalNotFoundException;
import com.webauthn4j.springframework.security.extension.AuthenticationExtensionsClientInputsProvider;
import com.webauthn4j.springframework.security.options.PublicKeyCredentialUserEntityProvider;
import com.webauthn4j.springframework.security.options.RpIdProvider;

/**
 * An {@link AssertionOptionsProvider} implementation
 */
public class AttestationOptionsProviderImpl implements AttestationOptionsProvider {

  // ~ Instance fields
  // ================================================================================================
  private String rpId = null;

  private String rpName = null;

  private List<PublicKeyCredentialParameters> pubKeyCredParams = new ArrayList<>();

  private AuthenticatorSelectionCriteria registrationAuthenticatorSelection;

  private AttestationConveyancePreference attestation;

  private Long registrationTimeout = null;

  private AuthenticationExtensionsClientInputs<RegistrationExtensionClientInput> registrationExtensions;

  private RpIdProvider rpIdProvider;

  private PublicKeyCredentialUserEntityProvider publicKeyCredentialUserEntityProvider = new DefaultPublicKeyCredentialUserEntityProvider();

  private final WebAuthnAuthenticatorService authenticatorService;

  private final ChallengeRepository challengeRepository;

  private AuthenticationExtensionsClientInputsProvider<RegistrationExtensionClientInput> registrationExtensionsProvider = new DefaultRegistrationExtensionsProvider();

  // ~ Constructors
  // ===================================================================================================

  public AttestationOptionsProviderImpl(RpIdProvider rpIdProvider, WebAuthnAuthenticatorService authenticatorService,
      ChallengeRepository challengeRepository) {

    Assert.notNull(authenticatorService, "authenticatorService must not be null");
    Assert.notNull(challengeRepository, "challengeRepository must not be null");

    this.rpIdProvider = rpIdProvider;
    this.authenticatorService = authenticatorService;
    this.challengeRepository = challengeRepository;
  }

  public AttestationOptionsProviderImpl(WebAuthnAuthenticatorService authenticatorService,
      ChallengeRepository challengeRepository) {

    this(null, authenticatorService, challengeRepository);
  }

  // ~ Methods
  // ========================================================================================================

  /**
   * {@inheritDoc}
   */
  @Override
  public AttestationOptions getAttestationOptions(HttpServletRequest request, Authentication authentication) {

    PublicKeyCredentialRpEntity relyingParty = new PublicKeyCredentialRpEntity(getRpId(request), this.rpName);
    PublicKeyCredentialUserEntity user;
    try {
      user = getPublicKeyCredentialUserEntityProvider().provide(authentication);
    } catch (PrincipalNotFoundException e) {
      user = null;
    }

    return new AttestationOptions(relyingParty, user, getChallengeRepository().loadOrGenerateChallenge(request),
        getPubKeyCredParams(), getRegistrationTimeout(), getCredentials(authentication),
        getRegistrationAuthenticatorSelection(), getAttestation(),
        getRegistrationExtensionsProvider().provide(request));
  }

  public String getRpId() {

    return this.rpId;
  }

  public void setRpId(String rpId) {

    this.rpId = rpId;
    this.rpIdProvider = null;
  }

  public String getRpName() {

    return this.rpName;
  }

  public void setRpName(String rpName) {

    Assert.hasText(rpName, "rpName parameter must not be empty or null");
    this.rpName = rpName;
  }

  public List<PublicKeyCredentialParameters> getPubKeyCredParams() {

    return this.pubKeyCredParams;
  }

  public void setPubKeyCredParams(List<PublicKeyCredentialParameters> pubKeyCredParams) {

    this.pubKeyCredParams = pubKeyCredParams;
  }

  public AuthenticatorSelectionCriteria getRegistrationAuthenticatorSelection() {

    return this.registrationAuthenticatorSelection;
  }

  public void setRegistrationAuthenticatorSelection(AuthenticatorSelectionCriteria registrationAuthenticatorSelection) {

    this.registrationAuthenticatorSelection = registrationAuthenticatorSelection;
  }

  public AttestationConveyancePreference getAttestation() {

    return this.attestation;
  }

  public void setAttestation(AttestationConveyancePreference attestation) {

    this.attestation = attestation;
  }

  public Long getRegistrationTimeout() {

    return this.registrationTimeout;
  }

  public void setRegistrationTimeout(Long registrationTimeout) {

    Assert.notNull(registrationTimeout, "registrationTimeout must not be null.");
    Assert.isTrue(registrationTimeout >= 0, "registrationTimeout must be within unsigned long.");
    this.registrationTimeout = registrationTimeout;
  }

  public AuthenticationExtensionsClientInputs<RegistrationExtensionClientInput> getRegistrationExtensions() {

    return this.registrationExtensions;
  }

  public void setRegistrationExtensions(
      AuthenticationExtensionsClientInputs<RegistrationExtensionClientInput> registrationExtensions) {

    this.registrationExtensions = registrationExtensions;
  }

  public RpIdProvider getRpIdProvider() {

    return this.rpIdProvider;
  }

  public void setRpIdProvider(RpIdProvider rpIdProvider) {

    this.rpId = null;
    this.rpIdProvider = rpIdProvider;
  }

  public AuthenticationExtensionsClientInputsProvider<RegistrationExtensionClientInput> getRegistrationExtensionsProvider() {

    return this.registrationExtensionsProvider;
  }

  public void setRegistrationExtensionsProvider(
      AuthenticationExtensionsClientInputsProvider<RegistrationExtensionClientInput> registrationExtensionsProvider) {

    Assert.notNull(registrationExtensionsProvider, "registrationExtensionsProvider must not be null");
    this.registrationExtensionsProvider = registrationExtensionsProvider;
  }

  public WebAuthnAuthenticatorService getAuthenticatorService() {

    return this.authenticatorService;
  }

  public void setPublicKeyCredentialUserEntityProvider(
      PublicKeyCredentialUserEntityProvider publicKeyCredentialUserEntityProvider) {

    Assert.notNull(publicKeyCredentialUserEntityProvider, "webAuthnUserHandleProvider must not be null");
    this.publicKeyCredentialUserEntityProvider = publicKeyCredentialUserEntityProvider;
  }

  public PublicKeyCredentialUserEntityProvider getPublicKeyCredentialUserEntityProvider() {

    return this.publicKeyCredentialUserEntityProvider;
  }

  protected ChallengeRepository getChallengeRepository() {

    return this.challengeRepository;
  }

  protected List<PublicKeyCredentialDescriptor> getCredentials(Authentication authentication) {

    if (authentication == null) {
      return Collections.emptyList();
    }
    try {
      return getAuthenticatorService().loadAuthenticatorsByUserPrincipal(authentication.getName()).stream()
          .map(authenticator -> new PublicKeyCredentialDescriptor(PublicKeyCredentialType.PUBLIC_KEY,
              authenticator.getAttestedCredentialData().getCredentialId(), authenticator.getTransports()))
          .collect(Collectors.toList());
    } catch (PrincipalNotFoundException e) {
      return Collections.emptyList();
    }
  }

  String getRpId(HttpServletRequest request) {

    if (this.rpIdProvider != null) {
      return this.rpIdProvider.provide(request);
    } else {
      return this.rpId;
    }
  }

  static class DefaultPublicKeyCredentialUserEntityProvider implements PublicKeyCredentialUserEntityProvider {

    @Override
    public PublicKeyCredentialUserEntity provide(Authentication authentication) {

      if (authentication == null) {
        return null;
      }
      String username = authentication.getName();
      return new PublicKeyCredentialUserEntity(username.getBytes(StandardCharsets.UTF_8), username, username);
    }
  }

  class DefaultRegistrationExtensionsProvider
      implements AuthenticationExtensionsClientInputsProvider<RegistrationExtensionClientInput> {
    @Override
    public AuthenticationExtensionsClientInputs<RegistrationExtensionClientInput> provide(
        HttpServletRequest httpServletRequest) {

      return AttestationOptionsProviderImpl.this.registrationExtensions;
    }
  }

}
