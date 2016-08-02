package org.safris.xws.xrs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

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

  protected abstract HttpHeaders getHttpHeaders();

  public final String getHeaderString(final String name) {
    return getHttpHeaders().getHeaderString(name);
  }

  public final Date getDate() {
    final String date = getHttpHeaders().getHeaderString(HttpHeaders.DATE);
    try {
      return date == null ? null : dateFormat.get().parse(date);
    }
    catch (final ParseException e) {
      // FIXME!
      throw new RuntimeException(e);
    }
  }

  public Locale getLanguage() {
    return locale;
  }

  public final int getLength() {
    return getHttpHeaders().getLength();
  }

  public final MediaType getMediaType() {
    return getHttpHeaders().getMediaType();
  }
}