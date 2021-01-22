package com.devonfw.fido.usermanagement.common.api;

import com.devonfw.fido.general.common.api.ApplicationEntity;

public interface Group extends ApplicationEntity {

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
   * @return groupNameId
   */
  public String getGroupName();

  /**
   * @param groupName setter for groupName attribute
   */
  public void setGroupName(String groupName);

}
