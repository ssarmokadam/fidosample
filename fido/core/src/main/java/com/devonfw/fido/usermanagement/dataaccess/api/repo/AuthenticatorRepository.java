package com.devonfw.fido.usermanagement.dataaccess.api.repo;

import static com.querydsl.core.alias.Alias.$;

import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devonfw.fido.usermanagement.dataaccess.api.AuthenticatorEntity;
import com.devonfw.fido.usermanagement.logic.api.to.AuthenticatorSearchCriteriaTo;
import com.devonfw.module.jpa.dataaccess.api.QueryUtil;
import com.devonfw.module.jpa.dataaccess.api.data.DefaultRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.webauthn4j.data.attestation.authenticator.AttestedCredentialData;
import com.webauthn4j.data.attestation.statement.AttestationStatement;
import com.webauthn4j.data.extension.authenticator.AuthenticationExtensionsAuthenticatorOutputs;
import com.webauthn4j.data.extension.client.AuthenticationExtensionsClientOutputs;

/**
 * {@link DefaultRepository} for {@link AuthenticatorEntity}
 */
public interface AuthenticatorRepository extends DefaultRepository<AuthenticatorEntity> {

  /**
   * @param criteria the {@link AuthenticatorSearchCriteriaTo} with the criteria to search.
   * @return the {@link Page} of the {@link AuthenticatorEntity} objects that matched the search. If no pageable is set,
   *         it will return a unique page with all the objects that matched the search.
   */
  default Page<AuthenticatorEntity> findByCriteria(AuthenticatorSearchCriteriaTo criteria) {

    AuthenticatorEntity alias = newDslAlias();
    JPAQuery<AuthenticatorEntity> query = newDslQuery(alias);

    Long id = criteria.getId();
    if (id != null) {
      query.where($(alias.getId()).eq(id));
    }
    String name = criteria.getName();
    if (name != null && !name.isEmpty()) {
      QueryUtil.get().whereString(query, $(alias.getName()), name, criteria.getNameOption());
    }
    Long user = criteria.getUserId();
    if (user != null) {
      query.where($(alias.getUser().getId()).eq(user));
    }
    Long counter = criteria.getCounter();
    if (counter != null) {
      query.where($(alias.getCounter()).eq(counter));
    }
    AttestedCredentialData attestedCredentialData = criteria.getAttestedCredentialData();
    if (attestedCredentialData != null) {
      query.where($(alias.getAttestedCredentialData()).eq(attestedCredentialData));
    }
    AttestationStatement attestationStatement = criteria.getAttestationStatement();
    if (attestationStatement != null) {
      query.where($(alias.getAttestationStatement()).eq(attestationStatement));
    }
    AuthenticationExtensionsClientOutputs clientExtensions = criteria.getClientExtensions();
    if (clientExtensions != null) {
      query.where($(alias.getClientExtensions()).eq(clientExtensions));
    }
    AuthenticationExtensionsAuthenticatorOutputs authenticatorExtensions = criteria.getAuthenticatorExtensions();
    if (authenticatorExtensions != null) {
      query.where($(alias.getAuthenticatorExtensions()).eq(authenticatorExtensions));
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
  public default void addOrderBy(JPAQuery<AuthenticatorEntity> query, AuthenticatorEntity alias, Sort sort) {

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
          case "name":
            if (next.isAscending()) {
              query.orderBy($(alias.getName()).asc());
            } else {
              query.orderBy($(alias.getName()).desc());
            }
            break;
          case "user":
            if (next.isAscending()) {
              query.orderBy($(alias.getUser().getId().toString()).asc());
            } else {
              query.orderBy($(alias.getUser().getId().toString()).desc());
            }
            break;
          case "counter":
            if (next.isAscending()) {
              query.orderBy($(alias.getCounter()).asc());
            } else {
              query.orderBy($(alias.getCounter()).desc());
            }
            break;

          default:
            throw new IllegalArgumentException("Sorted by the unknown property '" + next.getProperty() + "'");
        }
      }
    }
  }

  // @Query(value = "SELECT authenticator FROM AuthenticatorEntity authenticator WHERE
  // authenticator.attestedCredentialData.credentialId = :credentialId")
  // Optional<AuthenticatorEntity> findByAttestedCredentialDataCredentialId(byte[] credentialId);

  @Query("SELECT a FROM AuthenticatorEntity a ,UserEntity u WHERE u.emailAddress=:emailAddress")
  List<AuthenticatorEntity> abc(@Param("emailAddress") String emailAddress);

}