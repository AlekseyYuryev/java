/*  Copyright 2010 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
    public static void main(String[] args) throws Exception {
        new SubstitutionGroupExample().runExample();
    }

    private static void printCommon($li_staffType<?> staffType) {
        String name = staffType.get_name().get(0).getText();
        System.out.println("Name: " + name);

        List<String> workDays = staffType.get_workDays().get(0).getText();
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
        if (roster.get_employees() != null && roster.get_employees().size() != -1) {
            List<$li_employeeType<?>> employees = roster.get_employees().get(0).get_employee();
            for ($li_employeeType<?> employee : employees) {
                printCommon(employee);

                String position = employee.get_position().get(0).getText();
                System.out.println("Position: " + position);

                List<Date> vacationDates = employee.get_vacationDates().get(0).getText();
                System.out.println("Vacation Dates:");
                for (Date vacationDate : vacationDates)
                    System.out.println("\t" + vacationDate);
            }

            li_roster._employees._employee employee = new li_roster._employees._employee();
            employee.add_name(new li_roster._employees._employee._name("Woody Harold"));
            employee.add_workDays(new li_roster._employees._employee._workDays(li_roster._employees._employee._workDays.MON, li_roster._employees._employee._workDays.TUE, li_roster._employees._employee._workDays.WED));
            employee.add_position(new li_roster._employees._employee._position(li_roster._employees._employee._position.STOCKROOM));
            employee.add_vacationDates(new li_roster._employees._employee._vacationDates(new Date(2008, 8, 12), new Date(2008, 9, 22), new Date(2008, 10, 30)));
            employees.add(employee);
        }

        if (roster.get_volunteers() != null && roster.get_volunteers().size() != -1) {
            List<$li_volunteerType<?>> volunteers = roster.get_volunteers().get(0).get_volunteer();
            for ($li_volunteerType<?> volunteer : volunteers) {
                printCommon(volunteer);

                List<Time> breakTimes = volunteer.get_breakTimes().get(0).getText();
                System.out.println("Break Times:");
                for (Time breakTime : breakTimes)
                    System.out.println("\t" + breakTime);
            }

            li_roster._volunteers._volunteer volunteer = new li_roster._volunteers._volunteer();
            volunteer.add_name(new li_roster._employees._employee._name("Michelle Smith"));
            volunteer.add_workDays(new li_roster._employees._employee._workDays(li_roster._employees._employee._workDays.MON, li_roster._employees._employee._workDays.TUE, li_roster._employees._employee._workDays.WED));
            volunteer.add_breakTimes(new li_roster._volunteers._volunteer._breakTimes(new Time(10, 15, 00), new Time(12, 00, 00), new Time(15, 30, 00)));
            volunteers.add(volunteer);
        }

        return roster;
    }
}
