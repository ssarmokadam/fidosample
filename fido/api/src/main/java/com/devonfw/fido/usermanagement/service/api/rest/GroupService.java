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

package com.devonfw.fido.usermanagement.service.api.rest;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.devonfw.fido.usermanagement.logic.api.to.GroupCto;

/**
 * Group service
 */
public interface GroupService {

  /**
   * find one group
   *
   * @param id groupId
   * @return group
   */
  GroupCto findOne(long id);

  /**
   * find all groups
   *
   * @return group list
   */
  List<GroupCto> findAll();

  /**
   * find all groups with paging
   *
   * @param pageable paging info
   * @return group list
   */
  Page<GroupCto> findAll(Pageable pageable);

  /**
   * find all groups by keyword
   *
   * @param pageable paging info
   * @param keyword keyword
   * @return group list
   */
  Page<GroupCto> findAllByKeyword(Pageable pageable, String keyword);

  /**
   * create a groupEntity
   *
   * @param groupEntity groupEntity
   * @return created groupEntity
   */
  GroupCto create(GroupCto groupEntity);

  /**
   * update the specified groupEntity
   *
   * @param groupEntity groupEntity
   * @return updated groupEntity
   */
  GroupCto update(GroupCto groupEntity);

  /**
   * delete the specified group
   *
   * @param id groupId
   */
  void delete(long id);

}
