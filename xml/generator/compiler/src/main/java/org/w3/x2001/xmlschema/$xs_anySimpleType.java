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

import java.util.Collection;
import javax.xml.namespace.QName;
import org.safris.commons.xml.validator.ValidationException;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public abstract class $xs_anySimpleType<T extends BindingType> extends Binding<T> {
    private Object text = null;

    private $xs_anySimpleType($xs_anySimpleType<T> binding) {
        super(binding);
        this.text = binding.text;
    }

    public $xs_anySimpleType(Object text) {
        super();
        this.text = text;
    }

    protected $xs_anySimpleType(String text) {
        super();
        this.text = text;
    }

    protected $xs_anySimpleType() {
        super();
    }

    protected void setText(Object text) {
        this.text = text;
    }

    protected Object getText() {
        return text;
    }

    protected void _$$decode(Element parent, String value) throws ParseException {
        this.text = value;
    }

    protected String _$$encode(Element parent) throws MarshalException {
        // FIXME: Is it right to return "" for a null getText()?
        if (getText() == null)
            return "";

        if (!(getText() instanceof Collection))
            return getText().toString();

        final StringBuffer stringBuffer = new StringBuffer();
        for (Object obj : (Collection)getText())
            stringBuffer.append(" ").append(obj.toString());

        return stringBuffer.substring(1);
    }

    private transient Element parent = null;

    protected Element marshal(Element parent, QName name, QName typeName) throws MarshalException {
        this.parent = parent;
        final Element element = super.marshal(parent, name, typeName);
        if (text != null)
            element.appendChild(parent.getOwnerDocument().createTextNode(String.valueOf(_$$encode(parent))));

        return element;
    }

    protected Attr marshalAttr(String name, Element parent) throws MarshalException {
        this.parent = parent;
        final Attr attr = parent.getOwnerDocument().createAttribute(name);
        attr.setNodeValue(String.valueOf(_$$encode(parent)));
        return attr;
    }

    protected void parseText(Text text) throws ParseException, ValidationException {
        // Ignore all attributes that have a xsi prefix because these are
        // controlled implicitly by the framework
        if (XSI_NIL.getPrefix().equals(text.getPrefix()))
            return;

        final StringBuffer value = new StringBuffer("");
        if (text.getNodeValue() != null)
            value.append(text.getNodeValue().trim());

        if (value.length() == 0)
            return;

        _$$decode((Element)text.getParentNode(), value.toString());
    }

    protected QName _$$getName() {
        return _$$getName(inherits());
    }

    protected QName _$$getTypeName() {
        return null;
    }

    public $xs_anySimpleType clone() {
        return new $xs_anySimpleType(this) {
            protected $xs_anySimpleType inherits() {
                return this;
            }
        };
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof $xs_anySimpleType))
            return false;

        final $xs_anySimpleType that = ($xs_anySimpleType)obj;
        try {
            final String thisEncoded = _$$encode(parent);
            final String thatEncoded = that._$$encode(parent);
            return thisEncoded != null ? thisEncoded.equals(thatEncoded) : thatEncoded == null;
        }
        catch (MarshalException e) {
            return false;
        }
    }

    public int hashCode() {
        final String value;
        try {
            value = _$$encode(parent);
        }
        catch (MarshalException e) {
            return super.hashCode();
        }

        if (value == null)
            return super.hashCode();

        return value.hashCode();
    }

    public String toString() {
        try {
            return String.valueOf(_$$encode(parent));
        }
        catch (MarshalException e) {
            return super.toString();
        }
    }
}
