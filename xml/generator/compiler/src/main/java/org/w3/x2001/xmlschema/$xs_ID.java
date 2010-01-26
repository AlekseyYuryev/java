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

package org.w3.x2001.xmlschema;

import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.w3c.dom.Element;

public abstract class $xs_ID<T extends BindingType> extends $xs_NCName<T> {
    protected static final Map<String,Map<Object,$xs_ID>> namespaceIds = new HashMap<String,Map<Object,$xs_ID>>();

    private static void persist(String namespace, Object value, $xs_ID id) {
        Map<Object,$xs_ID> idMap = namespaceIds.get(namespace);
        if (idMap == null)
            namespaceIds.put(namespace, idMap = new HashMap<Object,$xs_ID>());

        idMap.put(value, id);
    }

    private static void remove(String namespace, Object value) {
        final Map<Object,$xs_ID> ids = namespaceIds.get(namespace);
        if (ids == null)
            return;

        ids.remove(value);
    }

    public static $xs_ID lookupId(Object id) {
        final Map<Object,$xs_ID> ids = namespaceIds.get(UniqueQName.XS.getNamespaceURI().toString());
        if (ids == null)
            return null;

        return ids.get(id);
    }

    public $xs_ID($xs_ID<T> binding) {
        super(binding);
    }

    public $xs_ID(String value) {
        super(value);
        persist(_$$getName().getNamespaceURI(), value, this);
    }

    protected $xs_ID() {
        super();
    }

    protected String getText() {
        return super.getText();
    }

    protected void setText(String text) {
        final Object old = getText();
        super.setText(text);
        if (old != null)
            remove(_$$getName().getNamespaceURI(), old);

        persist(_$$getName().getNamespaceURI(), text, this);
    }

    protected void _$$decode(Element parent, String value) throws ParseException {
        persist(parent.getNamespaceURI(), value, this);
        super.setText(value);
    }

    protected String _$$encode(Element parent) throws MarshalException {
        if (super.getText() == null)
            return "";

        return super.getText().toString();
    }

    public $xs_ID clone() {
        return new $xs_ID(this) {
            protected $xs_ID inherits() {
                return this;
            }
        };
    }
}
