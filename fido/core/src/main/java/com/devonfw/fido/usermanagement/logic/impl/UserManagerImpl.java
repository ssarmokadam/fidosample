/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.devonfw.fido.usermanagement.logic.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.devonfw.fido.general.common.exception.WebAuthnSampleBusinessException;
import com.devonfw.fido.general.common.exception.WebAuthnSampleEntityNotFoundException;
import com.devonfw.fido.usermanagement.dataaccess.api.UserEntity;
import com.devonfw.fido.usermanagement.dataaccess.api.repo.AuthenticatorRepository;
import com.devonfw.fido.usermanagement.dataaccess.api.repo.UserRepository;
import com.devonfw.fido.usermanagement.logic.api.UserManager;
import com.devonfw.fido.usermanagement.logic.api.to.UserCto;
import com.devonfw.fido.usermanagement.logic.api.to.UserSearchCriteriaTo;
import com.webauthn4j.springframework.security.exception.PrincipalNotFoundException;

/**
 * userDetailsService for FIDO.
 */
@Component
@Transactional
public class UserManagerImpl implements UserManager {

  private final ModelMapper modelMapper;

  private final UserRepository userEntityRepository;

  private final AuthenticatorRepository authenticatorEntityRepository;

  @Autowired
  public UserManagerImpl(ModelMapper mapper, UserRepository userEntityRepository,
      AuthenticatorRepository authenticatorEntityRepository) {

    this.modelMapper = mapper;
    this.userEntityRepository = userEntityRepository;
    this.authenticatorEntityRepository = authenticatorEntityRepository;
  }

  @Override
  public UserCto findById(Long id) {

    UserCto userCto = this.modelMapper.map(this.userEntityRepository.findById(id), UserCto.class);
    if (userCto == null) {
      throw new WebAuthnSampleEntityNotFoundException("User not found");
    }
    return userCto;

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UserEntity loadUserByUsername(String username) {

    UserSearchCriteriaTo criteria = new UserSearchCriteriaTo();
    criteria.setEmailAddress(username);
    List<UserEntity> userList = this.userEntityRepository.findByCriteria(criteria).getContent();
    if (userList.isEmpty()) {
      throw new PrincipalNotFoundException(String.format("UserEntity with username'%s' is not found.", username));
    }
    return userList.get(0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UserCto createUser(UserCto user) {

    UserSearchCriteriaTo criteria = new UserSearchCriteriaTo();
    criteria.setEmailAddress(user.getUser().getEmailAddress());
    List<UserEntity> userList = this.userEntityRepository.findByCriteria(criteria).getContent();

    if (!userList.isEmpty()) {
      throw new WebAuthnSampleBusinessException("Email address is already used.");
    }

    UserEntity userEntity = this.userEntityRepository.save(this.modelMapper.map(user, UserEntity.class));
    return this.modelMapper.map(userEntity, UserCto.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateUser(UserCto user) {

    UserEntity userEntity = this.userEntityRepository.findById(user.getUser().getId())
        .orElseThrow(() -> new WebAuthnSampleEntityNotFoundException("User not found"));
    this.userEntityRepository.save(userEntity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteUser(String username) {

    UserEntity userEntity = this.userEntityRepository.getUserByEmailAddress(username);
    if (userEntity == null) {
      throw new PrincipalNotFoundException(String.format("UserEntity with username'%s' is not found.", username));
    }
    this.userEntityRepository.delete(userEntity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteUser(Long id) {

    this.userEntityRepository.findById(id)
        .orElseThrow(() -> new WebAuthnSampleEntityNotFoundException("User not found"));
    this.userEntityRepository.deleteById(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void changePassword(String oldPassword, String newPassword) {

    UserEntity currentUserEntity = getCurrentUser();

    if (currentUserEntity == null) {
      // This would indicate bad coding somewhere
      throw new AccessDeniedException(
          "Can't change rawPassword as no Authentication object found in context " + "for current user.");
    }

    currentUserEntity.setPassword(newPassword);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean userExists(String username) {

    if (this.userEntityRepository.getUserByEmailAddress(username) != null) {
      return true;
    }
    return false;
  }

  /**
   * return current login user
   *
   * @return login user
   */
  private UserEntity getCurrentUser() {

    return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  @Override
  public List<UserCto> findAll() {

    List<UserEntity> userEntityList = this.userEntityRepository.findAll();
    List<UserCto> userCtoList = userEntityList.stream().map(user -> this.modelMapper.map(user, UserCto.class))
        .collect(Collectors.toList());
    return userCtoList;

  }

  @Override
  public Page<UserCto> findAll(Pageable pageable) {

    Page<UserEntity> userEntityList = this.userEntityRepository.findAll(pageable);
    List<UserCto> userCtoList = userEntityList.stream().map(user -> this.modelMapper.map(user, UserCto.class))
        .collect(Collectors.toList());
    return new PageImpl<>(userCtoList, pageable, userCtoList.size());
  }

}
