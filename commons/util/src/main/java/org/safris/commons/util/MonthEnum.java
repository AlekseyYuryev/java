package org.safris.commons.util;

import java.util.Calendar;

public class MonthEnum extends MaskedEnum {
  public static final MonthEnum JANUARY = new MonthEnum(Calendar.JANUARY);
  public static final MonthEnum FEBRUARY = new MonthEnum(Calendar.FEBRUARY);
  public static final MonthEnum MARCH = new MonthEnum(Calendar.MARCH);
  public static final MonthEnum APRIL = new MonthEnum(Calendar.APRIL);
  public static final MonthEnum MAY = new MonthEnum(Calendar.MAY);
  public static final MonthEnum JUNE = new MonthEnum(Calendar.JUNE);
  public static final MonthEnum JULY = new MonthEnum(Calendar.JULY);
  public static final MonthEnum AUGUST = new MonthEnum(Calendar.AUGUST);
  public static final MonthEnum SEPTEMBER = new MonthEnum(Calendar.SEPTEMBER);
  public static final MonthEnum OCTOBER = new MonthEnum(Calendar.OCTOBER);
  public static final MonthEnum NOVEMBER = new MonthEnum(Calendar.NOVEMBER);
  public static final MonthEnum DECEMBER = new MonthEnum(Calendar.DECEMBER);

  public static MonthEnum[] toArray(final int mask) {
    return MaskedEnum.<MonthEnum>toArray(MonthEnum.class, mask);
  }
  
  public static MonthEnum valueOf(final int ordinal) {
    return MaskedEnum.<MonthEnum>valueOf(MonthEnum.class, ordinal);
  }

  private MonthEnum(final int index) {
    super(index);
  }
}