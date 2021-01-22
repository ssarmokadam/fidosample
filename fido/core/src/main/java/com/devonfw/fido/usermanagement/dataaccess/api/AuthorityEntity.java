package com.devonfw.fido.usermanagement.dataaccess.api;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;

import com.devonfw.fido.general.dataaccess.api.ApplicationPersistenceEntity;
import com.devonfw.fido.usermanagement.common.api.Authority;

/**
 * Authority model
 */
@SuppressWarnings("WeakerAccess")
@Entity
@Table(name = "m_authority")
public class AuthorityEntity extends ApplicationPersistenceEntity implements GrantedAuthority, Authority {

  private List<UserEntity> users;

  private List<GroupEntity> groups;

  private String authority;

  private static final long serialVersionUID = 1L;

  public AuthorityEntity() {

    // NOP
  }

  public AuthorityEntity(Long id, String authority) {

    setId(id);
    this.authority = authority;
  }

  @ManyToMany
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinTable(name = "r_user_authority", joinColumns = {
  @JoinColumn(name = "authority_id", referencedColumnName = "id") }, inverseJoinColumns = {
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
  @JoinColumn(name = "authority_id", referencedColumnName = "id") }, inverseJoinColumns = {
  @JoinColumn(name = "group_id", referencedColumnName = "id") })
  public List<GroupEntity> getGroups() {

    return this.groups;
  }

  public void setGroups(List<GroupEntity> groups) {

    this.groups = groups;
  }

  @Override
  @Column(name = "authority")
  public String getAuthority() {

    return this.authority;
  }

  @Override
  public void setAuthority(String authority) {

    this.authority = authority;
  }

  @Override
  public String toString() {

    return this.authority;
  }

}
