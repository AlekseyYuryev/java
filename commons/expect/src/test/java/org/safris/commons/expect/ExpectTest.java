/*  Copyright 2008 Safris Technologies Inc.
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

package org.safris.commons.expect;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.junit.Ignore;
import org.junit.Test;

public class ExpectTest
{
    public static void main(String[] args) throws Exception
	{
        new ExpectTest().testStart();
    }

    @Test
	// FIXME
    @Ignore("FIXME")
    public void testStart() throws Exception
	{
        final ExpectCallback callback = new ExpectCallback() {
            private final Map<String,String> variables = new HashMap<String,String>();
            private int index = -1;

            public Map<String,String> process(String exec)
			{
                variables.put("date", "080630");
                return variables;
            }

            public Map<String,String> rule(String ruleId, String prompt, String response)
			{
                if(!"r2".equals(ruleId))
                    return variables;

                switch(++index)
				{
                    case 0:
                        variables.put("name", "Lisa\n");
                        variables.put("adultOrChild", "C\n");
                        variables.put("boyOrGirl", "G\n");
                        variables.put("age", "14\n");
                        variables.put("attending", "N\n");
                        break;
                    case 1:
                        variables.put("name", "David\n");
                        variables.put("adultOrChild", "C\n");
                        variables.put("boyOrGirl", "B\n");
                        variables.put("age", "10\n");
                        variables.put("attending", "Y\n");
                        variables.put("date", "080630\n");
                        variables.put("bringingStuff", "N\n");
                        variables.put("drinking", "N\n");
                        variables.put("driving", "N\n");
                        variables.put("printRoster", "N\n");
                        break;
                    case 2:
                        variables.put("name", "Natalie\n");
                        variables.put("adultOrChild", "C\n");
                        variables.put("boyOrGirl", "G\n");
                        variables.put("age", "19\n");
                        variables.put("attending", "Y\n");
                        variables.put("date", "080630\n");
                        variables.put("bringingStuff", "N\n");
                        variables.put("drinking", "Y\n");
                        variables.put("driving", "N\n");
                        variables.put("printRoster", "N\n");
                        break;
                    case 3:
                        variables.put("name", "Sylvie\n");
                        variables.put("adultOrChild", "C\n");
                        variables.put("boyOrGirl", "G\n");
                        variables.put("age", "19\n");
                        variables.put("attending", "Y\n");
                        variables.put("date", "080630\n");
                        variables.put("bringingStuff", "N\n");
                        variables.put("drinking", "Y\n");
                        variables.put("driving", "Y\n");
                        variables.put("needRide", "Y\n");
                        variables.put("printRoster", "N\n");
                        break;
                    case 4:
                        variables.put("name", "Jon\n");
                        variables.put("adultOrChild", "A\n");
                        variables.put("maleOrFemale", "M\n");
                        variables.put("attending", "N\n");
                        break;
                    case 5:
                        variables.put("name", "Jake\n");
                        variables.put("adultOrChild", "A\n");
                        variables.put("maleOrFemale", "M\n");
                        variables.put("attending", "Y\n");
                        variables.put("date", "080630\n");
                        variables.put("bringingStuff", "N\n");
                        variables.put("drinking", "N\n");
                        variables.put("printRoster", "N\n");
                        break;
                    case 6:
                        variables.put("name", "Shoshana\n");
                        variables.put("adultOrChild", "A\n");
                        variables.put("maleOrFemale", "F\n");
                        variables.put("attending", "Y\n");
                        variables.put("date", "080630\n");
                        variables.put("bringingStuff", "N\n");
                        variables.put("drinking", "Y\n");
                        variables.put("driving", "N\n");
                        variables.put("printRoster", "N\n");
                        break;
                    case 7:
                        variables.put("name", "Katie\n");
                        variables.put("adultOrChild", "A\n");
                        variables.put("maleOrFemale", "F\n");
                        variables.put("attending", "Y\n");
                        variables.put("date", "080630\n");
                        variables.put("bringingStuff", "N\n");
                        variables.put("drinking", "Y\n");
                        variables.put("driving", "Y\n");
                        variables.put("needRide", "Y\n");
                        variables.put("printRoster", "Y\n");
                        break;
                }

                return variables;
            }
        };

        Expect.start(System.in, System.out, System.err, callback, new File("src/test/resources/xml/expect.xml"));
    }
}
