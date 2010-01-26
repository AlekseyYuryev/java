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

package org.safris.commons.xml.binding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * http://www.w3.org/TR/xmlschema11-2/#language
 */
public class Language implements CharSequence {
    public static Language parseLanguage(String string) {
        if (string == null)
            throw new NullPointerException("string == null");

        string = string.trim();
        if (string.length() < LANGUAGE_FRAG_MIN_LENGTH)
            throw new IllegalArgumentException(string);

        final StringTokenizer tokenizer = new StringTokenizer(string, "-");
        final Collection<String> languages = new ArrayList<String>();
        while (tokenizer.hasMoreTokens())
            languages.add(tokenizer.nextToken());

        return new Language(languages);
    }

    private static final Pattern firstPattern = Pattern.compile("[a-zA-Z]{1,8}");
    private static final Pattern otherPattern = Pattern.compile("[a-zA-Z0-9]{1,8}");
    private static final int LANGUAGE_FRAG_MIN_LENGTH = 1;
    private final String[] language;
    private String encoded = null;

    public Language(String ... language) {
        if (language == null)
            throw new NullPointerException("language == null");

        if (language.length == 0)
            throw new IllegalArgumentException("language.length == 0");

        if (!firstPattern.matcher(language[0]).matches())
            throw new IllegalArgumentException(language[0]);

        for (int i = 1; i < language.length; i++)
            if (!otherPattern.matcher(language[i]).matches())
                throw new IllegalArgumentException(language[i]);

        this.language = language;
    }

    public Language(Collection<String> language) {
        this(language != null ? language.toArray(new String[language.size()]) : null);
    }

    public String[] getLanguage() {
        return language;
    }

    public int length() {
        return toString().length();
    }

    public char charAt(int index) {
        return toString().charAt(index);
    }

    public CharSequence subSequence(int start, int end) {
        return toString().subSequence(start, end);
    }

    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Year))
            return false;

        final Language that = (Language)obj;
        return this.language != null ? Arrays.equals(this.language, that.language) : that.language == null;
    }

    public int hashCode() {
        return language != null ? Arrays.hashCode(language) : -1;
    }

    public String toString() {
        if (encoded != null)
            return encoded;

        if (language == null || language.length == 0)
            return encoded = "";

        final StringBuffer buffer = new StringBuffer();
        for (String string : language)
            buffer.append("-").append(string);

        return encoded = buffer.substring(1);
    }
}
