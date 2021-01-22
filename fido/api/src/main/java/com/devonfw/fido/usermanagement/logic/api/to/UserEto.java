package com.devonfw.fido.usermanagement.logic.api.to;

import com.devonfw.fido.usermanagement.common.api.User;
import com.devonfw.module.basic.common.api.to.AbstractEto;

/**
 * Entity transport object of User
 */
public class UserEto extends AbstractEto implements User {

  private static final long serialVersionUID = 1L;

  private Long id;

  private byte[] userHandle;

  private String firstName;

  private String lastName;

  private String emailAddress;

  private String password;

  private boolean locked;

  @Override
  public Long getId() {

    return this.id;
  }

  @Override
  public void setId(Long id) {

    this.id = id;
  }

  @Override
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
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
    result = prime * result + ((this.userHandle == null) ? 0 : this.userHandle.hashCode());
    result = prime * result + ((this.firstName == null) ? 0 : this.firstName.hashCode());
    result = prime * result + ((this.lastName == null) ? 0 : this.lastName.hashCode());
    result = prime * result + ((this.emailAddress == null) ? 0 : this.emailAddress.hashCode());

    result = prime * result + ((this.password == null) ? 0 : this.password.hashCode());
    result = prime * result + ((Boolean) this.locked).hashCode();
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
    UserEto other = (UserEto) obj;
    if (this.id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!this.id.equals(other.id)) {
      return false;
    }
    if (this.userHandle == null) {
      if (other.userHandle != null) {
        return false;
      }
    } else if (!this.userHandle.equals(other.userHandle)) {
      return false;
    }
    if (this.firstName == null) {
      if (other.firstName != null) {
        return false;
      }
    } else if (!this.firstName.equals(other.firstName)) {
      return false;
    }
    if (this.lastName == null) {
      if (other.lastName != null) {
        return false;
      }
    } else if (!this.lastName.equals(other.lastName)) {
      return false;
    }
    if (this.emailAddress == null) {
      if (other.emailAddress != null) {
        return false;
      }
    } else if (!this.emailAddress.equals(other.emailAddress)) {
      return false;
    }

    if (this.password == null) {
      if (other.password != null) {
        return false;
      }
    } else if (!this.password.equals(other.password)) {
      return false;
    }
    if (this.locked != other.locked) {
      return false;
    }
    return true;
  }

}
