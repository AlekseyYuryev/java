package org.safris.xws.xrs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;

public abstract class ContainerContextImpl {
  protected static final ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>() {
    @Override
    protected SimpleDateFormat initialValue() {
      return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
    }
  };

  private final Locale locale;

  protected ContainerContextImpl(final Locale locale) {
    this.locale = locale;
  }

  protected abstract MultivaluedMap<String,String> getStringHeaders();

  public final String getHeaderString(final String name) {
    return getStringHeaders().getFirst(name);
  }

  public final Date getDate() {
    final String date = getStringHeaders().getFirst(HttpHeaders.DATE);
    try {
      return date == null ? null : dateFormat.get().parse(date);
    }
    catch (final ParseException e) {
      getStringHeaders().remove(HttpHeaders.DATE);
      return null;
    }
  }

  public Locale getLanguage() {
    return locale;
  }
}