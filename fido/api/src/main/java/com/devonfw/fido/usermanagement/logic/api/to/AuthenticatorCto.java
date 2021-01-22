package com.devonfw.fido.usermanagement.logic.api.to;

import com.devonfw.module.basic.common.api.to.AbstractCto;

/**
 * Composite transport object of Authenticator
 */
public class AuthenticatorCto extends AbstractCto {

  private static final long serialVersionUID = 1L;

  private AuthenticatorEto authenticator;

  private UserEto user;

  public AuthenticatorEto getAuthenticator() {

    return this.authenticator;
  }

  public void setAuthenticator(AuthenticatorEto authenticator) {

    this.authenticator = authenticator;
  }

  public UserEto getUser() {

    return this.user;
  }

  public void setUser(UserEto user) {

    this.user = user;
  }

}
