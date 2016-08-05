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