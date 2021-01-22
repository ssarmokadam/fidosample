package com.devonfw.fido.usermanagement.logic.api.to;

import com.devonfw.fido.usermanagement.common.api.Authority;
import com.devonfw.module.basic.common.api.to.AbstractEto;

/**
 * Entity transport object of Authority
 */
public class AuthorityEto extends AbstractEto implements Authority {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String authority;

  @Override
  public Long getId() {

    return this.id;
  }

  @Override
  public void setId(Long id) {

    this.id = id;
  }

  @Override
  public String getAuthority() {

    return this.authority;
  }

  @Override
  public void setAuthority(String authority) {

    this.authority = authority;
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());

    result = prime * result + ((this.authority == null) ? 0 : this.authority.hashCode());
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
    AuthorityEto other = (AuthorityEto) obj;
    if (this.id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!this.id.equals(other.id)) {
      return false;
    }

    if (this.authority == null) {
      if (other.authority != null) {
        return false;
      }
    } else if (!this.authority.equals(other.authority)) {
      return false;
    }
    return true;
  }

}
