package com.devonfw.fido.usermanagement.logic.api.to;

import java.util.List;

import com.devonfw.module.basic.common.api.to.AbstractCto;

/**
 * Composite transport object of User
 */
public class UserCto extends AbstractCto {

  private static final long serialVersionUID = 1L;

  private UserEto user;

  private List<GroupEto> groups;

  private List<AuthorityEto> authorities;

  private List<AuthenticatorEto> authenticators;

  public UserEto getUser() {

    return this.user;
  }

  public void setUser(UserEto user) {

    this.user = user;
  }

  public List<GroupEto> getGroups() {

    return this.groups;
  }

  public void setGroups(List<GroupEto> groups) {

    this.groups = groups;
  }

  public List<AuthorityEto> getAuthorities() {

    return this.authorities;
  }

  public void setAuthorities(List<AuthorityEto> authorities) {

    this.authorities = authorities;
  }

  public List<AuthenticatorEto> getAuthenticators() {

    return this.authenticators;
  }

  public void setAuthenticators(List<AuthenticatorEto> authenticators) {

    this.authenticators = authenticators;
  }

}
