package com.devonfw.fido.usermanagement.dataaccess.api.repo;

import static com.querydsl.core.alias.Alias.$;

import java.util.Iterator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.devonfw.fido.usermanagement.dataaccess.api.UserEntity;
import com.devonfw.fido.usermanagement.logic.api.to.UserSearchCriteriaTo;
import com.devonfw.module.jpa.dataaccess.api.QueryUtil;
import com.devonfw.module.jpa.dataaccess.api.data.DefaultRepository;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * {@link DefaultRepository} for {@link UserEntity}
 */
public interface UserRepository extends DefaultRepository<UserEntity> {

  /**
   * @param criteria the {@link UserSearchCriteriaTo} with the criteria to search.
   * @return the {@link Page} of the {@link UserEntity} objects that matched the search. If no pageable is set, it will
   *         return a unique page with all the objects that matched the search.
   */
  default Page<UserEntity> findByCriteria(UserSearchCriteriaTo criteria) {

    UserEntity alias = newDslAlias();
    JPAQuery<UserEntity> query = newDslQuery(alias);

    Long id = criteria.getId();
    if (id != null) {
      query.where($(alias.getId()).eq(id));
    }
    byte[] userHandle = criteria.getUserHandle();
    if (userHandle != null) {
      query.where($(alias.getUserHandle()).eq(userHandle));
    }
    String firstName = criteria.getFirstName();
    if (firstName != null && !firstName.isEmpty()) {
      QueryUtil.get().whereString(query, $(alias.getFirstName()), firstName, criteria.getFirstNameOption());
    }
    String lastName = criteria.getLastName();
    if (lastName != null && !lastName.isEmpty()) {
      QueryUtil.get().whereString(query, $(alias.getLastName()), lastName, criteria.getLastNameOption());
    }
    String emailAddress = criteria.getEmailAddress();
    if (emailAddress != null && !emailAddress.isEmpty()) {
      QueryUtil.get().whereString(query, $(alias.getEmailAddress()), emailAddress, criteria.getEmailAddressOption());
    }
    String password = criteria.getPassword();
    if (password != null && !password.isEmpty()) {
      QueryUtil.get().whereString(query, $(alias.getPassword()), password, criteria.getPasswordOption());
    }
    Boolean locked = criteria.getLocked();
    if (locked != null) {
      query.where($(alias.isLocked()).eq(locked));
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
  public default void addOrderBy(JPAQuery<UserEntity> query, UserEntity alias, Sort sort) {

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
          // case "userHandle":
          // if (next.isAscending()) {
          // query.orderBy($(alias.getUserHandle()).asc());
          // } else {
          // query.orderBy($(alias.getUserHandle()).desc());
          // }
          // break;
          case "firstName":
            if (next.isAscending()) {
              query.orderBy($(alias.getFirstName()).asc());
            } else {
              query.orderBy($(alias.getFirstName()).desc());
            }
            break;
          case "lastName":
            if (next.isAscending()) {
              query.orderBy($(alias.getLastName()).asc());
            } else {
              query.orderBy($(alias.getLastName()).desc());
            }
            break;
          case "emailAddress":
            if (next.isAscending()) {
              query.orderBy($(alias.getEmailAddress()).asc());
            } else {
              query.orderBy($(alias.getEmailAddress()).desc());
            }
            break;
          case "password":
            if (next.isAscending()) {
              query.orderBy($(alias.getPassword()).asc());
            } else {
              query.orderBy($(alias.getPassword()).desc());
            }
            break;
          case "locked":
            if (next.isAscending()) {
              query.orderBy($(alias.isLocked()).asc());
            } else {
              query.orderBy($(alias.isLocked()).desc());
            }
            break;
          default:
            throw new IllegalArgumentException("Sorted by the unknown property '" + next.getProperty() + "'");
        }
      }
    }
  }

  default UserEntity getUserByEmailAddress(String emailAddress) {

    UserSearchCriteriaTo criteria = new UserSearchCriteriaTo();
    criteria.setEmailAddress(emailAddress);
    UserEntity alias = newDslAlias();
    JPAQuery<UserEntity> query = newDslQuery(alias);

    if (emailAddress != null && !emailAddress.isEmpty()) {
      QueryUtil.get().whereString(query, $(alias.getEmailAddress()), emailAddress, criteria.getEmailAddressOption());
    }
    criteria.setPageable(PageRequest.of(0, Integer.MAX_VALUE));

    Page<UserEntity> userEntityPage = QueryUtil.get().findPaginated(criteria.getPageable(), query, true);
    return userEntityPage.getContent().get(0);
  }
}