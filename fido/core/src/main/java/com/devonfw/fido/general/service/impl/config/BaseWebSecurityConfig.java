package com.devonfw.fido.general.service.impl.config;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.devonfw.fido.general.service.impl.filter.FidoServerAssertionOptionsEndpointFilter;
import com.devonfw.fido.general.service.impl.filter.FidoServerAssertionResultEndpointFilter;
import com.devonfw.fido.general.service.impl.filter.FidoServerAttestationOptionsEndpointFilter;
import com.devonfw.fido.general.service.impl.filter.FidoServerAttestationResultEndpointFilter;
import com.devonfw.fido.general.service.impl.filter.SampleUsernameNotFoundHandler;
import com.devonfw.fido.general.util.AssertionOptionsProvider;
import com.devonfw.fido.general.util.AttestationOptionsProvider;
import com.devonfw.fido.usermanagement.logic.api.UserManager;
import com.webauthn4j.WebAuthnManager;
import com.webauthn4j.converter.util.ObjectConverter;
import com.webauthn4j.data.PublicKeyCredentialParameters;
import com.webauthn4j.data.PublicKeyCredentialType;
import com.webauthn4j.data.attestation.statement.COSEAlgorithmIdentifier;
import com.webauthn4j.springframework.security.WebAuthnRegistrationRequestValidator;
import com.webauthn4j.springframework.security.authenticator.WebAuthnAuthenticatorManager;
import com.webauthn4j.springframework.security.authenticator.WebAuthnAuthenticatorService;
import com.webauthn4j.springframework.security.challenge.ChallengeRepository;
import com.webauthn4j.springframework.security.config.configurers.WebAuthnAuthenticationProviderConfigurer;
import com.webauthn4j.springframework.security.config.configurers.WebAuthnLoginConfigurer;
import com.webauthn4j.springframework.security.server.ServerPropertyProvider;

/**
 * This type serves as a base class for extensions of the {@code WebSecurityConfigurerAdapter} and provides a default
 * configuration. <br/>
 * Security configuration is based on {@link WebSecurityConfigurerAdapter}. This configuration is by purpose designed
 * most simple for two channels of authentication: simple login form and rest-url.
 */
public abstract class BaseWebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${security.cors.enabled}")
  boolean corsEnabled = false;

  @Inject
  private WebAuthnAuthenticatorService authenticatorService;

  @Inject
  private WebAuthnManager webAuthnManager;

  @Inject
  private WebAuthnRegistrationRequestValidator webAuthnRegistrationRequestValidator;

  @Inject
  private UserManager userDetailsManager;

  @Inject
  private ObjectConverter objectConverter;

  @Inject
  private AttestationOptionsProvider attestationOptionsProvider;

  @Inject
  private AssertionOptionsProvider assertionOptionsProvider;

  @Inject
  private ServerPropertyProvider serverPropertyProvider;

  @Inject
  private WebAuthnAuthenticatorManager webAuthnAuthenticatorManager;

  @Inject
  private ChallengeRepository challengeRepository;

  @Inject
  private UserDetailsService userDetailsService;

  @Inject
  private PasswordEncoder passwordEncoder;

  @Override
  public void configure(AuthenticationManagerBuilder builder) throws Exception {

    builder.apply(new WebAuthnAuthenticationProviderConfigurer<>(this.authenticatorService, this.webAuthnManager));
    builder.userDetailsService(this.userDetailsService).passwordEncoder(this.passwordEncoder);
  }

  private CorsFilter getCorsFilter() {

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("OPTIONS");
    config.addAllowedMethod("HEAD");
    config.addAllowedMethod("GET");
    config.addAllowedMethod("PUT");
    config.addAllowedMethod("POST");
    config.addAllowedMethod("DELETE");
    config.addAllowedMethod("PATCH");
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

  /**
   * Configure spring security to enable a simple webform-login + a simple rest login.
   */
  @Override
  public void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/api/auth/status").permitAll()
        .antMatchers(HttpMethod.GET, "/login").permitAll().antMatchers(HttpMethod.POST, "/api/profile").permitAll()
        .antMatchers("/health/**").permitAll().and().formLogin();

    // WebAuthn Login
    http.apply(WebAuthnLoginConfigurer.webAuthnLogin()).attestationOptionsEndpoint().rp()
        .name("WebAuthn4J Spring Security Sample").and()
        .pubKeyCredParams(
            new PublicKeyCredentialParameters(PublicKeyCredentialType.PUBLIC_KEY, COSEAlgorithmIdentifier.ES256),
            new PublicKeyCredentialParameters(PublicKeyCredentialType.PUBLIC_KEY, COSEAlgorithmIdentifier.RS1))
        .extensions().entry("example.extension", "test").and().assertionOptionsEndpoint().extensions()
        .entry("example.extension", "test").and();

    FidoServerAttestationOptionsEndpointFilter fidoServerAttestationOptionsEndpointFilter = new FidoServerAttestationOptionsEndpointFilter(
        this.objectConverter, this.attestationOptionsProvider, this.challengeRepository);
    FidoServerAttestationResultEndpointFilter fidoServerAttestationResultEndpointFilter = new FidoServerAttestationResultEndpointFilter(
        this.objectConverter, this.userDetailsService, this.webAuthnAuthenticatorManager,
        this.webAuthnRegistrationRequestValidator);
    fidoServerAttestationResultEndpointFilter
        .setUsernameNotFoundHandler(new SampleUsernameNotFoundHandler(this.userDetailsManager));
    FidoServerAssertionOptionsEndpointFilter fidoServerAssertionOptionsEndpointFilter = new FidoServerAssertionOptionsEndpointFilter(
        this.objectConverter, this.assertionOptionsProvider, this.challengeRepository);
    FidoServerAssertionResultEndpointFilter fidoServerAssertionResultEndpointFilter = new FidoServerAssertionResultEndpointFilter(
        this.objectConverter, this.serverPropertyProvider);
    fidoServerAssertionResultEndpointFilter.setAuthenticationManager(authenticationManagerBean());

    http.addFilterAfter(fidoServerAttestationOptionsEndpointFilter, SessionManagementFilter.class);
    http.addFilterAfter(fidoServerAttestationResultEndpointFilter, SessionManagementFilter.class);
    http.addFilterAfter(fidoServerAssertionOptionsEndpointFilter, SessionManagementFilter.class);
    http.addFilterAfter(fidoServerAssertionResultEndpointFilter, SessionManagementFilter.class);
  }

}
