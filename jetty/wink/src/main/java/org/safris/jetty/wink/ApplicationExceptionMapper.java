package org.safris.jetty.wink;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<Throwable> {
  @Override
  public Response toResponse(final Throwable throwable) {
    if (throwable instanceof BadRequestException) {
      return Response.status(Response.Status.BAD_REQUEST).entity(throwable.getMessage()).build();
    }

    if (throwable instanceof WebApplicationException) {
      final WebApplicationException exception = (WebApplicationException)throwable;
      return Response.status(exception.getResponse().getStatus()).entity(exception.getMessage()).build();
    }

    return Response.status(500).entity(throwable.getMessage()).build();
  }
}