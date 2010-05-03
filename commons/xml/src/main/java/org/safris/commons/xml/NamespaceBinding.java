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

package org.safris.commons.xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public final class NamespaceBinding {
    // punctuation characters
    private final static char HYPHEN = '\u002D';
    private final static char PERIOD = '\u002E';
    private final static char COLON  = '\u003A';
    private final static char USCORE = '\u005F';
    private final static char DOT   = '\u00B7';
    private final static char TELEIA = '\u0387';
    private final static char AYAH   = '\u06DD';
    private final static char ELHIZB = '\u06DE';

    private final static Collection<String> reservedWorlds = new HashSet<String>(Arrays.<String>asList(new String[]
    {
        "assert",
        "abstract",
        "boolean",
        "break",
        "byte",
        "case",
        "catch",
        "char",
        "class",
        "const",
        "continue",
        "default",
        "do",
        "double",
        "else",
        "enum", // since JDK1.5
        "extends",
        "false", // not a keyword
        "final",
        "finally",
        "float",
        "for",
        "goto",
        "if",
        "implements",
        "import",
        "instanceof",
        "int",
        "interface",
        "long",
        "native",
        "new",
        "null", // not a keyword
        "package",
        "private",
        "protected",
        "public",
        "return",
        "short",
        "static",
        "super",
        "switch",
        "synchronized",
        "this",
        "threadsafe",
        "throw",
        "throws",
        "transient",
        "true", // not a keyword
        "try",
        "void",
        "volatile",
        "while",
    }));

    private static String JAVA_PREFIX = "java:";

    public static String getNamespaceFromPackage(final Class clazz) {
        final String fullname = clazz.getName();
        final int lastdot = fullname.lastIndexOf('.');
        final String pkg_name = lastdot < 0 ? "" : fullname.substring(0, lastdot);

        return JAVA_PREFIX + pkg_name;
    }

    private static boolean isUriSchemeChar(char ch) {
        return ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch >= '0' && ch <= '9' || ch == '-' || ch == '.' || ch == '+';
    }

    private static boolean isUriAlphaChar(char ch) {
        return ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z';
    }

    private static int findSchemeColon(String uri) {
        final int len = uri.length();
        if (len == 0)
            return -1;

        if (!isUriAlphaChar(uri.charAt(0)))
            return -1;

        int i;
        for (i = 1; i < len; i++)
            if (!isUriSchemeChar(uri.charAt(i)))
                break;

        if (i == len)
            return -1;

        if (uri.charAt(i) != ':')
            return -1;

        // consume consecutive colons
        while (i++ < len)
            if (uri.charAt(i) != ':')
                break;

        // for the "scheme:::" case, return len-1
        return i - 1;
    }

    private static String jls77String(String name) {
        final StringBuffer buffer = new StringBuffer(name);
        for (int i = 0; i < name.length(); i++) {
            // We need to also make sure that our package names don't contain the
            // "$" character in them, which, although a valid Java identifier part,
            // would create confusion when trying to generate fully-qualified names
            if ('$' == buffer.charAt(i) || !Character.isJavaIdentifierPart(buffer.charAt(i)))
                buffer.setCharAt(i, '_');
        }

        if (buffer.length() == 0 || !Character.isJavaIdentifierStart(buffer.charAt(0)))
            buffer.insert(0, '_');

        if (isJavaReservedWord(name))
            buffer.append('_');

        return buffer.toString();
    }

    private static List<String> splitDNS(String dns) {
        // JAXB says: only split+reverse DNS if TLD matches known TLDs or ISO 3166
        // We are ignoring this now (TH)
        final List<String> result = new ArrayList<String>();
        int end = dns.length();
        for (int begin = dns.lastIndexOf('.'); begin != -1 ; begin--) {
            if (dns.charAt(begin) == '.') {
                result.add(jls77String(dns.substring(begin + 1, end)));
                end = begin;
            }
        }

        result.add(jls77String(dns.substring(0, end)));

        // JAXB draft example implies removal of www
        if (result.size() >= 3 && result.get(result.size() - 1).toLowerCase().equals("www"))
            result.remove(result.size() - 1);

        return result;
    }

    private static String processFilename(String filename) {
        // JAXB says: strip 2 or 3 letter extension or ".html"
        final int i = filename.lastIndexOf('.');
        if (i > 0 && (i + 1 + 2 == filename.length() || i + 1 + 3 == filename.length() || filename.substring(i + 1).toLowerCase() == "html"))
            return filename.substring(0, i);

        return filename;
    }

    public static String getPackageFromNamespace(String uri) {
        return getPackageFromNamespace(uri, true);
    }

    private static String getPackageFromNamespace(String uri, boolean useJaxRpcRules) {
		if(uri == null)
			throw new NullPointerException("uri == null");

        // special case: no namespace -> package "noNamespace"
        if (uri.length() == 0)
            return "noNamespace";

        // apply draft JAXB rules
        int colon = findSchemeColon(uri);
        List<String> results = null;
        final int length = uri.length();
        if (colon == length - 1) {
            // XMLBEANS-57: colon is at end so just use scheme as the package name
            results = new ArrayList<String>();
            results.add(uri.substring(0, colon));
        }
        else if (colon >= 0 && uri.substring(0, colon).equals("java")) {
            results = Arrays.<String>asList(uri.substring(colon + 1).split("\\."));
        }
        else {
            results = new ArrayList<String>();
outer:
            for (colon = colon + 1; colon < length;) {
                while (uri.charAt(colon) == '/')
                    if (++colon >= length)
                        break outer;

                int start = colon;
                while (uri.charAt(colon) != '/')
                    if (++colon >= length)
                        break;

                int end = colon;
                results.add(uri.substring(start, end));
            }

            if (results.size() > 1)
                results.set(results.size() - 1, processFilename(results.get(results.size() - 1)));

            if (results.size() > 0) {
                List<String> splitdns = splitDNS(results.get(0));
                results.remove(0);
                results.addAll(0, splitdns);
            }
        }

        final StringBuffer buffer = new StringBuffer();
        for (String result : results) {
            final String part = nonJavaKeyword(lowerCamelCase(result, useJaxRpcRules, true));
            if (part.length() > 0) {
                buffer.append(part);
                buffer.append('.');
            }
        }

        if (buffer.length() == 0)
            return "noNamespace";

        if (useJaxRpcRules)
            return buffer.substring(0, buffer.length() - 1).toLowerCase();

        return buffer.substring(0, buffer.length() - 1); // chop off extra dot
    }

    /**
     * Returns a camel-cased string using the JAXB or JAX-RPC rules
     */
    private static String lowerCamelCase(String xml_name, boolean useJaxRpcRules, boolean fixGeneratedName) {
        StringBuffer buffer = new StringBuffer();
        List<String> words = splitWords(xml_name, useJaxRpcRules);

        if (words.size() > 0) {
            String firstWord = words.get(0).toLowerCase();
            char firstChar = firstWord.charAt(0);
            if (fixGeneratedName && !Character.isJavaIdentifierStart(firstChar))
                buffer.append("x");

            buffer.append(firstWord);
            Iterator<String> iterator = words.iterator();
            iterator.next(); // skip already-lowercased word
            while (iterator.hasNext())
                buffer.append(iterator.next());
        }

        return buffer.toString();
    }

    private static String upperCaseFirstLetter(String string) {
        if (string.length() == 0 || Character.isUpperCase(string.charAt(0)))
            return string;

        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    /**
     * split an xml name into words via JAXB approach, upcasing first
     * letter of each word as needed, if upcase is true
     *
     * ncname is xml ncname (i.e. no colons).
     */
    private static void addCapped(List<String> list, String string) {
        if (string.length() != 0)
            list.add(upperCaseFirstLetter(string));
    }

    private static List<String> splitWords(String name, boolean useJaxRpcRules) {
        final List<String> list = new ArrayList<String>();
        final int len = name.length();
        int start = 0;
        int prefix = START;
        for (int i = 0; i < len; i++) {
            int current = getCharClass(name.charAt(i), useJaxRpcRules);
            if (prefix != PUNCT && current == PUNCT) {
                addCapped(list, name.substring(start, i));
                while ((current = getCharClass(name.charAt(i), useJaxRpcRules)) == PUNCT)
                    if (++i >= len)
                        return list;

                start = i;
            }
            else if ((prefix == DIGIT) != (current == DIGIT) || (prefix == LOWER && current != LOWER) || (isLetter(prefix) != isLetter(current))) {
                addCapped(list, name.substring(start, i));
                start = i;
            }
            else if (prefix == UPPER && current == LOWER && i > start + 1) {
                addCapped(list, name.substring(start, i - 1));
                start = i - 1;
            }

            prefix = current;
        }

        addCapped(list, name.substring(start));
        return list;
    }

    //char classes
    private final static int START = 0;
    private final static int PUNCT = 1;
    private final static int DIGIT = 2;
    private final static int MARK = 3;
    private final static int UPPER = 4;
    private final static int LOWER= 5;
    private final static int NOCASE= 6;

    private static int getCharClass(char c, boolean useJaxRpcRules) {
        //ordering is important here.
        if (isPunctuation(c, useJaxRpcRules))
            return PUNCT;
        else if (Character.isDigit(c))
            return DIGIT;
        else if (Character.isUpperCase(c))
            return UPPER;
        else if (Character.isLowerCase(c))
            return LOWER;
        else if (Character.isLetter(c))
            return NOCASE;
        else if (Character.isJavaIdentifierPart(c))
            return MARK;
        else
            return PUNCT; // not covered by JAXB: treat it as punctuation
    }

    private static boolean isLetter(int state) {
        return state == UPPER || state == LOWER || state == NOCASE;
    }

    private static boolean isPunctuation(char c, boolean useJaxRpcRules) {
        return c == HYPHEN || c == PERIOD || c == COLON || c == DOT || (c == USCORE && !useJaxRpcRules) || c == TELEIA || c == AYAH || c == ELHIZB;
    }

    /**
     * Intended to be applied to a lowercase-starting identifier that
     * may collide with a Java keyword.  If it does collide, this
     * prepends the letter "x".
     */
    private static String nonJavaKeyword(String word) {
        if (isJavaReservedWord(word))
            return "x" + word;

        return word;
    }

    private static boolean isJavaReservedWord(String word) {
        return reservedWorlds.contains(word.toLowerCase());
    }
}
