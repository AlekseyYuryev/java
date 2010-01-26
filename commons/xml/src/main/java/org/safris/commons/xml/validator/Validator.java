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

package org.safris.commons.xml.validator;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public abstract class Validator {
    private static final QName XSI = new QName("http://www.w3.org/2001/XMLSchema-instance", "xsi", "xmlns");
    private static final QName XMLNS = new QName("http://www.w3.org/2000/xmlns/", "xmlns");

    private static Validator validator = null;
    private boolean validateOnMarshal = false;
    private boolean validateOnParse = false;

    public static Validator getSystemValidator() {
        return validator;
    }

    public static void setSystemValidator(Validator validator) {
        Validator.validator = validator;
    }

    protected abstract URL lookupSchemaLocation(String namespaceURI);

    public void setValidateOnMarshal(boolean validateOnMarshal) {
        this.validateOnMarshal = validateOnMarshal;
    }

    public boolean isValidateOnMarshal() {
        return validateOnMarshal;
    }

    public void setValidateOnParse(boolean validateOnParse) {
        this.validateOnParse = validateOnParse;
    }

    public boolean isValidateOnParse() {
        return validateOnParse;
    }

    public final void validate(Element element) throws ValidationException {
        // only do validation on the root element of the document
        if (element != element.getOwnerDocument().getDocumentElement())
            return;

        NamedNodeMap attributes = element.getAttributes();
        Node node = null;
        Collection<String> namespaceURIs = new ArrayList<String>(attributes.getLength());
        for (int i = 0; i < attributes.getLength(); i++) {
            node = attributes.item(i);
            if (node.getNodeName().startsWith(XMLNS.getLocalPart()))
                namespaceURIs.add(node.getNodeValue());
        }

        String namespaceLocations = "";
        for (String namespaceURI : namespaceURIs) {
            if (namespaceURI == null || namespaceURI.length() == 0 || XSI.getNamespaceURI().equals(namespaceURI))
                continue;

            namespaceLocations += " " + namespaceURI + " " + getSchemaLocation(namespaceURI);
        }

        element.setAttributeNS(XMLNS.getNamespaceURI(), XSI.getPrefix() + ":" + XSI.getLocalPart(), "http://www.w3.org/2001/XMLSchema-instance");
        element.setAttributeNS(XSI.getNamespaceURI(), "xsi:schemaLocation", namespaceLocations.substring(1));
        try {
            parse(element);
        }
        catch (IOException e) {
            throw new ValidationException(e);
        }
    }

    /**
     * This method allows a caller to get the schemaLocation <code>URL</code>
     * of the declaring namespaceURI.
     *
     * @param namespaceURI The namespaceURI that is defined at the
     * schemaLocation.
     *
     * @return The schemaLocation <code>URL</code>.
     */
    protected abstract URL getSchemaLocation(String namespaceURI);

    protected abstract void parse(Element element) throws IOException, ValidationException;

    public void validateMarshal(Element element) throws ValidationException {
        if (validateOnMarshal)
            validate(element);
    }

    public void validateParse(Element element) throws ValidationException {
        if (validateOnParse)
            validate(element);
    }
}
