/* Copyright (c) 2016 Seva Safris
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

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