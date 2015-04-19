/* Copyright (c) 2008 Seva Safris
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

package org.safris.xml.toolkit.sample.binding;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import org.safris.commons.xml.binding.Date;
import org.safris.commons.xml.binding.Time;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.toolkit.sample.binding.list.$li_employeeType;
import org.safris.xml.toolkit.sample.binding.list.$li_staffType;
import org.safris.xml.toolkit.sample.binding.list.$li_volunteerType;
import org.safris.xml.toolkit.sample.binding.list.li_roster;
import org.xml.sax.InputSource;

public class ListExample {
  public static void main(final String[] args) throws Exception {
    new SubstitutionGroupExample().runExample();
  }

  private static void printCommon(final $li_staffType staffType) {
    String name = staffType._name(0).text();
    System.out.println("Name: " + name);

    List<String> workDays = staffType._workDays(0).text();
    System.out.println("Work Days: " + name);
    for (String workDay : workDays)
      System.out.println("\t" + workDay);
  }

  public Binding runExample() throws Exception {
    File file = new File("src/main/resources/xml/list.xml");
    if (!file.exists())
      throw new Error("File " + file.getAbsolutePath() + " does not exist.");

    if (!file.canRead())
      throw new Error("File " + file.getAbsolutePath() + " is not readable.");

    li_roster roster = (li_roster)Bindings.parse(new InputSource(new FileInputStream(file)));
    if (roster._employees() != null && roster._employees().size() != -1) {
      List<$li_employeeType> employees = roster._employees(0)._employee();
      for ($li_employeeType employee : employees) {
        printCommon(employee);

        String position = employee._position(0).text();
        System.out.println("Position: " + position);

        List<Date> vacationDates = employee._vacationDates(0).text();
        System.out.println("Vacation Dates:");
        for (Date vacationDate : vacationDates)
          System.out.println("\t" + vacationDate);
      }

      li_roster._employees._employee employee = new li_roster._employees._employee();
      employee._name(new li_roster._employees._employee._name("Woody Harold"));
      employee._workDays(new li_roster._employees._employee._workDays(li_roster._employees._employee._workDays.mon, li_roster._employees._employee._workDays.tue, li_roster._employees._employee._workDays.wed));
      employee._position(new li_roster._employees._employee._position(li_roster._employees._employee._position.stockroom));
      employee._vacationDates(new li_roster._employees._employee._vacationDates(new Date(2008, 8, 12), new Date(2008, 9, 22), new Date(2008, 10, 30)));
      employees.add(employee);
    }

    if (roster._volunteers() != null && roster._volunteers().size() != -1) {
      List<$li_volunteerType> volunteers = roster._volunteers(0)._volunteer();
      for ($li_volunteerType volunteer : volunteers) {
        printCommon(volunteer);

        List<Time> breakTimes = volunteer._breakTimes(0).text();
        System.out.println("Break Times:");
        for (Time breakTime : breakTimes)
          System.out.println("\t" + breakTime);
      }

      li_roster._volunteers._volunteer volunteer = new li_roster._volunteers._volunteer();
      volunteer._name(new li_roster._employees._employee._name("Michelle Smith"));
      volunteer._workDays(new li_roster._employees._employee._workDays(li_roster._employees._employee._workDays.mon, li_roster._employees._employee._workDays.tue, li_roster._employees._employee._workDays.wed));
      volunteer._breakTimes(new li_roster._volunteers._volunteer._breakTimes(new Time(10, 15, 00), new Time(12, 00, 00), new Time(15, 30, 00)));
      volunteers.add(volunteer);
    }

    return roster;
  }
}