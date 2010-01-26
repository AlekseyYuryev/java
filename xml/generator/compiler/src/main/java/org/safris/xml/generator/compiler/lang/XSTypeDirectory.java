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

package org.safris.xml.generator.compiler.lang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.safris.commons.xml.binding.Base64Binary;
import org.safris.commons.xml.binding.Date;
import org.safris.commons.xml.binding.DateTime;
import org.safris.commons.xml.binding.Day;
import org.safris.commons.xml.binding.Decimal;
import org.safris.commons.xml.binding.Duration;
import org.safris.commons.xml.binding.HexBinary;
import org.safris.commons.xml.binding.Language;
import org.safris.commons.xml.binding.Month;
import org.safris.commons.xml.binding.MonthDay;
import org.safris.commons.xml.binding.Time;
import org.safris.commons.xml.binding.Year;
import org.safris.commons.xml.binding.YearMonth;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.NotationType;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.w3.x2001.xmlschema.$xs_anyURI;
import org.w3.x2001.xmlschema.$xs_base64Binary;
import org.w3.x2001.xmlschema.$xs_date;
import org.w3.x2001.xmlschema.$xs_dateTime;
import org.w3.x2001.xmlschema.$xs_decimal;
import org.w3.x2001.xmlschema.$xs_duration;
import org.w3.x2001.xmlschema.$xs_ENTITIES;
import org.w3.x2001.xmlschema.$xs_ENTITY;
import org.w3.x2001.xmlschema.$xs_gDay;
import org.w3.x2001.xmlschema.$xs_gMonth;
import org.w3.x2001.xmlschema.$xs_gMonthDay;
import org.w3.x2001.xmlschema.$xs_gYear;
import org.w3.x2001.xmlschema.$xs_gYearMonth;
import org.w3.x2001.xmlschema.$xs_hexBinary;
import org.w3.x2001.xmlschema.$xs_ID;
import org.w3.x2001.xmlschema.$xs_IDREF;
import org.w3.x2001.xmlschema.$xs_IDREFS;
import org.w3.x2001.xmlschema.$xs_int;
import org.w3.x2001.xmlschema.$xs_language;
import org.w3.x2001.xmlschema.$xs_long;
import org.w3.x2001.xmlschema.$xs_NCName;
import org.w3.x2001.xmlschema.$xs_NMTOKEN;
import org.w3.x2001.xmlschema.$xs_NMTOKENS;
import org.w3.x2001.xmlschema.$xs_NOTATION;
import org.w3.x2001.xmlschema.$xs_Name;
import org.w3.x2001.xmlschema.$xs_negativeInteger;
import org.w3.x2001.xmlschema.$xs_nonNegativeInteger;
import org.w3.x2001.xmlschema.$xs_nonPositiveInteger;
import org.w3.x2001.xmlschema.$xs_normalizedString;
import org.w3.x2001.xmlschema.$xs_positiveInteger;
import org.w3.x2001.xmlschema.$xs_QName;
import org.w3.x2001.xmlschema.$xs_short;
import org.w3.x2001.xmlschema.$xs_time;
import org.w3.x2001.xmlschema.$xs_token;
import org.w3.x2001.xmlschema.$xs_unsignedByte;
import org.w3.x2001.xmlschema.$xs_unsignedInt;
import org.w3.x2001.xmlschema.$xs_unsignedLong;
import org.w3.x2001.xmlschema.$xs_unsignedShort;
import org.w3.x2001.xmlschema.$xs_anySimpleType;
import org.w3.x2001.xmlschema.$xs_boolean;
import org.w3.x2001.xmlschema.$xs_byte;
import org.w3.x2001.xmlschema.$xs_double;
import org.w3.x2001.xmlschema.$xs_float;
import org.w3.x2001.xmlschema.$xs_integer;
import org.w3.x2001.xmlschema.$xs_string;

public final class XSTypeDirectory {
    private static final Map<UniqueQName,XSTypeDirectory> defaultTypes = new HashMap<UniqueQName,XSTypeDirectory>();
    private static final Map<UniqueQName,UniqueQName> typeHierarchy = new HashMap<UniqueQName,UniqueQName>();

    // may not need this...
    public static final XSTypeDirectory TYPE = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "Type"), new NativeBinding.GenericClass(Binding.class)), null);
    public static final XSTypeDirectory ANYTYPE = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "anyType"), new NativeBinding.GenericClass($xs_anySimpleType.class), new NativeBinding.GenericClass(Object.class)), null);

    public static final XSTypeDirectory ANYSIMPLETYPE = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "anySimpleType"), new NativeBinding.GenericClass($xs_anySimpleType.class), new NativeBinding.GenericClass(Object.class)), null);

    public static final XSTypeDirectory QNAME;

    static {
        // This section creates simpleType bindings for all of the base xs simple types.
        try {
            // FIXME: Have the nativeClasses hardcoded here be looked up using reflection during the generation process!!!!
            final XSTypeDirectory ENTITIES = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "ENTITIES"), new NativeBinding.GenericClass($xs_ENTITIES.class), new NativeBinding.GenericClass(List.class, String.class)), ANYSIMPLETYPE);
            final XSTypeDirectory string = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "string"), new NativeBinding.GenericClass($xs_string.class), new NativeBinding.GenericClass(String.class)), ANYSIMPLETYPE);
            final XSTypeDirectory normalizedString = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "normalizedString"), new NativeBinding.GenericClass($xs_normalizedString.class), new NativeBinding.GenericClass(String.class)), string);
            final XSTypeDirectory token = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "token"), new NativeBinding.GenericClass($xs_token.class), new NativeBinding.GenericClass(String.class)), normalizedString);
            final XSTypeDirectory Name = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "Name"), new NativeBinding.GenericClass($xs_Name.class), new NativeBinding.GenericClass(String.class)), token);
            final XSTypeDirectory NCName = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "NCName"), new NativeBinding.GenericClass($xs_NCName.class), new NativeBinding.GenericClass(String.class)), Name);
            final XSTypeDirectory ENTITY = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "ENTITY"), new NativeBinding.GenericClass($xs_ENTITY.class), new NativeBinding.GenericClass(String.class)), NCName);
            final XSTypeDirectory ID = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "ID"), new NativeBinding.GenericClass($xs_ID.class), new NativeBinding.GenericClass(String.class)), NCName);
            final XSTypeDirectory IDREF = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "IDREF"), new NativeBinding.GenericClass($xs_IDREF.class), new NativeBinding.GenericClass(String.class)), NCName);
            final XSTypeDirectory IDREFS = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "IDREFS"), new NativeBinding.GenericClass($xs_IDREFS.class), new NativeBinding.GenericClass(List.class, String.class)), ANYSIMPLETYPE);
            final XSTypeDirectory NMTOKEN = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "NMTOKEN"), new NativeBinding.GenericClass($xs_NMTOKEN.class), new NativeBinding.GenericClass(String.class)), token);
            final XSTypeDirectory NMTOKENS = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "NMTOKENS"), new NativeBinding.GenericClass($xs_NMTOKENS.class), new NativeBinding.GenericClass(List.class, String.class)), ANYSIMPLETYPE);
            QNAME = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "QName"), new NativeBinding.GenericClass($xs_QName.class), new NativeBinding.GenericClass(QName.class), QName.class.getDeclaredMethod("valueOf", String.class)), ANYSIMPLETYPE);
            final XSTypeDirectory anyURI = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "anyURI"), new NativeBinding.GenericClass($xs_anyURI.class), new NativeBinding.GenericClass(String.class)), ANYSIMPLETYPE);
            final XSTypeDirectory base64Binary = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "base64Binary"), new NativeBinding.GenericClass($xs_base64Binary.class), new NativeBinding.GenericClass(Base64Binary.class), Base64Binary.class.getDeclaredMethod("parseBase64Binary", String.class)), ANYSIMPLETYPE);
            final XSTypeDirectory _boolean = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "boolean"), new NativeBinding.GenericClass($xs_boolean.class), new NativeBinding.GenericClass(Boolean.class), $xs_boolean.class.getDeclaredMethod("parseBoolean", String.class)), ANYSIMPLETYPE);
            final XSTypeDirectory _long = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "long"), new NativeBinding.GenericClass($xs_long.class), new NativeBinding.GenericClass(Long.class), Long.class.getDeclaredMethod("parseLong", String.class)), ANYSIMPLETYPE);
            final XSTypeDirectory _int = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "int"), new NativeBinding.GenericClass($xs_int.class), new NativeBinding.GenericClass(Integer.class), Integer.class.getDeclaredMethod("parseInt", String.class)), _long);
            final XSTypeDirectory _short = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "short"), new NativeBinding.GenericClass($xs_short.class), new NativeBinding.GenericClass(Short.class), Short.class.getDeclaredMethod("parseShort", String.class)), _int);
            final XSTypeDirectory _byte = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "byte"), new NativeBinding.GenericClass($xs_byte.class), new NativeBinding.GenericClass(Byte.class), Byte.class.getDeclaredMethod("parseByte", String.class)), _short);
            final XSTypeDirectory date = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "date"), new NativeBinding.GenericClass($xs_date.class), new NativeBinding.GenericClass(Date.class), Date.class.getDeclaredMethod("parseDate", String.class)), ANYSIMPLETYPE);
            final XSTypeDirectory dateTime = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "dateTime"), new NativeBinding.GenericClass($xs_dateTime.class), new NativeBinding.GenericClass(DateTime.class), DateTime.class.getDeclaredMethod("parseDateTime", String.class)), ANYSIMPLETYPE);
            final XSTypeDirectory decimal = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "decimal"), new NativeBinding.GenericClass($xs_decimal.class), new NativeBinding.GenericClass(Decimal.class), Decimal.class.getDeclaredMethod("parseDecimal", String.class)), ANYSIMPLETYPE);
            final XSTypeDirectory _double = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "double"), new NativeBinding.GenericClass($xs_double.class), new NativeBinding.GenericClass(Double.class), Double.class.getDeclaredMethod("parseDouble", String.class)), ANYSIMPLETYPE);
            final XSTypeDirectory duration = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "duration"), new NativeBinding.GenericClass($xs_duration.class), new NativeBinding.GenericClass(Duration.class), Duration.class.getDeclaredMethod("parseDuration", String.class)), ANYSIMPLETYPE);
            final XSTypeDirectory _float = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "float"), new NativeBinding.GenericClass($xs_float.class), new NativeBinding.GenericClass(Float.class), Float.class.getDeclaredMethod("parseFloat", String.class)), ANYSIMPLETYPE);
            final XSTypeDirectory gDay = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "gDay"), new NativeBinding.GenericClass($xs_gDay.class), new NativeBinding.GenericClass(Day.class), Day.class.getDeclaredMethod("parseDay", String.class)), ANYSIMPLETYPE);
            final XSTypeDirectory gMonth = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "gMonth"), new NativeBinding.GenericClass($xs_gMonth.class), new NativeBinding.GenericClass(Month.class), Month.class.getDeclaredMethod("parseMonth", String.class)), ANYSIMPLETYPE);
            final XSTypeDirectory gMonthDay = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "gMonthDay"), new NativeBinding.GenericClass($xs_gMonthDay.class), new NativeBinding.GenericClass(MonthDay.class), MonthDay.class.getDeclaredMethod("parseMonthDay", String.class)), ANYSIMPLETYPE);
            final XSTypeDirectory gYear = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "gYear"), new NativeBinding.GenericClass($xs_gYear.class), new NativeBinding.GenericClass(Year.class), Year.class.getDeclaredMethod("parseYear", String.class)), ANYSIMPLETYPE);
            final XSTypeDirectory gYearMonth = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "gYearMonth"), new NativeBinding.GenericClass($xs_gYearMonth.class), new NativeBinding.GenericClass(YearMonth.class), YearMonth.class.getDeclaredMethod("parseYearMonth", String.class)), ANYSIMPLETYPE);
            final XSTypeDirectory hexBinary = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "hexBinary"), new NativeBinding.GenericClass($xs_hexBinary.class), new NativeBinding.GenericClass(HexBinary.class), HexBinary.class.getDeclaredMethod("parseHexBinary", String.class)), ANYSIMPLETYPE);
            final XSTypeDirectory integer = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "integer"), new NativeBinding.GenericClass($xs_integer.class), new NativeBinding.GenericClass(Integer.class), Integer.class.getDeclaredMethod("parseInt", String.class)), decimal);
            final XSTypeDirectory language = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "language"), new NativeBinding.GenericClass($xs_language.class), new NativeBinding.GenericClass(Language.class), Language.class.getDeclaredMethod("parseLanguage", String.class)), token);
            final XSTypeDirectory positiveInteger = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "positiveInteger"), new NativeBinding.GenericClass($xs_positiveInteger.class), new NativeBinding.GenericClass(Integer.class), Integer.class.getDeclaredMethod("parseInt", String.class)), integer);
            final XSTypeDirectory negativeInteger = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "negativeInteger"), new NativeBinding.GenericClass($xs_negativeInteger.class), new NativeBinding.GenericClass(Integer.class), Integer.class.getDeclaredMethod("parseInt", String.class)), positiveInteger);
            final XSTypeDirectory NOTATION = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "NOTATION"), new NativeBinding.GenericClass($xs_NOTATION.class), new NativeBinding.GenericClass(NotationType.class), NotationType.class.getDeclaredMethod("parseNotation", String.class)), ANYSIMPLETYPE);
            final XSTypeDirectory nonNegativeInteger = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "nonNegativeInteger"), new NativeBinding.GenericClass($xs_nonNegativeInteger.class), new NativeBinding.GenericClass(Integer.class), Integer.class.getDeclaredMethod("parseInt", String.class)), integer);
            final XSTypeDirectory nonPositiveInteger = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "nonPositiveInteger"), new NativeBinding.GenericClass($xs_nonPositiveInteger.class), new NativeBinding.GenericClass(Integer.class), Integer.class.getDeclaredMethod("parseInt", String.class)), integer);
            final XSTypeDirectory time = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "time"), new NativeBinding.GenericClass($xs_time.class), new NativeBinding.GenericClass(Time.class), Time.class.getDeclaredMethod("parseTime", String.class)), ANYSIMPLETYPE);
            final XSTypeDirectory unsignedLong = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "unsignedLong"), new NativeBinding.GenericClass($xs_unsignedLong.class), new NativeBinding.GenericClass(Long.class), Long.class.getDeclaredMethod("parseLong", String.class)), nonNegativeInteger);
            final XSTypeDirectory unsignedInt = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "unsignedInt"), new NativeBinding.GenericClass($xs_unsignedInt.class), new NativeBinding.GenericClass(Integer.class), Integer.class.getDeclaredMethod("parseInt", String.class)), unsignedLong);
            final XSTypeDirectory unsignedShort = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "unsignedShort"), new NativeBinding.GenericClass($xs_unsignedShort.class), new NativeBinding.GenericClass(Short.class), Short.class.getDeclaredMethod("parseShort", String.class)), unsignedInt);
            final XSTypeDirectory unsignedByte = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "unsignedByte"), new NativeBinding.GenericClass($xs_unsignedByte.class), new NativeBinding.GenericClass(Byte.class), Byte.class.getDeclaredMethod("parseByte", String.class)), unsignedShort);
        }
        catch (NoSuchMethodException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static final XSTypeDirectory parseType(UniqueQName name) {
        return defaultTypes.get(name);
    }

    public static UniqueQName lookupSuperType(UniqueQName name) {
        if (name == null)
            return null;

        return typeHierarchy.get(name);
    }

    private final NativeBinding nativeBinding;
    private final String nativeFactory;

    public NativeBinding getNativeBinding() {
        return nativeBinding;
    }

    public String getNativeFactory() {
        return nativeFactory;
    }

    private XSTypeDirectory(NativeBinding nativeBinding, final XSTypeDirectory superType) {
        this.nativeBinding = nativeBinding;
        if (nativeBinding.getFactoryMethod() != null)
            nativeFactory = nativeBinding.getFactoryMethod().getDeclaringClass().getName() + "." + nativeBinding.getFactoryMethod().getName();
        else
            nativeFactory = null;

        defaultTypes.put(nativeBinding.getName(), this);
        if (superType != null)
            typeHierarchy.put(nativeBinding.getName(), superType.getNativeBinding().getName());
    }
}
