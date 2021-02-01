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
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.webauthn4j.data.PublicKeyCredentialDescriptor;
import com.webauthn4j.data.UserVerificationRequirement;
import com.webauthn4j.data.client.challenge.Challenge;
import com.webauthn4j.data.extension.client.AuthenticationExtensionClientInput;
import com.webauthn4j.data.extension.client.AuthenticationExtensionsClientInputs;
import com.webauthn4j.util.CollectionUtil;

/**
 * {@link com.webauthn4j.data.PublicKeyCredentialRequestOptions} supplies get() with the data it needs to generate an
 * assertion. Its challenge member MUST be present, while its other members are OPTIONAL.
 *
 * @see <a href="https://www.w3.org/TR/webauthn-1/#dictdef-publickeycredentialrequestoptions"> §5.5. Options for
 *      Assertion Generation (dictionary PublicKeyCredentialRequestOptions)</a>
 */
public class AssertionOptions implements Serializable {

  // ~ Instance fields
  // ================================================================================================

  private final Challenge challenge;

  private final Long timeout;

  private final String rpId;

  private final List<PublicKeyCredentialDescriptor> allowCredentials;

  private final UserVerificationRequirement userVerification;

  private final AuthenticationExtensionsClientInputs<AuthenticationExtensionClientInput> extensions;

  @JsonCreator
  public AssertionOptions(@JsonProperty("challenge") Challenge challenge, @JsonProperty("timeout") Long timeout,
      @JsonProperty("rpId") String rpId,
      @JsonProperty("allowCredentials") List<PublicKeyCredentialDescriptor> allowCredentials,
      @JsonProperty("userVerification") UserVerificationRequirement userVerification,
      @JsonProperty("extensions") AuthenticationExtensionsClientInputs<AuthenticationExtensionClientInput> extensions) {

    this.challenge = challenge;
    this.timeout = timeout;
    this.rpId = rpId;
    this.allowCredentials = CollectionUtil.unmodifiableList(allowCredentials);
    this.userVerification = userVerification;
    this.extensions = extensions;
  }

  public Challenge getChallenge() {

    return this.challenge;
  }

  public Long getTimeout() {

    return this.timeout;
  }

  public String getRpId() {

    return this.rpId;
  }

  public List<PublicKeyCredentialDescriptor> getAllowCredentials() {

    return this.allowCredentials;
  }

  public UserVerificationRequirement getUserVerification() {

    return this.userVerification;
  }

  public AuthenticationExtensionsClientInputs<AuthenticationExtensionClientInput> getExtensions() {

    return this.extensions;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    AssertionOptions that = (AssertionOptions) o;
    return Objects.equals(this.timeout, that.timeout) && Objects.equals(this.challenge, that.challenge)
        && Objects.equals(this.rpId, that.rpId) && Objects.equals(this.allowCredentials, that.allowCredentials)
        && this.userVerification == that.userVerification && Objects.equals(this.extensions, that.extensions);
  }

  @Override
  public int hashCode() {

    return Objects.hash(this.challenge, this.timeout, this.rpId, this.allowCredentials, this.userVerification,
        this.extensions);
  }
}
