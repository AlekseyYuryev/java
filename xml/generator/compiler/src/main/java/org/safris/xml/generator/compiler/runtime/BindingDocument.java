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

package org.safris.xml.generator.compiler.runtime;

import java.io.IOException;
import java.net.URL;
import org.safris.commons.xml.validator.ValidationException;
import org.xml.sax.InputSource;

public class BindingDocument {
    private final URL url;
    private final Binding document;

    public BindingDocument(URL url) throws IOException, ParseException, ValidationException {
        this.url = url;
        url.openConnection();
        document = Bindings.parse(new InputSource(url.openStream()));
    }

    public Binding getDocument() {
        return document;
    }

    public URL getURL() {
        return url;
    }
}
