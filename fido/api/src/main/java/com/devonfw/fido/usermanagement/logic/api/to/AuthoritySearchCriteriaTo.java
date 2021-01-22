package com.devonfw.fido.usermanagement.logic.api.to;

import com.devonfw.fido.general.common.api.to.AbstractSearchCriteriaTo;
import com.devonfw.module.basic.common.api.query.StringSearchConfigTo;

/**
 * {@link SearchCriteriaTo} to find instances of {@link com.cg.webauthndemo.usermanagement.common.api.Authority}s.
 */
public class AuthoritySearchCriteriaTo extends AbstractSearchCriteriaTo {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String authority;

  private StringSearchConfigTo authorityOption;

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
   * @return authorityId
   */
  public String getAuthority() {

    return this.authority;
  }

  /**
   * @param authority setter for authority attribute
   */
  public void setAuthority(String authority) {

    this.authority = authority;
  }

  /**
   * @return the {@link StringSearchConfigTo} used to search for {@link #getAuthority() authority}.
   */
  public StringSearchConfigTo getAuthorityOption() {

    return this.authorityOption;
  }

  /**
   * @param authorityOption new value of {@link #getAuthorityOption()}.
   */
  public void setAuthorityOption(StringSearchConfigTo authorityOption) {

    this.authorityOption = authorityOption;
  }

}
