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

package com.devonfw.fido.general.service.impl.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import com.webauthn4j.converter.util.ObjectConverter;
import com.webauthn4j.springframework.security.util.internal.ExceptionUtil;
import com.webauthn4j.util.exception.WebAuthnException;

public abstract class ServerEndpointFilterBase extends GenericFilterBean {

  // ~ Instance fields
  // ================================================================================================
  protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

  protected ObjectConverter objectConverter;

  protected ServerEndpointFilterUtil serverEndpointFilterUtil;

  /**
   * Url this filter should get activated on.
   */
  private String filterProcessesUrl;

  public ServerEndpointFilterBase(String filterProcessesUrl, ObjectConverter objectConverter) {

    this.filterProcessesUrl = filterProcessesUrl;
    this.objectConverter = objectConverter;
    this.serverEndpointFilterUtil = new ServerEndpointFilterUtil(this.objectConverter);
    checkConfig();
  }

  public ServerEndpointFilterBase() {

  }

  @Override
  public void afterPropertiesSet() {

    checkConfig();
  }

  private void checkConfig() {

    Assert.notNull(this.filterProcessesUrl, "filterProcessesUrl must not be null");
    Assert.notNull(this.objectConverter, "objectConverter must not be null");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    FilterInvocation fi = new FilterInvocation(request, response, chain);
    try {
      HttpServletRequest httpServletRequest = fi.getRequest();
      HttpServletResponse httpServletResponse = fi.getResponse();
      if (!httpServletRequest.getMethod().equals(HttpMethod.POST.name())) {
        throw new AuthenticationServiceException(
            "Authentication method not supported: " + httpServletRequest.getMethod());
      }

      if (!processFilter(httpServletRequest)) {
        chain.doFilter(request, response);
        return;
      }

      try {
        ServerResponse serverResponse = processRequest(httpServletRequest);
        this.serverEndpointFilterUtil.writeResponse(httpServletResponse, serverResponse);
      } catch (WebAuthnException e) {
        throw ExceptionUtil.wrapWithAuthenticationException(e);
      }
    } catch (RuntimeException e) {
      this.logger.debug("RuntimeException is thrown", e);
      this.serverEndpointFilterUtil.writeErrorResponse(fi.getResponse(), e);
    }
  }

  protected abstract ServerResponse processRequest(HttpServletRequest request);

  /**
   * The filter will be used in case the URL of the request contains the FILTER_URL.
   *
   * @param request request used to determine whether to enable this filter
   * @return true if this filter should be used
   */
  private boolean processFilter(HttpServletRequest request) {

    return (request.getRequestURI().contains(this.filterProcessesUrl));
  }

  public String getFilterProcessesUrl() {

    return this.filterProcessesUrl;
  }

  public void setFilterProcessesUrl(String filterProcessesUrl) {

    Assert.hasText(filterProcessesUrl, "filterProcessesUrl parameter must not be empty or null");
    this.filterProcessesUrl = filterProcessesUrl;
  }

}
