package com.devonfw.fido.usermanagement.logic.api.to;

import com.devonfw.fido.general.common.api.to.AbstractSearchCriteriaTo;
import com.devonfw.module.basic.common.api.query.StringSearchConfigTo;

/**
 * {@link SearchCriteriaTo} to find instances of {@link com.cg.webauthndemo.usermanagement.common.api.Group}s.
 */
public class GroupSearchCriteriaTo extends AbstractSearchCriteriaTo {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String groupName;

  private StringSearchConfigTo groupNameOption;

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
   * @return groupNameId
   */
  public String getGroupName() {

    return this.groupName;
  }

  /**
   * @param groupName setter for groupName attribute
   */
  public void setGroupName(String groupName) {

    this.groupName = groupName;
  }

  /**
   * @return the {@link StringSearchConfigTo} used to search for {@link #getGroupName() groupName}.
   */
  public StringSearchConfigTo getGroupNameOption() {

    return this.groupNameOption;
  }

  /**
   * @param groupNameOption new value of {@link #getGroupNameOption()}.
   */
  public void setGroupNameOption(StringSearchConfigTo groupNameOption) {

    this.groupNameOption = groupNameOption;
  }

}
