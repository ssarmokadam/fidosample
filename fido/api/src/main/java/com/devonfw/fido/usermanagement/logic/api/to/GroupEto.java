package com.devonfw.fido.usermanagement.logic.api.to;

import com.devonfw.fido.usermanagement.common.api.Group;
import com.devonfw.module.basic.common.api.to.AbstractEto;

/**
 * Entity transport object of Group
 */
public class GroupEto extends AbstractEto implements Group {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String groupName;

  @Override
  public Long getId() {

    return this.id;
  }

  @Override
  public void setId(Long id) {

    this.id = id;
  }

  @Override
  public String getGroupName() {

    return this.groupName;
  }

  @Override
  public void setGroupName(String groupName) {

    this.groupName = groupName;
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
    result = prime * result + ((this.groupName == null) ? 0 : this.groupName.hashCode());

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
    GroupEto other = (GroupEto) obj;
    if (this.id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!this.id.equals(other.id)) {
      return false;
    }
    if (this.groupName == null) {
      if (other.groupName != null) {
        return false;
      }
    } else if (!this.groupName.equals(other.groupName)) {
      return false;
    }

    return true;
  }

}
