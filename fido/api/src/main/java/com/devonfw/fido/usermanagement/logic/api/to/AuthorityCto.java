package com.devonfw.fido.usermanagement.logic.api.to;

import java.util.List;

import com.devonfw.module.basic.common.api.to.AbstractCto;

/**
 * Composite transport object of Authority
 */
public class AuthorityCto extends AbstractCto {

  private static final long serialVersionUID = 1L;

  private AuthorityEto authority;

  private List<UserEto> users;

  private List<GroupEto> groups;

  public AuthorityEto getAuthority() {

    return this.authority;
  }

  public void setAuthority(AuthorityEto authority) {

    this.authority = authority;
  }

  public List<UserEto> getUsers() {

    return this.users;
  }

  public void setUsers(List<UserEto> users) {

    this.users = users;
  }

  public List<GroupEto> getGroups() {

    return this.groups;
  }

  public void setGroups(List<GroupEto> groups) {

    this.groups = groups;
  }

}
