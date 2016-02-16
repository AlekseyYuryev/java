package org.safris.commons.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface UncaughtServletExceptionHandler {
  public void uncaughtIOException(final ServletRequest request, final ServletResponse response, final IOException e) throws IOException;
  public void uncaughtServletException(final ServletRequest request, final ServletResponse response, final ServletException e) throws ServletException;
}