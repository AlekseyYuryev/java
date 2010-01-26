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

package org.safris.xml.toolkit.test.binding.regression;

public abstract class RegressionTestMetrics {
    private static int testCount = 0;
    private static String smallestXMLDocument = null;
    private static String largestXMLDocument = "";

    public static void process(String xml) {
        testCount++;
        if (smallestXMLDocument == null || smallestXMLDocument.length() > xml.length())
            smallestXMLDocument = xml;

        if (largestXMLDocument.length() < xml.length())
            largestXMLDocument = xml;
    }

    public static int getTestCount() {
        return testCount;
    }

    public static String getSmallestXMLDocument() {
        return smallestXMLDocument;
    }

    public static String getLargestXMLDocument() {
        return largestXMLDocument;
    }
}
