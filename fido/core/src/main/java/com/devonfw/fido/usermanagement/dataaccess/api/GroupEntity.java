package com.devonfw.fido.usermanagement.dataaccess.api;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.devonfw.fido.general.dataaccess.api.ApplicationPersistenceEntity;
import com.devonfw.fido.usermanagement.common.api.Group;

/**
 * Group model
 */
@Entity
@Table(name = "m_group")
public class GroupEntity extends ApplicationPersistenceEntity implements Serializable, Group {

  private String groupName;

  private List<UserEntity> users;

  private List<AuthorityEntity> authorities;

  private static final long serialVersionUID = 1L;

  public GroupEntity(String groupName) {

    this.groupName = groupName;
  }

  public GroupEntity() {

  }

  /**
   * String representation of the group
   *
   * @return group name
   */
  @Override
  public String toString() {

    return this.groupName;
  }

  @Override
  @Column(name = "group_name")
  public String getGroupName() {

    return this.groupName;
  }

  @Override
  public void setGroupName(String groupName) {

    this.groupName = groupName;
  }

  @ManyToMany
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinTable(name = "r_user_group", joinColumns = {
  @JoinColumn(name = "group_id", referencedColumnName = "id") }, inverseJoinColumns = {
  @JoinColumn(name = "user_id", referencedColumnName = "id") })
  public List<UserEntity> getUsers() {

    return this.users;
  }

  public void setUsers(List<UserEntity> users) {

    this.users = users;
  }

  @ManyToMany
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinTable(name = "r_group_authority", joinColumns = {
  @JoinColumn(name = "group_id", referencedColumnName = "id") }, inverseJoinColumns = {
  @JoinColumn(name = "authority_id", referencedColumnName = "id") })
  public List<AuthorityEntity> getAuthorities() {

    return this.authorities;
  }

  public void setAuthorities(List<AuthorityEntity> authorities) {

    this.authorities = authorities;
  }

}
