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

package com.devonfw.fido.general.service.impl.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.devonfw.fido.usermanagement.dataaccess.api.AuthenticatorEntity;
import com.devonfw.fido.usermanagement.dataaccess.api.UserEntity;
import com.devonfw.fido.usermanagement.dataaccess.api.repo.AuthenticatorRepository;
import com.devonfw.fido.usermanagement.logic.api.to.AuthenticatorSearchCriteriaTo;
import com.webauthn4j.data.attestation.authenticator.AttestedCredentialData;
import com.webauthn4j.springframework.security.authenticator.WebAuthnAuthenticator;
import com.webauthn4j.springframework.security.authenticator.WebAuthnAuthenticatorManager;
import com.webauthn4j.springframework.security.exception.CredentialIdNotFoundException;
import com.webauthn4j.util.Base64UrlUtil;

@Transactional
@Component
public class AuthenticatorManagerImpl implements WebAuthnAuthenticatorManager {

  private final Logger logger = LoggerFactory.getLogger(AuthenticatorManagerImpl.class);

  private Map<Object, Map<String, WebAuthnAuthenticator>> map = new HashMap<>();

  private final AuthenticatorRepository authenticatorEntityRepository;

  public AuthenticatorManagerImpl(AuthenticatorRepository authenticatorEntityRepository) {

    this.authenticatorEntityRepository = authenticatorEntityRepository;
  }

  @Override
  public void updateCounter(byte[] credentialId, long counter) throws CredentialIdNotFoundException {

    AuthenticatorSearchCriteriaTo criteria = new AuthenticatorSearchCriteriaTo();
    AttestedCredentialData credentialData = new AttestedCredentialData(null, credentialId, null);
    criteria.setAttestedCredentialData(credentialData);
    AuthenticatorEntity authenticatorEntity = this.authenticatorEntityRepository.findByCriteria(criteria).getContent()
        .get(0);
    // .orElseThrow(() -> new CredentialIdNotFoundException("AuthenticatorEntity not found"));
    authenticatorEntity.setCounter(counter);
  }

  @Override
  public WebAuthnAuthenticator loadAuthenticatorByCredentialId(byte[] credentialId) {

    AuthenticatorSearchCriteriaTo criteria = new AuthenticatorSearchCriteriaTo();
    AttestedCredentialData credentialData = new AttestedCredentialData(null, credentialId, null);
    criteria.setAttestedCredentialData(credentialData);
    return this.authenticatorEntityRepository.findByCriteria(criteria).getContent().get(0);
    // .orElseThrow(() -> new CredentialIdNotFoundException("AuthenticatorEntity not found"));
  }

  @Override
  public List<WebAuthnAuthenticator> loadAuthenticatorsByUserPrincipal(Object principal) {

    String username;
    if (principal == null) {
      return Collections.emptyList();
    } else if (principal instanceof String) {
      username = (String) principal;
    } else if (principal instanceof Authentication) {
      username = ((Authentication) principal).getName();
    } else {
      throw new IllegalArgumentException("unexpected principal is specified.");
    }
    UserEntity userEntity = new UserEntity();
    userEntity.setEmailAddress(username);
    return new ArrayList<>(this.authenticatorEntityRepository.abc(username));
  }

  @Override
  public void createAuthenticator(WebAuthnAuthenticator webAuthnAuthenticator) {

    Object userPrincipal = webAuthnAuthenticator.getUserPrincipal();
    if (!this.map.containsKey(userPrincipal)) {
      this.map.put(userPrincipal, new HashMap<>());
    }
    this.map.get(userPrincipal).put(
        Base64UrlUtil.encodeToString(webAuthnAuthenticator.getAttestedCredentialData().getCredentialId()),
        webAuthnAuthenticator);
  }

  @Override
  public void deleteAuthenticator(byte[] credentialId) {

    for (Map.Entry<Object, Map<String, WebAuthnAuthenticator>> entry : this.map.entrySet()) {
      WebAuthnAuthenticator webAuthnAuthenticator = entry.getValue().get(Base64UrlUtil.encodeToString(credentialId));
      if (webAuthnAuthenticator != null) {
        entry.getValue().remove(Base64UrlUtil.encodeToString(credentialId));
        return;
      }
    }
    throw new CredentialIdNotFoundException("credentialId not found.");
  }

  @Override
  public boolean authenticatorExists(byte[] credentialId) {

    return this.map.values().stream()
        .anyMatch(innerMap -> innerMap.get(Base64UrlUtil.encodeToString(credentialId)) != null);
  }
}
