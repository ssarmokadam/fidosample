package com.devonfw.fido.usermanagement.dataaccess.api.repo;

import static com.querydsl.core.alias.Alias.$;

import java.util.Iterator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.devonfw.fido.usermanagement.dataaccess.api.GroupEntity;
import com.devonfw.fido.usermanagement.logic.api.to.GroupSearchCriteriaTo;
import com.devonfw.module.jpa.dataaccess.api.QueryUtil;
import com.devonfw.module.jpa.dataaccess.api.data.DefaultRepository;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * {@link DefaultRepository} for {@link GroupEntity}
 */
public interface GroupRepository extends DefaultRepository<GroupEntity> {

  /**
   * @param criteria the {@link GroupSearchCriteriaTo} with the criteria to search.
   * @return the {@link Page} of the {@link GroupEntity} objects that matched the search. If no pageable is set, it will
   *         return a unique page with all the objects that matched the search.
   */
  default Page<GroupEntity> findByCriteria(GroupSearchCriteriaTo criteria) {

    GroupEntity alias = newDslAlias();
    JPAQuery<GroupEntity> query = newDslQuery(alias);

    Long id = criteria.getId();
    if (id != null) {
      query.where($(alias.getId()).eq(id));
    }
    String groupName = criteria.getGroupName();
    if (groupName != null && !groupName.isEmpty()) {
      QueryUtil.get().whereString(query, $(alias.getGroupName()), groupName, criteria.getGroupNameOption());
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
  public default void addOrderBy(JPAQuery<GroupEntity> query, GroupEntity alias, Sort sort) {

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
          case "groupName":
            if (next.isAscending()) {
              query.orderBy($(alias.getGroupName()).asc());
            } else {
              query.orderBy($(alias.getGroupName()).desc());
            }
            break;
          default:
            throw new IllegalArgumentException("Sorted by the unknown property '" + next.getProperty() + "'");
        }
      }
    }
  }

}