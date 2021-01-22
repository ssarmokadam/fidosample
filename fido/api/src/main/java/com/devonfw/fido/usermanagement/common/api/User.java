package com.devonfw.fido.usermanagement.common.api;

import com.devonfw.fido.general.common.api.ApplicationEntity;

public interface User extends ApplicationEntity {

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
   * @return userHandleId
   */
  public byte[] getUserHandle();

  /**
   * @param userHandle setter for userHandle attribute
   */
  public void setUserHandle(byte[] userHandle);

  /**
   * @return firstNameId
   */
  public String getFirstName();

  /**
   * @param firstName setter for firstName attribute
   */
  public void setFirstName(String firstName);

  /**
   * @return lastNameId
   */
  public String getLastName();

  /**
   * @param lastName setter for lastName attribute
   */
  public void setLastName(String lastName);

  /**
   * @return emailAddressId
   */
  public String getEmailAddress();

  /**
   * @param emailAddress setter for emailAddress attribute
   */
  public void setEmailAddress(String emailAddress);

  /**
   * @return passwordId
   */
  public String getPassword();

  /**
   * @param password setter for password attribute
   */
  public void setPassword(String password);

  /**
   * @return lockedId
   */
  public boolean isLocked();

  /**
   * @param locked setter for locked attribute
   */
  public void setLocked(boolean locked);

}
