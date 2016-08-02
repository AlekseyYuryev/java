package org.safris.xws.xrs;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.safris.xws.xjb.JSObject;

public class ClientResponse {
  private final HttpServletResponse httpServletResponse;
  private final ContainerResponseContext containerResponseContext;

  public ClientResponse(final HttpServletResponse httpServletResponse, final ContainerResponseContext containerResponseContext) {
    this.httpServletResponse = httpServletResponse;
    this.containerResponseContext = containerResponseContext;
  }

  private Response response;

  public Response getResponse() {
    return response;
  }

  public void setResponse(final Response response) {
    this.response = response;
  }

  private void syncResponses() {
    if (getResponse() != null) {
      if (getResponse().hasEntity())
        containerResponseContext.setEntity(getResponse().getEntity());

      containerResponseContext.setStatusInfo(getResponse().getStatusInfo());

      final MultivaluedMap<String,String> headers = getResponse().getStringHeaders();
      for (final Map.Entry<String,List<String>> entry : headers.entrySet())
        for (final String header : entry.getValue())
          containerResponseContext.getStringHeaders().add(entry.getKey(), header);
    }
  }

  public void commit() throws IOException {
    syncResponses();
    final MultivaluedMap<String,String> headers = containerResponseContext.getStringHeaders();
    for (final Map.Entry<String,List<String>> entry : headers.entrySet())
      for (final String header : entry.getValue())
        httpServletResponse.addHeader(entry.getKey(), header);

    httpServletResponse.setStatus(containerResponseContext.getStatus());

    final Object entity = containerResponseContext.getEntity();
    if (entity != null) {
      if (entity instanceof JSObject) {
        // NOTE: This may throw a EncodeException, and should thus be outside the
        // NOTE: try (response) block, otherwise the response will be committed
        // NOTE: before we can set a HTTP 500
        final String json = entity.toString();
        try (final Writer writer = httpServletResponse.getWriter()) {
          writer.write(json);
          writer.flush();
        }

        return;
      }
      else if (entity instanceof String) {
        try (final Writer writer = httpServletResponse.getWriter()) {
          writer.write((String)entity);
        }
      }
      else if (entity instanceof byte[]) {
        try (final OutputStream out = httpServletResponse.getOutputStream()) {
          out.write((byte[])entity);
        }
      }
      else {
        throw new WebApplicationException("Unexpected entity return type: " + entity.getClass().getName());
      }
    }
    else {
      // @see ServletResponse#getOutputStream :: "Calling flush() on the ServletOutputStream commits the response."
      httpServletResponse.getOutputStream().flush();
    }
  }
}