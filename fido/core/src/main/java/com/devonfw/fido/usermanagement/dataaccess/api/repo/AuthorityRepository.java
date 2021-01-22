package com.devonfw.fido.usermanagement.dataaccess.api.repo;

import static com.querydsl.core.alias.Alias.$;

import java.util.Iterator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.devonfw.fido.usermanagement.dataaccess.api.AuthorityEntity;
import com.devonfw.fido.usermanagement.logic.api.to.AuthoritySearchCriteriaTo;
import com.devonfw.module.jpa.dataaccess.api.QueryUtil;
import com.devonfw.module.jpa.dataaccess.api.data.DefaultRepository;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * {@link DefaultRepository} for {@link AuthorityEntity}
 */
public interface AuthorityRepository extends DefaultRepository<AuthorityEntity> {

  /**
   * @param criteria the {@link AuthoritySearchCriteriaTo} with the criteria to search.
   * @return the {@link Page} of the {@link AuthorityEntity} objects that matched the search. If no pageable is set, it
   *         will return a unique page with all the objects that matched the search.
   */
  default Page<AuthorityEntity> findByCriteria(AuthoritySearchCriteriaTo criteria) {

    AuthorityEntity alias = newDslAlias();
    JPAQuery<AuthorityEntity> query = newDslQuery(alias);

    Long id = criteria.getId();
    if (id != null) {
      query.where($(alias.getId()).eq(id));
    }
    String authority = criteria.getAuthority();
    if (authority != null && !authority.isEmpty()) {
      QueryUtil.get().whereString(query, $(alias.getAuthority()), authority, criteria.getAuthorityOption());
    }
    if (criteria.getPageable() == null) {
      criteria.setPageable(PageRequest.of(0, Integer.MAX_VALUE));
    } else {
      addOrderBy(query, alias, criteria.getPageable().getSort());
    }

    return QueryUtil.get().findPaginated(criteria.getPageable(), query, true);
  }

  /**
   * Add sorting to the given query on the given alias
   *
   * @param query to add sorting to
   * @param alias to retrieve columns from for sorting
   * @param sort specification of sorting
   */
  public default void addOrderBy(JPAQuery<AuthorityEntity> query, AuthorityEntity alias, Sort sort) {

    if (sort != null && sort.isSorted()) {
      Iterator<Order> it = sort.iterator();
      while (it.hasNext()) {
        Order next = it.next();
        switch (next.getProperty()) {
          case "id":
            if (next.isAscending()) {
              query.orderBy($(alias.getId().toString()).asc());
            } else {
              query.orderBy($(alias.getId().toString()).desc());
            }
            break;
          case "authority":
            if (next.isAscending()) {
              query.orderBy($(alias.getAuthority()).asc());
            } else {
              query.orderBy($(alias.getAuthority()).desc());
            }
            break;
          default:
            throw new IllegalArgumentException("Sorted by the unknown property '" + next.getProperty() + "'");
        }
      }
    }
  }

}