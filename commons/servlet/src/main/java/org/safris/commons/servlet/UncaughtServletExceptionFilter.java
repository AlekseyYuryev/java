package org.safris.commons.servlet;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(filterName = "UncaughtServletExceptionFilter", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class UncaughtServletExceptionFilter implements Filter {
  public void init(final FilterConfig filterConfig) throws ServletException {
  }

  public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
    final UncaughtServletExceptionHandler uncaughtExceptionHandler = EmbeddedServer.getUncaughtServletExceptionHandler();
    try {
      chain.doFilter(request, response);
    }
    catch (final IOException e) {
      if (uncaughtExceptionHandler != null) {
        uncaughtExceptionHandler.uncaughtIOException(request, response, e);
      }
      else {
        throw e;
      }
    }
    catch (final ServletException e) {
      if (uncaughtExceptionHandler != null) {
        uncaughtExceptionHandler.uncaughtServletException(request, response, e);
      }
      else {
        throw e;
      }
    }
  }

  public void destroy() {
  }
}