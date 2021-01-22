package com.devonfw.fido.usermanagement.logic.api.to;

import java.util.Set;

import com.devonfw.fido.usermanagement.common.api.Authenticator;
import com.devonfw.module.basic.common.api.to.AbstractEto;
import com.webauthn4j.data.AuthenticatorTransport;
import com.webauthn4j.data.attestation.authenticator.AttestedCredentialData;
import com.webauthn4j.data.attestation.statement.AttestationStatement;
import com.webauthn4j.data.extension.authenticator.AuthenticationExtensionsAuthenticatorOutputs;
import com.webauthn4j.data.extension.authenticator.RegistrationExtensionAuthenticatorOutput;
import com.webauthn4j.data.extension.client.AuthenticationExtensionsClientOutputs;
import com.webauthn4j.data.extension.client.RegistrationExtensionClientOutput;

/**
 * Entity transport object of Authenticator
 */
public class AuthenticatorEto extends AbstractEto implements Authenticator {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String name;

  private Long userId;

  private long counter;

  private Set<AuthenticatorTransport> transports;

  private AttestedCredentialData attestedCredentialData;

  private AttestationStatement attestationStatement;

  private AuthenticationExtensionsClientOutputs<RegistrationExtensionClientOutput> clientExtensions;

  private AuthenticationExtensionsAuthenticatorOutputs<RegistrationExtensionAuthenticatorOutput> authenticatorExtensions;

  @Override
  public Long getId() {

    return this.id;
  }

  @Override
  public void setId(Long id) {

    this.id = id;
  }

  @Override
  public String getName() {

    return this.name;
  }

  @Override
  public void setName(String name) {

    this.name = name;
  }

  @Override
  public Long getUserId() {

    return this.userId;
  }

  @Override
  public void setUserId(Long userId) {

    this.userId = userId;
  }

  @Override
  public long getCounter() {

    return this.counter;
  }

  @Override
  public void setCounter(long counter) {

    this.counter = counter;
  }

  @Override
  public Set<AuthenticatorTransport> getTransports() {

    return this.transports;
  }

  @Override
  public void setTransports(Set<AuthenticatorTransport> transports) {

    this.transports = transports;
  }

  // @Override
  // public AttestedCredentialData getAttestedCredentialData() {
  //
  // return this.attestedCredentialData;
  // }
  //
  // @Override
  // public void setAttestedCredentialData(AttestedCredentialData attestedCredentialData) {
  //
  // this.attestedCredentialData = attestedCredentialData;
  // }

  @Override
  public AttestationStatement getAttestationStatement() {

    return this.attestationStatement;
  }

  @Override
  public void setAttestationStatement(AttestationStatement attestationStatement) {

    this.attestationStatement = attestationStatement;
  }

  @Override
  public AuthenticationExtensionsClientOutputs<RegistrationExtensionClientOutput> getClientExtensions() {

    return this.clientExtensions;
  }

  @Override
  public void setClientExtensions(
      AuthenticationExtensionsClientOutputs<RegistrationExtensionClientOutput> clientExtensions) {

    this.clientExtensions = clientExtensions;
  }

  @Override
  public AuthenticationExtensionsAuthenticatorOutputs<RegistrationExtensionAuthenticatorOutput> getAuthenticatorExtensions() {

    return this.authenticatorExtensions;
  }

  @Override
  public void setAuthenticatorExtensions(
      AuthenticationExtensionsAuthenticatorOutputs<RegistrationExtensionAuthenticatorOutput> authenticatorExtensions) {

    this.authenticatorExtensions = authenticatorExtensions;
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
    result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());

    result = prime * result + ((this.userId == null) ? 0 : this.userId.hashCode());
    result = prime * result + ((Long) this.counter).hashCode();
    result = prime * result + ((this.transports == null) ? 0 : this.transports.hashCode());
    // result = prime * result + ((this.attestedCredentialData == null) ? 0 : this.attestedCredentialData.hashCode());
    result = prime * result + ((this.attestationStatement == null) ? 0 : this.attestationStatement.hashCode());
    result = prime * result + ((this.clientExtensions == null) ? 0 : this.clientExtensions.hashCode());
    result = prime * result + ((this.authenticatorExtensions == null) ? 0 : this.authenticatorExtensions.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    // class check will be done by super type EntityTo!
    if (!super.equals(obj)) {
      return false;
    }
    AuthenticatorEto other = (AuthenticatorEto) obj;
    if (this.id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!this.id.equals(other.id)) {
      return false;
    }
    if (this.name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!this.name.equals(other.name)) {
      return false;
    }

    if (this.userId == null) {
      if (other.userId != null) {
        return false;
      }
    } else if (!this.userId.equals(other.userId)) {
      return false;
    }
    if (this.counter != other.counter) {
      return false;
    }
    if (this.transports == null) {
      if (other.transports != null) {
        return false;
      }
    } else if (!this.transports.equals(other.transports)) {
      return false;
    }
    // if (this.attestedCredentialData == null) {
    // if (other.attestedCredentialData != null) {
    // return false;
    // }
    // } else if (!this.attestedCredentialData.equals(other.attestedCredentialData)) {
    // return false;
    // }
    if (this.attestationStatement == null) {
      if (other.attestationStatement != null) {
        return false;
      }
    } else if (!this.attestationStatement.equals(other.attestationStatement)) {
      return false;
    }
    if (this.clientExtensions == null) {
      if (other.clientExtensions != null) {
        return false;
      }
    } else if (!this.clientExtensions.equals(other.clientExtensions)) {
      return false;
    }
    if (this.authenticatorExtensions == null) {
      if (other.authenticatorExtensions != null) {
        return false;
      }
    } else if (!this.authenticatorExtensions.equals(other.authenticatorExtensions)) {
      return false;
    }
    return true;
  }

  @Override
  public AttestedCredentialData getAttestedCredentialData() {

    return this.attestedCredentialData;
  }

}
