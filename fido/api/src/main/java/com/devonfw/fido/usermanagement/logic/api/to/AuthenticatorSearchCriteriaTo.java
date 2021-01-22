package com.devonfw.fido.usermanagement.logic.api.to;

import com.devonfw.fido.general.common.api.to.AbstractSearchCriteriaTo;
import com.devonfw.module.basic.common.api.query.StringSearchConfigTo;
import com.webauthn4j.data.attestation.authenticator.AttestedCredentialData;
import com.webauthn4j.data.attestation.statement.AttestationStatement;
import com.webauthn4j.data.extension.authenticator.AuthenticationExtensionsAuthenticatorOutputs;
import com.webauthn4j.data.extension.authenticator.RegistrationExtensionAuthenticatorOutput;
import com.webauthn4j.data.extension.client.AuthenticationExtensionsClientOutputs;
import com.webauthn4j.data.extension.client.RegistrationExtensionClientOutput;

/**
 * {@link SearchCriteriaTo} to find instances of {@link com.cg.webauthndemo.usermanagement.common.api.Authenticator}s.
 */
public class AuthenticatorSearchCriteriaTo extends AbstractSearchCriteriaTo {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String name;

  private Long userId;

  private Long counter;

  private AttestedCredentialData attestedCredentialData;

  private AttestationStatement attestationStatement;

  private AuthenticationExtensionsClientOutputs<RegistrationExtensionClientOutput> clientExtensions;

  private AuthenticationExtensionsAuthenticatorOutputs<RegistrationExtensionAuthenticatorOutput> authenticatorExtensions;

  private StringSearchConfigTo nameOption;

  /**
   * @return idId
   */
  public Long getId() {

    return this.id;
  }

  /**
   * @param id setter for id attribute
   */
  public void setId(Long id) {

    this.id = id;
  }

  /**
   * @return nameId
   */
  public String getName() {

    return this.name;
  }

  /**
   * @param name setter for name attribute
   */
  public void setName(String name) {

    this.name = name;
  }

  /**
   * getter for userId attribute
   *
   * @return userId
   */
  public Long getUserId() {

    return this.userId;
  }

  /**
   * @param user setter for user attribute
   */
  public void setUserId(Long userId) {

    this.userId = userId;
  }

  /**
   * @return counterId
   */
  public Long getCounter() {

    return this.counter;
  }

  /**
   * @param counter setter for counter attribute
   */
  public void setCounter(Long counter) {

    this.counter = counter;
  }

  /**
   * @return attestedCredentialDataId
   */
  public AttestedCredentialData getAttestedCredentialData() {

    return this.attestedCredentialData;
  }

  /**
   * @param attestedCredentialData setter for attestedCredentialData attribute
   */
  public void setAttestedCredentialData(AttestedCredentialData attestedCredentialData) {

    this.attestedCredentialData = attestedCredentialData;
  }

  /**
   * @return attestationStatementId
   */
  public AttestationStatement getAttestationStatement() {

    return this.attestationStatement;
  }

  /**
   * @param attestationStatement setter for attestationStatement attribute
   */
  public void setAttestationStatement(AttestationStatement attestationStatement) {

    this.attestationStatement = attestationStatement;
  }

  /**
   * @return clientExtensionsId
   */
  public AuthenticationExtensionsClientOutputs getClientExtensions() {

    return this.clientExtensions;
  }

  /**
   * @param clientExtensions setter for clientExtensions attribute
   */
  public void setClientExtensions(AuthenticationExtensionsClientOutputs clientExtensions) {

    this.clientExtensions = clientExtensions;
  }

  /**
   * @return authenticatorExtensionsId
   */
  public AuthenticationExtensionsAuthenticatorOutputs getAuthenticatorExtensions() {

    return this.authenticatorExtensions;
  }

  /**
   * @param authenticatorExtensions setter for authenticatorExtensions attribute
   */
  public void setAuthenticatorExtensions(AuthenticationExtensionsAuthenticatorOutputs authenticatorExtensions) {

    this.authenticatorExtensions = authenticatorExtensions;
  }

  /**
   * @return the {@link StringSearchConfigTo} used to search for {@link #getName() name}.
   */
  public StringSearchConfigTo getNameOption() {

    return this.nameOption;
  }

  /**
   * @param nameOption new value of {@link #getNameOption()}.
   */
  public void setNameOption(StringSearchConfigTo nameOption) {

    this.nameOption = nameOption;
  }

}
