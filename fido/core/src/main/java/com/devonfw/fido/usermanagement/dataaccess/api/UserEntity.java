package com.devonfw.fido.usermanagement.dataaccess.api;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.userdetails.UserDetails;

import com.devonfw.fido.general.dataaccess.api.ApplicationPersistenceEntity;
import com.devonfw.fido.usermanagement.common.api.User;

@Entity
@Table(name = "m_user")
@Access(AccessType.FIELD)
public class UserEntity extends ApplicationPersistenceEntity implements UserDetails, User {

  private byte[] userHandle;

  private String firstName;

  private String lastName;

  private String emailAddress;

  private List<GroupEntity> groups;

  private List<AuthorityEntity> authorities;

  private List<AuthenticatorEntity> authenticators;

  private String password;

  private boolean locked;

  private static final long serialVersionUID = 1L;

  @Override
  @Column(columnDefinition = "blob")
  public byte[] getUserHandle() {

    return this.userHandle;
  }

  @Override
  public void setUserHandle(byte[] userHandle) {

    this.userHandle = userHandle;
  }

  @Override
  public String getFirstName() {

    return this.firstName;
  }

  @Override
  public void setFirstName(String firstName) {

    this.firstName = firstName;
  }

  @Override
  public String getLastName() {

    return this.lastName;
  }

  @Override
  public void setLastName(String lastName) {

    this.lastName = lastName;
  }

  @Override
  public String getEmailAddress() {

    return this.emailAddress;
  }

  @Override
  public void setEmailAddress(String emailAddress) {

    this.emailAddress = emailAddress;
  }

  @ManyToMany
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinTable(name = "r_user_group", joinColumns = {
  @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
  @JoinColumn(name = "group_id", referencedColumnName = "id") })
  @Access(AccessType.PROPERTY)
  public List<GroupEntity> getGroups() {

    return this.groups;
  }

  public void setGroups(List<GroupEntity> groups) {

    this.groups = groups;
  }

  @Override
  @ManyToMany(cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinTable(name = "r_user_authority", joinColumns = {
  @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
  @JoinColumn(name = "authority_id", referencedColumnName = "id") })
  @Access(AccessType.PROPERTY)
  public List<AuthorityEntity> getAuthorities() {

    return this.authorities;
  }

  public void setAuthorities(List<AuthorityEntity> authorities) {

    this.authorities = authorities;
  }

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  @Access(AccessType.PROPERTY)
  public List<AuthenticatorEntity> getAuthenticators() {

    return this.authenticators;
  }

  public void setAuthenticators(List<AuthenticatorEntity> authenticators) {

    this.authenticators = authenticators;
  }

  @Override
  public String getPassword() {

    return this.password;
  }

  @Override
  public void setPassword(String password) {

    this.password = password;
  }

  @Override
  public boolean isLocked() {

    return this.locked;
  }

  @Override
  public void setLocked(boolean locked) {

    this.locked = locked;
  }

  @Override
  public String getUsername() {

    return getEmailAddress();
  }

  @Override
  public boolean isAccountNonExpired() {

    return true;
  }

  @Override
  public boolean isAccountNonLocked() {

    return !isLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {

    return true;
  }

  @Override
  public boolean isEnabled() {

    return true;
  }

  /**
   * return String representation
   */
  @Override
  public String toString() {

    return this.emailAddress;
  }

}
