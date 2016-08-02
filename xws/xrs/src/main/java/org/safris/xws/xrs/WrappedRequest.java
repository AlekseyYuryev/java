package org.safris.xws.xrs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.ws.rs.container.ContainerRequestContext;

public class WrappedRequest extends HttpServletRequestWrapper {
  public WrappedRequest(final HttpServletRequest request) {
    super(request);
  }

  private ContainerRequestContext requestContext;

  public ContainerRequestContext getRequestContext() {
    return requestContext;
  }

  public void setRequestContext(final ContainerRequestContext requestContext) {
    this.requestContext = requestContext;
  }

  private ServiceManifest serviceManifest;

  public ServiceManifest getServiceManifest() {
    return serviceManifest;
  }

  public void setServiceManifest(final ServiceManifest serviceManifest) {
    this.serviceManifest = serviceManifest;
  }
}