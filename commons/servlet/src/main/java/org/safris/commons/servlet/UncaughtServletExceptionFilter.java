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
  @Override
  public void init(final FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
    final UncaughtServletExceptionHandler uncaughtExceptionHandler = EmbeddedServer.getUncaughtServletExceptionHandler();
    try {
      chain.doFilter(request, response);
    }
    catch (final Exception e1) {
      if (uncaughtExceptionHandler != null) {
        try {
          uncaughtExceptionHandler.uncaughtServletException(request, response, e1);
        }
        catch (final Throwable e2) {
          if (e2 instanceof IOException)
            throw (IOException)e2;

          if (e2 instanceof ServletException)
            throw (ServletException)e2;

          if (e2 instanceof RuntimeException)
            throw (RuntimeException)e2;
        }
      }
      else {
        throw e1;
      }
    }
  }

  @Override
  public void destroy() {
  }
}