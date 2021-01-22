package com.devonfw.fido.usermanagement.common.api;

import java.util.Set;

import com.devonfw.fido.general.common.api.ApplicationEntity;
import com.webauthn4j.data.AuthenticatorTransport;
import com.webauthn4j.data.attestation.authenticator.AttestedCredentialData;
import com.webauthn4j.data.attestation.statement.AttestationStatement;
import com.webauthn4j.data.extension.authenticator.AuthenticationExtensionsAuthenticatorOutputs;
import com.webauthn4j.data.extension.authenticator.RegistrationExtensionAuthenticatorOutput;
import com.webauthn4j.data.extension.client.AuthenticationExtensionsClientOutputs;
import com.webauthn4j.data.extension.client.RegistrationExtensionClientOutput;

public interface Authenticator extends ApplicationEntity {

  /**
   * @return idId
   */
  @Override
  public Long getId();

  /**
   * @param id setter for id attribute
   */
  @Override
  public void setId(Long id);

  /**
   * @return nameId
   */
  public String getName();

  /**
   * @param name setter for name attribute
   */
  public void setName(String name);

  /**
   * getter for userId attribute
   *
   * @return userId
   */
  public Long getUserId();

  /**
   * @param user setter for user attribute
   */
  public void setUserId(Long userId);

  /**
   * @return counterId
   */
  public long getCounter();

  /**
   * @param counter setter for counter attribute
   */
  public void setCounter(long counter);

  /**
   * @return transportsId
   */
  public Set<AuthenticatorTransport> getTransports();

  /**
   * @param transports setter for transports attribute
   */
  public void setTransports(Set<AuthenticatorTransport> transports);

  /**
   * @return attestedCredentialDataId
   */
  public AttestedCredentialData getAttestedCredentialData();

  /**
   * @return attestationStatementId
   */
  public AttestationStatement getAttestationStatement();

  /**
   * @param attestationStatement setter for attestationStatement attribute
   */
  public void setAttestationStatement(AttestationStatement attestationStatement);

  /**
   * @return clientExtensionsId
   */
  public AuthenticationExtensionsClientOutputs<RegistrationExtensionClientOutput> getClientExtensions();

  /**
   * @param clientExtensions setter for clientExtensions attribute
   */
  public void setClientExtensions(
      AuthenticationExtensionsClientOutputs<RegistrationExtensionClientOutput> clientExtensions);

  /**
   * @return authenticatorExtensionsId
   */
  public AuthenticationExtensionsAuthenticatorOutputs<RegistrationExtensionAuthenticatorOutput> getAuthenticatorExtensions();

  /**
   * @param authenticatorExtensions setter for authenticatorExtensions attribute
   */
  public void setAuthenticatorExtensions(
      AuthenticationExtensionsAuthenticatorOutputs<RegistrationExtensionAuthenticatorOutput> authenticatorExtensions);

}
