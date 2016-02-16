package org.safris.commons.servlet;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface UncaughtServletExceptionHandler {
  public void uncaughtServletException(final ServletRequest request, final ServletResponse response, final Exception e) throws Exception;
}