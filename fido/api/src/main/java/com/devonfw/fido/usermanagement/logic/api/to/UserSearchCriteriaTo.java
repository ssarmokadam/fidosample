package com.devonfw.fido.usermanagement.logic.api.to;

import com.devonfw.fido.general.common.api.to.AbstractSearchCriteriaTo;
import com.devonfw.module.basic.common.api.query.StringSearchConfigTo;

/**
 * {@link SearchCriteriaTo} to find instances of {@link com.cg.webauthndemo.usermanagement.common.api.User}s.
 */
public class UserSearchCriteriaTo extends AbstractSearchCriteriaTo {

  private static final long serialVersionUID = 1L;

  private Long id;

  private byte[] userHandle;

  private String firstName;

  private String lastName;

  private String emailAddress;

  private String password;

  private Boolean locked;

  private StringSearchConfigTo firstNameOption;

  private StringSearchConfigTo lastNameOption;

  private StringSearchConfigTo emailAddressOption;

  private StringSearchConfigTo passwordOption;

  /**
   * @return idId
   */
  public Long getId() {

    return this.id;
  }

  /**
   * @param id setter for id attribute
   */
  public void setId(Long id) {

    this.id = id;
  }

  /**
   * @return userHandleId
   */
  public byte[] getUserHandle() {

    return this.userHandle;
  }

  /**
   * @param userHandle setter for userHandle attribute
   */
  public void setUserHandle(byte[] userHandle) {

    this.userHandle = userHandle;
  }

  /**
   * @return firstNameId
   */
  public String getFirstName() {

    return this.firstName;
  }

  /**
   * @param firstName setter for firstName attribute
   */
  public void setFirstName(String firstName) {

    this.firstName = firstName;
  }

  /**
   * @return lastNameId
   */
  public String getLastName() {

    return this.lastName;
  }

  /**
   * @param lastName setter for lastName attribute
   */
  public void setLastName(String lastName) {

    this.lastName = lastName;
  }

  /**
   * @return emailAddressId
   */
  public String getEmailAddress() {

    return this.emailAddress;
  }

  /**
   * @param emailAddress setter for emailAddress attribute
   */
  public void setEmailAddress(String emailAddress) {

    this.emailAddress = emailAddress;
  }

  /**
   * @return passwordId
   */
  public String getPassword() {

    return this.password;
  }

  /**
   * @param password setter for password attribute
   */
  public void setPassword(String password) {

    this.password = password;
  }

  /**
   * @return lockedId
   */
  public Boolean getLocked() {

    return this.locked;
  }

  /**
   * @param locked setter for locked attribute
   */
  public void setLocked(Boolean locked) {

    this.locked = locked;
  }

  /**
   * @return the {@link StringSearchConfigTo} used to search for {@link #getFirstName() firstName}.
   */
  public StringSearchConfigTo getFirstNameOption() {

    return this.firstNameOption;
  }

  /**
   * @param firstNameOption new value of {@link #getFirstNameOption()}.
   */
  public void setFirstNameOption(StringSearchConfigTo firstNameOption) {

    this.firstNameOption = firstNameOption;
  }

  /**
   * @return the {@link StringSearchConfigTo} used to search for {@link #getLastName() lastName}.
   */
  public StringSearchConfigTo getLastNameOption() {

    return this.lastNameOption;
  }

  /**
   * @param lastNameOption new value of {@link #getLastNameOption()}.
   */
  public void setLastNameOption(StringSearchConfigTo lastNameOption) {

    this.lastNameOption = lastNameOption;
  }

  /**
   * @return the {@link StringSearchConfigTo} used to search for {@link #getEmailAddress() emailAddress}.
   */
  public StringSearchConfigTo getEmailAddressOption() {

    return this.emailAddressOption;
  }

  /**
   * @param emailAddressOption new value of {@link #getEmailAddressOption()}.
   */
  public void setEmailAddressOption(StringSearchConfigTo emailAddressOption) {

    this.emailAddressOption = emailAddressOption;
  }

  /**
   * @return the {@link StringSearchConfigTo} used to search for {@link #getPassword() password}.
   */
  public StringSearchConfigTo getPasswordOption() {

    return this.passwordOption;
  }

  /**
   * @param passwordOption new value of {@link #getPasswordOption()}.
   */
  public void setPasswordOption(StringSearchConfigTo passwordOption) {

    this.passwordOption = passwordOption;
  }

}
