package org.safris.jetty.wink;

import java.lang.reflect.Method;
import java.util.Properties;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.wink.server.handlers.HandlersChain;
import org.apache.wink.server.handlers.MessageContext;
import org.apache.wink.server.handlers.RequestHandler;
import org.apache.wink.server.internal.handlers.SearchResult;

public class MyRequestHandler implements RequestHandler {
  @Override
  public void init(final Properties props) {
  }

  @Override
  public void handleRequest(final MessageContext context, final HandlersChain chain) throws Throwable {
    final UriInfo info = context.getUriInfo();
    System.out.println("RequestHandler: The path relative to the base URI is : " + info.getPath());

//    final HttpServletRequest httpRequest = context.getAttribute(HttpServletRequest.class);
//    final HttpHeaders httpHeaders = context.getAttribute(HttpHeaders.class);
    final SearchResult searchResult = context.getAttribute(SearchResult.class);
    final Method method = searchResult.getMethod().getMetadata().getReflectionMethod();
    if (method.isAnnotationPresent(PermitAll.class) || method.getDeclaringClass().isAnnotationPresent(PermitAll.class)) {
      chain.doChain(context);
      return;
    }

    if (method.isAnnotationPresent(DenyAll.class) || method.getDeclaringClass().isAnnotationPresent(DenyAll.class)) {
      context.setResponseEntity(Response.status(Response.Status.FORBIDDEN).build());
      return;
    }

    chain.doChain(context);
  }
}