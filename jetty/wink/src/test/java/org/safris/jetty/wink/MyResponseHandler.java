package org.safris.jetty.wink;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriInfo;

import org.apache.wink.server.handlers.HandlersChain;
import org.apache.wink.server.handlers.MessageContext;
import org.apache.wink.server.handlers.ResponseHandler;

public class MyResponseHandler implements ResponseHandler {
  private static final Logger logger = Logger.getLogger(MyResponseHandler.class.getName());

  @Override
  public void init(final Properties props) {
  }

  @Override
  public void handleResponse(final MessageContext context, final HandlersChain chain) throws Throwable {
    // assert response is not committed
    final HttpServletResponse httpResponse = context.getAttribute(HttpServletResponse.class);
    if (httpResponse.isCommitted()) {
      logger.log(Level.FINE, "The response is already committed. Nothing to do.");
      return;
    }

    httpResponse.setHeader("Access-Control-Allow-Origin", "*");
    if ("OPTIONS".equals(context.getHttpMethod())) {
      httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Content-Range, Content-Disposition, Content-Description, Accept, Authorization");
      httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
      httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
      httpResponse.setHeader("Access-Control-Max-Age", "1209600");
    }

    final UriInfo info = context.getUriInfo();
    System.out.println("ResponseHandler: The path relative to the base URI is : " + info.getPath());
    chain.doChain(context);
  }
}