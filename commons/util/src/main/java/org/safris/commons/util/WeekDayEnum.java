package org.safris.commons.util;

import java.util.Calendar;

public class WeekDayEnum extends MaskedEnum {
  public static final WeekDayEnum MONDAY = new WeekDayEnum(Calendar.MONDAY);
  public static final WeekDayEnum TUESDAY = new WeekDayEnum(Calendar.TUESDAY);
  public static final WeekDayEnum WEDNESDAY = new WeekDayEnum(Calendar.WEDNESDAY);
  public static final WeekDayEnum THURSDAY = new WeekDayEnum(Calendar.THURSDAY);
  public static final WeekDayEnum FRIDAY = new WeekDayEnum(Calendar.FRIDAY);
  public static final WeekDayEnum SATURDAY = new WeekDayEnum(Calendar.SATURDAY);
  public static final WeekDayEnum SUNDAY = new WeekDayEnum(Calendar.SUNDAY);

  public static WeekDayEnum[] toArray(final int mask) {
    return MaskedEnum.<WeekDayEnum>toArray(WeekDayEnum.class, mask);
  }
  
  public static WeekDayEnum valueOf(final int ordinal) {
    return MaskedEnum.<WeekDayEnum>valueOf(WeekDayEnum.class, ordinal);
  }

  private WeekDayEnum(final int index) {
    super((index + 5) % 7);
  }
}