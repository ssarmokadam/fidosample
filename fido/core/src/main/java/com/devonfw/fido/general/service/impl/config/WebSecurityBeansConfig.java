package com.devonfw.fido.general.service.impl.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.ForwardLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import com.devonfw.fido.general.logic.impl.config.DefaultRolesPrefixPostProcessor;
import com.devonfw.fido.general.util.AssertionOptionsProvider;
import com.devonfw.fido.general.util.AssertionOptionsProviderImpl;
import com.devonfw.fido.general.util.AttestationOptionsProvider;
import com.devonfw.fido.general.util.AttestationOptionsProviderImpl;
import com.devonfw.fido.usermanagement.logic.api.UserManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import com.webauthn4j.WebAuthnManager;
import com.webauthn4j.converter.util.ObjectConverter;
import com.webauthn4j.metadata.converter.jackson.WebAuthnMetadataJSONModule;
import com.webauthn4j.springframework.security.WebAuthnRegistrationRequestValidator;
import com.webauthn4j.springframework.security.WebAuthnSecurityExpression;
import com.webauthn4j.springframework.security.authenticator.WebAuthnAuthenticatorService;
import com.webauthn4j.springframework.security.challenge.ChallengeRepository;
import com.webauthn4j.springframework.security.challenge.HttpSessionChallengeRepository;
import com.webauthn4j.springframework.security.converter.jackson.WebAuthn4JSpringSecurityJSONModule;
import com.webauthn4j.springframework.security.options.PublicKeyCredentialUserEntityProvider;
import com.webauthn4j.springframework.security.options.RpIdProvider;
import com.webauthn4j.springframework.security.options.RpIdProviderImpl;
import com.webauthn4j.springframework.security.server.ServerPropertyProvider;
import com.webauthn4j.springframework.security.server.ServerPropertyProviderImpl;

/**
 * This configuration class provides factory methods for several Spring security related beans.
 */
@Configuration
public class WebSecurityBeansConfig {

  /**
   * This method provides a new instance of {@code CsrfTokenRepository}
   *
   * @return the newly created {@code CsrfTokenRepository}
   */
  @Bean
  public CsrfTokenRepository csrfTokenRepository() {

    return new HttpSessionCsrfTokenRepository();
  }

  /**
   * This method provides a new instance of {@code DefaultRolesPrefixPostProcessor}
   *
   * @return the newly create {@code DefaultRolesPrefixPostProcessor}
   */
  @Bean
  public static DefaultRolesPrefixPostProcessor defaultRolesPrefixPostProcessor() {

    // By default Spring-Security is setting the prefix "ROLE_" for all permissions/authorities.
    // We disable this undesired behavior here...
    return new DefaultRolesPrefixPostProcessor("");
  }

  /**
   * This method provide a new instance of {@code DelegatingPasswordEncoder}
   *
   * @return the newly create {@code DelegatingPasswordEncoder}
   */
  @Bean
  public PasswordEncoder passwordEncoder() {

    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  // START: FIDO2
  @Bean
  public WebAuthnRegistrationRequestValidator webAuthnRegistrationRequestValidator(WebAuthnManager webAuthnManager,
      ServerPropertyProvider serverPropertyProvider) {

    return new WebAuthnRegistrationRequestValidator(webAuthnManager, serverPropertyProvider);
  }

  @Bean
  public AuthenticationTrustResolver authenticationTrustResolver() {

    return new AuthenticationTrustResolverImpl();
  }

  @Bean
  public ChallengeRepository challengeRepository() {

    return new HttpSessionChallengeRepository();
  }

  @Bean
  public AttestationOptionsProvider attestationOptionsProvider(RpIdProvider rpIdProvider,
      WebAuthnAuthenticatorService webAuthnAuthenticatorService, ChallengeRepository challengeRepository,
      PublicKeyCredentialUserEntityProvider publicKeyCredentialUserEntityProvider) {

    AttestationOptionsProviderImpl optionsProviderImpl = new AttestationOptionsProviderImpl(rpIdProvider,
        webAuthnAuthenticatorService, challengeRepository);
    optionsProviderImpl.setPublicKeyCredentialUserEntityProvider(publicKeyCredentialUserEntityProvider);
    return optionsProviderImpl;
  }

  @Bean
  public AssertionOptionsProvider assertionOptionsProvider(RpIdProvider rpIdProvider,
      WebAuthnAuthenticatorService webAuthnAuthenticatorService, ChallengeRepository challengeRepository) {

    return new AssertionOptionsProviderImpl(rpIdProvider, webAuthnAuthenticatorService, challengeRepository);
  }

  @Bean
  public RpIdProvider rpIdProvider() {

    return new RpIdProviderImpl();
  }

  @Bean
  public ServerPropertyProvider serverPropertyProvider(RpIdProvider rpIdProvider,
      ChallengeRepository challengeRepository) {

    return new ServerPropertyProviderImpl(rpIdProvider, challengeRepository);
  }

  @Bean
  public WebAuthnManager webAuthnManager(ObjectConverter objectConverter) {

    return WebAuthnManager.createNonStrictWebAuthnManager(objectConverter);
  }

  @Bean
  public WebAuthnSecurityExpression webAuthnSecurityExpression() {

    return new WebAuthnSecurityExpression();
  }

  @Bean
  public ObjectConverter objectConverter() {

    ObjectMapper jsonMapper = new ObjectMapper();
    jsonMapper.registerModule(new WebAuthnMetadataJSONModule());
    jsonMapper.registerModule(new WebAuthn4JSpringSecurityJSONModule());
    ObjectMapper cborMapper = new ObjectMapper(new CBORFactory());
    return new ObjectConverter(jsonMapper, cborMapper);
  }

  @Bean
  public AuthenticationSuccessHandler authenticationSuccessHandler() {

    return new ForwardAuthenticationSuccessHandler("/api/status/200");
  }

  // @Bean
  // public AuthenticationFailureHandler authenticationFailureHandler() {
  //
  // LinkedHashMap<Class<? extends AuthenticationException>, AuthenticationFailureHandler> authenticationFailureHandlers
  // = new LinkedHashMap<>();
  //
  // // authenticator error handler
  // ForwardAuthenticationFailureHandler authenticationFailureHandler = new ForwardAuthenticationFailureHandler(
  // "/api/status/401");
  // authenticationFailureHandlers.put(AuthenticationException.class, authenticationFailureHandler);
  //
  // // default error handler
  // AuthenticationFailureHandler defaultAuthenticationFailureHandler = new ForwardAuthenticationFailureHandler(
  // "/api/status/401");
  //
  // return new DelegatingAuthenticationFailureHandler(authenticationFailureHandlers,
  // defaultAuthenticationFailureHandler);
  // }

  @Bean
  public LogoutSuccessHandler logoutSuccessHandler() {

    return new ForwardLogoutSuccessHandler("/api/status/200");
  }

  // @Bean
  // public AccessDeniedHandler accessDeniedHandler() {
  //
  // LinkedHashMap<Class<? extends AccessDeniedException>, AccessDeniedHandler> errorHandlers = new LinkedHashMap<>();
  //
  // // invalid csrf authenticator error handler
  // AccessDeniedHandlerImpl invalidCsrfTokenErrorHandler = new AccessDeniedHandlerImpl();
  // invalidCsrfTokenErrorHandler.setErrorPage("/api/status/403");
  // errorHandlers.put(InvalidCsrfTokenException.class, invalidCsrfTokenErrorHandler);
  //
  // // missing csrf authenticator error handler
  // AccessDeniedHandlerImpl missingCsrfTokenErrorHandler = new AccessDeniedHandlerImpl();
  // missingCsrfTokenErrorHandler.setErrorPage("/api/status/403");
  // errorHandlers.put(MissingCsrfTokenException.class, missingCsrfTokenErrorHandler);
  //
  // // default error handler
  // AccessDeniedHandlerImpl defaultErrorHandler = new AccessDeniedHandlerImpl();
  // defaultErrorHandler.setErrorPage("/api/status/403");
  //
  // return new DelegatingAccessDeniedHandler(errorHandlers, defaultErrorHandler);
  // }

  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {

    LoginUrlAuthenticationEntryPoint authenticationEntryPoint = new LoginUrlAuthenticationEntryPoint("/api/status/401");
    authenticationEntryPoint.setUseForward(true);
    return authenticationEntryPoint;
  }

  @Bean
  public ModelMapper modelMapper() {

    return new ModelMapper();
  }

  @Bean
  public PublicKeyCredentialUserEntityProvider publicKeyCredentialUserEntityProvider(UserManager userManager) {

    return new PublicKeyCredentialUserEntityProviderImpl(userManager);

  }

  // END: FIDO2

}
