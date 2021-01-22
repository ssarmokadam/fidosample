/**Copyright 2002-2019 the original author or authors.**Licensed under the Apache License,Version 2.0(the"License");*you may not use this file except in compliance with the License.*You may obtain a copy of the License at**http://www.apache.org/licenses/LICENSE-2.0
**Unless required by applicable law or agreed to in writing,software*distributed under the License is distributed on an"AS IS"BASIS,*WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,either express or implied.*See the License for the specific language governing permissions and*limitations under the License.*/

package com.devonfw.fido.usermanagement.service.api.rest;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.devonfw.fido.usermanagement.logic.api.to.AuthorityCto;
import com.devonfw.fido.usermanagement.logic.api.to.AuthorityUpdateDto;
import com.devonfw.fido.usermanagement.logic.api.to.GroupCto;
import com.devonfw.fido.usermanagement.logic.api.to.UserCto;

/*** Authority service */

public interface AuthorityService {
  Page<AuthorityCto> findAllByKeyword(Pageable pageable, String keyword);

  AuthorityCto findOne(Long authorityId);

  List<AuthorityCto> findAll();

  Page<AuthorityCto> findAll(Pageable pageable);

  AuthorityCto update(AuthorityCto authorityEntity);

  AuthorityCto update(AuthorityUpdateDto authorityUpdateDto);

  Page<UserCto> findAllCandidateUsersByKeyword(Pageable pageable, String keyword);

  Page<GroupCto> findAllCandidateGroupsByKeyword(Pageable pageable, String keyword);
}