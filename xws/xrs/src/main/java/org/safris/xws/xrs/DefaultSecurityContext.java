package org.safris.xws.xrs;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.SecurityContext;

public final class DefaultSecurityContext implements SecurityContext {
  private final HttpServletRequest request;

  public DefaultSecurityContext(final HttpServletRequest request) {
    this.request = request;
  }

  @Override
  public final Principal getUserPrincipal() {
    return null;
  }

  @Override
  public final boolean isUserInRole(final String role) {
    return false;
  }

  @Override
  public final boolean isSecure() {
    return request.isSecure();
  }

  @Override
  public final String getAuthenticationScheme() {
    return null;
  }
}