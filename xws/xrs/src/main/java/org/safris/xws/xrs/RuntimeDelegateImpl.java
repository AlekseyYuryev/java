package org.safris.xws.xrs;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Link.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Variant.VariantListBuilder;
import javax.ws.rs.ext.RuntimeDelegate;

public class RuntimeDelegateImpl extends RuntimeDelegate {
  static {
    System.setProperty(RuntimeDelegate.JAXRS_RUNTIME_DELEGATE_PROPERTY, RuntimeDelegateImpl.class.getName());
  }

  @Override
  public UriBuilder createUriBuilder() {
    return null;
  }

  @Override
  public ResponseBuilder createResponseBuilder() {
    // FIXME: Is there absolutely no way out of this?
    return new ResponseBuilderImpl(RESTServlet.RESPONSE.get());
  }

  @Override
  public VariantListBuilder createVariantListBuilder() {
    return null;
  }

  @Override
  public <T>T createEndpoint(final Application application, final Class<T> endpointType) throws IllegalArgumentException, UnsupportedOperationException {
    return null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T>HeaderDelegate<T> createHeaderDelegate(final Class<T> type) throws IllegalArgumentException {
    if (type == MediaType.class)
      return (HeaderDelegate<T>)new HeaderDelegateImpl();

    throw new UnsupportedOperationException("Unexpected header object type: " + type.getName());
  }

  @Override
  public Builder createLinkBuilder() {
    return null;
  }
}