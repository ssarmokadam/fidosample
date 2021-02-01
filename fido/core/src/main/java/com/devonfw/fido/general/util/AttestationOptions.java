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

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.webauthn4j.data.AttestationConveyancePreference;
import com.webauthn4j.data.AuthenticatorSelectionCriteria;
import com.webauthn4j.data.PublicKeyCredentialDescriptor;
import com.webauthn4j.data.PublicKeyCredentialParameters;
import com.webauthn4j.data.PublicKeyCredentialRpEntity;
import com.webauthn4j.data.PublicKeyCredentialUserEntity;
import com.webauthn4j.data.client.challenge.Challenge;
import com.webauthn4j.data.extension.client.AuthenticationExtensionsClientInputs;
import com.webauthn4j.data.extension.client.RegistrationExtensionClientInput;
import com.webauthn4j.util.CollectionUtil;

public class AttestationOptions implements Serializable {

  // ~ Instance fields
  // ================================================================================================

  private final PublicKeyCredentialRpEntity rp;

  private final PublicKeyCredentialUserEntity user;

  private final Challenge challenge;

  private final List<PublicKeyCredentialParameters> pubKeyCredParams;

  private final Long timeout;

  private final List<PublicKeyCredentialDescriptor> excludeCredentials;

  private final AuthenticatorSelectionCriteria authenticatorSelection;

  private final AttestationConveyancePreference attestation;

  private final AuthenticationExtensionsClientInputs<RegistrationExtensionClientInput> extensions;

  @SuppressWarnings("squid:S00107")
  public AttestationOptions(PublicKeyCredentialRpEntity rp, PublicKeyCredentialUserEntity user, Challenge challenge,
      List<PublicKeyCredentialParameters> pubKeyCredParams, Long timeout,
      List<PublicKeyCredentialDescriptor> excludeCredentials, AuthenticatorSelectionCriteria authenticatorSelection,
      AttestationConveyancePreference attestation,
      AuthenticationExtensionsClientInputs<RegistrationExtensionClientInput> extensions) {

    this.rp = rp;
    this.user = user;
    this.challenge = challenge;
    this.pubKeyCredParams = CollectionUtil.unmodifiableList(pubKeyCredParams);
    this.timeout = timeout;
    this.excludeCredentials = CollectionUtil.unmodifiableList(excludeCredentials);
    this.authenticatorSelection = authenticatorSelection;
    this.attestation = attestation;
    this.extensions = extensions;
  }

  public AttestationOptions(PublicKeyCredentialRpEntity rp, PublicKeyCredentialUserEntity user, Challenge challenge,
      List<PublicKeyCredentialParameters> pubKeyCredParams) {

    this(rp, user, challenge, pubKeyCredParams, null, Collections.emptyList(), null, null, null);
  }

  public PublicKeyCredentialRpEntity getRp() {

    return this.rp;
  }

  public PublicKeyCredentialUserEntity getUser() {

    return this.user;
  }

  public Challenge getChallenge() {

    return this.challenge;
  }

  public List<PublicKeyCredentialParameters> getPubKeyCredParams() {

    return this.pubKeyCredParams;
  }

  public Long getTimeout() {

    return this.timeout;
  }

  public List<PublicKeyCredentialDescriptor> getExcludeCredentials() {

    return this.excludeCredentials;
  }

  public AuthenticatorSelectionCriteria getAuthenticatorSelection() {

    return this.authenticatorSelection;
  }

  public AttestationConveyancePreference getAttestation() {

    return this.attestation;
  }

  public AuthenticationExtensionsClientInputs<RegistrationExtensionClientInput> getExtensions() {

    return this.extensions;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    AttestationOptions that = (AttestationOptions) o;
    return Objects.equals(this.rp, that.rp) && Objects.equals(this.user, that.user)
        && Objects.equals(this.challenge, that.challenge)
        && Objects.equals(this.pubKeyCredParams, that.pubKeyCredParams) && Objects.equals(this.timeout, that.timeout)
        && Objects.equals(this.excludeCredentials, that.excludeCredentials)
        && Objects.equals(this.authenticatorSelection, that.authenticatorSelection)
        && this.attestation == that.attestation && Objects.equals(this.extensions, that.extensions);
  }

  @Override
  public int hashCode() {

    return Objects.hash(this.rp, this.user, this.challenge, this.pubKeyCredParams, this.timeout,
        this.excludeCredentials, this.authenticatorSelection, this.attestation, this.extensions);
  }
}
