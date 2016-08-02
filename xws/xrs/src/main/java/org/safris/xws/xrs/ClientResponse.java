package org.safris.xws.xrs;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class ClientResponse {
  private final HttpServletResponse httpServletResponse;

  public ClientResponse(final HttpServletResponse httpServletResponse) {
    this.httpServletResponse = httpServletResponse;
  }

  private Response response;

  public Response getResponse() {
    return response;
  }

  public void setResponse(final Response response) {
    this.response = response;
  }

  public void commit() throws IOException {
    httpServletResponse.setStatus(response.getStatus());
    final MultivaluedMap<String,String> headers = response.getStringHeaders();
    for (final Map.Entry<String,List<String>> entry : headers.entrySet())
      for (final String header : entry.getValue())
        httpServletResponse.addHeader(entry.getKey(), header);

    // @see ServletResponse#getOutputStream :: "Calling flush() on the ServletOutputStream commits the response."
    httpServletResponse.getOutputStream().flush();
  }
}