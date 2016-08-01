package org.safris.xws.xrs;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.SecurityContext;

public class DefaultSecurityContext implements SecurityContext {
  private final HttpServletRequest request;

  public DefaultSecurityContext(final HttpServletRequest request) {
    this.request = request;
  }

  @Override
  public Principal getUserPrincipal() {
    return null;
  }

  @Override
  public boolean isUserInRole(final String role) {
    return false;
  }

  @Override
  public final boolean isSecure() {
    return request.isSecure();
  }

  @Override
  public String getAuthenticationScheme() {
    return null;
  }
}