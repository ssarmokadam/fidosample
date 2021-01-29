package com.devonfw.fido.usermanagement.dataaccess.api;

import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converts;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.userdetails.UserDetails;

import com.devonfw.fido.general.converter.AAGUIDConverter;
import com.devonfw.fido.general.converter.AttestationStatementConverter;
import com.devonfw.fido.general.converter.AuthenticatorExtensionsConverter;
import com.devonfw.fido.general.converter.AuthenticatorTransportConverter;
import com.devonfw.fido.general.converter.COSEKeyConverter;
import com.devonfw.fido.general.converter.ClientExtensionsConverter;
import com.devonfw.fido.general.dataaccess.api.ApplicationPersistenceEntity;
import com.devonfw.fido.usermanagement.common.api.Authenticator;
import com.webauthn4j.data.AuthenticatorTransport;
import com.webauthn4j.data.attestation.authenticator.AttestedCredentialData;
import com.webauthn4j.data.attestation.statement.AttestationStatement;
import com.webauthn4j.data.extension.authenticator.AuthenticationExtensionsAuthenticatorOutputs;
import com.webauthn4j.data.extension.authenticator.RegistrationExtensionAuthenticatorOutput;
import com.webauthn4j.data.extension.client.AuthenticationExtensionsClientOutputs;
import com.webauthn4j.data.extension.client.RegistrationExtensionClientOutput;
import com.webauthn4j.springframework.security.authenticator.WebAuthnAuthenticator;

/**
 * Authenticator model
 */
@Entity
@Table(name = "m_authenticator")
@Access(AccessType.FIELD)
public class AuthenticatorEntity extends ApplicationPersistenceEntity implements WebAuthnAuthenticator, Authenticator {

  private String name;

  private UserEntity userEntity;

  private long counter;

  private Set<AuthenticatorTransport> transports;

  private AttestedCredentialData attestedCredentialData = new AttestedCredentialData();

  private AttestationStatement attestationStatement;

  private AuthenticationExtensionsClientOutputs<RegistrationExtensionClientOutput> clientExtensions;

  private AuthenticationExtensionsAuthenticatorOutputs<RegistrationExtensionAuthenticatorOutput> authenticatorExtensions;

  private static final long serialVersionUID = 1L;

  public String getFormat() {

    return this.attestationStatement.getFormat();
  }

  @Override
  public String getName() {

    return this.name;
  }

  @Override
  public UserDetails getUserPrincipal() {

    return getUser();
  }

  @Override
  public void setName(String name) {

    this.name = name;
  }

  @SuppressWarnings("javadoc")
  @ManyToOne
  @Access(AccessType.PROPERTY)
  public UserEntity getUser() {

    return this.userEntity;
  }

  @SuppressWarnings("javadoc")
  public void setUser(UserEntity user) {

    this.userEntity = user;
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
  @Access(AccessType.PROPERTY)
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "m_transport", joinColumns = @JoinColumn(name = "authenticator_id"))
  @Column(name = "transport")
  @Convert(converter = AuthenticatorTransportConverter.class)
  public Set<AuthenticatorTransport> getTransports() {

    return this.transports;
  }

  @Override
  public void setTransports(Set<AuthenticatorTransport> transports) {

    this.transports = transports;
  }

  @Override
  @Lob
  @Convert(converter = AttestationStatementConverter.class)
  public AttestationStatement getAttestationStatement() {

    return this.attestationStatement;
  }

  @Override
  public void setAttestationStatement(AttestationStatement attestationStatement) {

    this.attestationStatement = attestationStatement;
  }

  @Override
  @Lob
  @Convert(converter = ClientExtensionsConverter.class)
  public AuthenticationExtensionsClientOutputs<RegistrationExtensionClientOutput> getClientExtensions() {

    return this.clientExtensions;
  }

  @Override
  public void setClientExtensions(
      AuthenticationExtensionsClientOutputs<RegistrationExtensionClientOutput> clientExtensions) {

    this.clientExtensions = clientExtensions;
  }

  @Override
  @Lob
  @Convert(converter = AuthenticatorExtensionsConverter.class)
  public AuthenticationExtensionsAuthenticatorOutputs<RegistrationExtensionAuthenticatorOutput> getAuthenticatorExtensions() {

    return this.authenticatorExtensions;
  }

  @Override
  public void setAuthenticatorExtensions(
      AuthenticationExtensionsAuthenticatorOutputs<RegistrationExtensionAuthenticatorOutput> authenticatorExtensions) {

    this.authenticatorExtensions = authenticatorExtensions;
  }

  @Override
  @Transient
  public Long getUserId() {

    if (this.userEntity == null) {
      return null;
    }
    return this.userEntity.getId();
  }

  @Override
  public void setUserId(Long userId) {

    if (userId == null) {
      this.userEntity = null;
    } else {
      UserEntity userEntity = new UserEntity();
      userEntity.setId(userId);
      this.userEntity = userEntity;
    }
  }

  @Override
  @Embedded
  @AttributeOverrides({ @AttributeOverride(name = "aaguid", column = @Column(columnDefinition = "blob")),
  @AttributeOverride(name = "credentialId", column = @Column(columnDefinition = "blob")),
  @AttributeOverride(name = "coseKey", column = @Column(name = "cose_key", columnDefinition = "blob")) })
  @Converts({ @Convert(converter = AAGUIDConverter.class, attributeName = "aaguid"),
  @Convert(converter = COSEKeyConverter.class, attributeName = "coseKey") })
  public AttestedCredentialData getAttestedCredentialData() {

    return this.attestedCredentialData;
  }

}
