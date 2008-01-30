package org.safris.xml.generator.compiler.lang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.NotationType;
import org.safris.xml.generator.compiler.runtime.lang.Base64Binary;
import org.safris.xml.generator.compiler.runtime.lang.Date;
import org.safris.xml.generator.compiler.runtime.lang.DateTime;
import org.safris.xml.generator.compiler.runtime.lang.Day;
import org.safris.xml.generator.compiler.runtime.lang.Decimal;
import org.safris.xml.generator.compiler.runtime.lang.Duration;
import org.safris.xml.generator.compiler.runtime.lang.HexBinary;
import org.safris.xml.generator.compiler.runtime.lang.Language;
import org.safris.xml.generator.compiler.runtime.lang.Month;
import org.safris.xml.generator.compiler.runtime.lang.MonthDay;
import org.safris.xml.generator.compiler.runtime.lang.Time;
import org.safris.xml.generator.compiler.runtime.lang.Year;
import org.safris.xml.generator.compiler.runtime.lang.YearMonth;
import org.safris.xml.generator.processor.BindingQName;
import org.w3.x2001.xmlschema.IXSAnySimpleType;
import org.w3.x2001.xmlschema.IXSAnyURI;
import org.w3.x2001.xmlschema.IXSBase64Binary;
import org.w3.x2001.xmlschema.IXSBoolean;
import org.w3.x2001.xmlschema.IXSByte;
import org.w3.x2001.xmlschema.IXSDate;
import org.w3.x2001.xmlschema.IXSDateTime;
import org.w3.x2001.xmlschema.IXSDecimal;
import org.w3.x2001.xmlschema.IXSDouble;
import org.w3.x2001.xmlschema.IXSDuration;
import org.w3.x2001.xmlschema.IXSENTITIES;
import org.w3.x2001.xmlschema.IXSENTITY;
import org.w3.x2001.xmlschema.IXSFloat;
import org.w3.x2001.xmlschema.IXSGDay;
import org.w3.x2001.xmlschema.IXSGMonth;
import org.w3.x2001.xmlschema.IXSGMonthDay;
import org.w3.x2001.xmlschema.IXSGYear;
import org.w3.x2001.xmlschema.IXSGYearMonth;
import org.w3.x2001.xmlschema.IXSHexBinary;
import org.w3.x2001.xmlschema.IXSID;
import org.w3.x2001.xmlschema.IXSIDREF;
import org.w3.x2001.xmlschema.IXSIDREFS;
import org.w3.x2001.xmlschema.IXSInt;
import org.w3.x2001.xmlschema.IXSInteger;
import org.w3.x2001.xmlschema.IXSLanguage;
import org.w3.x2001.xmlschema.IXSLong;
import org.w3.x2001.xmlschema.IXSNCName;
import org.w3.x2001.xmlschema.IXSNMTOKEN;
import org.w3.x2001.xmlschema.IXSNMTOKENS;
import org.w3.x2001.xmlschema.IXSNOTATION;
import org.w3.x2001.xmlschema.IXSName;
import org.w3.x2001.xmlschema.IXSNegativeInteger;
import org.w3.x2001.xmlschema.IXSNonNegativeInteger;
import org.w3.x2001.xmlschema.IXSNonPositiveInteger;
import org.w3.x2001.xmlschema.IXSNormalizedString;
import org.w3.x2001.xmlschema.IXSPositiveInteger;
import org.w3.x2001.xmlschema.IXSQName;
import org.w3.x2001.xmlschema.IXSShort;
import org.w3.x2001.xmlschema.IXSString;
import org.w3.x2001.xmlschema.IXSTime;
import org.w3.x2001.xmlschema.IXSToken;
import org.w3.x2001.xmlschema.IXSUnsignedByte;
import org.w3.x2001.xmlschema.IXSUnsignedInt;
import org.w3.x2001.xmlschema.IXSUnsignedLong;
import org.w3.x2001.xmlschema.IXSUnsignedShort;

public final class XSTypeDirectory
{
	private static final Map<BindingQName,XSTypeDirectory> defaultTypes = new HashMap<BindingQName,XSTypeDirectory>();
	private static final Map<BindingQName,BindingQName> typeHierarchy = new HashMap<BindingQName,BindingQName>();

	// may not need this...
	public static final XSTypeDirectory TYPE = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "Type"), new NativeBinding.GenericClass(Binding.class)), null);
	public static final XSTypeDirectory ANYTYPE = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "anyType"), new NativeBinding.GenericClass(IXSAnySimpleType.class), new NativeBinding.GenericClass(Object.class)), null);

	public static final XSTypeDirectory ANYSIMPLETYPE = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "anySimpleType"), new NativeBinding.GenericClass(IXSAnySimpleType.class), new NativeBinding.GenericClass(Object.class)), null);

	public static final XSTypeDirectory QNAME;

	public NativeBinding getNativeBinding()
	{
		return nativeBinding;
	}

	public String getNativeFactory()
	{
		return nativeFactory;
	}

	static
	{
		// This section creates simpleType bindings for all of the base xs simple types.
		try
		{
			// FIXME: Have the nativeClasses hardcoded here be looked up using reflection during the generation process!!!!
			XSTypeDirectory ENTITIES = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "ENTITIES"), new NativeBinding.GenericClass(IXSENTITIES.class), new NativeBinding.GenericClass(List.class, String.class)), ANYSIMPLETYPE);
			XSTypeDirectory string = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "string"), new NativeBinding.GenericClass(IXSString.class), new NativeBinding.GenericClass(String.class)), ANYSIMPLETYPE);
			XSTypeDirectory normalizedString = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "normalizedString"), new NativeBinding.GenericClass(IXSNormalizedString.class), new NativeBinding.GenericClass(String.class)), string);
			XSTypeDirectory token = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "token"), new NativeBinding.GenericClass(IXSToken.class), new NativeBinding.GenericClass(String.class)), normalizedString);
			XSTypeDirectory Name = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "Name"), new NativeBinding.GenericClass(IXSName.class), new NativeBinding.GenericClass(String.class)), token);
			XSTypeDirectory NCName = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "NCName"), new NativeBinding.GenericClass(IXSNCName.class), new NativeBinding.GenericClass(String.class)), Name);
			XSTypeDirectory ENTITY = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "ENTITY"), new NativeBinding.GenericClass(IXSENTITY.class), new NativeBinding.GenericClass(String.class)), NCName);
			XSTypeDirectory ID = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "ID"), new NativeBinding.GenericClass(IXSID.class), new NativeBinding.GenericClass(String.class)), NCName);
			XSTypeDirectory IDREF = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "IDREF"), new NativeBinding.GenericClass(IXSIDREF.class), new NativeBinding.GenericClass(String.class)), NCName);
			XSTypeDirectory IDREFS = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "IDREFS"), new NativeBinding.GenericClass(IXSIDREFS.class), new NativeBinding.GenericClass(List.class, String.class)), ANYSIMPLETYPE);
			XSTypeDirectory NMTOKEN = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "NMTOKEN"), new NativeBinding.GenericClass(IXSNMTOKEN.class), new NativeBinding.GenericClass(String.class)), token);
			XSTypeDirectory NMTOKENS = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "NMTOKENS"), new NativeBinding.GenericClass(IXSNMTOKENS.class), new NativeBinding.GenericClass(List.class, String.class)), ANYSIMPLETYPE);
			QNAME = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "QName"), new NativeBinding.GenericClass(IXSQName.class), new NativeBinding.GenericClass(QName.class), QName.class.getDeclaredMethod("valueOf", String.class)), ANYSIMPLETYPE);
			XSTypeDirectory anyURI = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "anyURI"), new NativeBinding.GenericClass(IXSAnyURI.class), new NativeBinding.GenericClass(String.class)), ANYSIMPLETYPE);
			XSTypeDirectory base64Binary = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "base64Binary"), new NativeBinding.GenericClass(IXSBase64Binary.class), new NativeBinding.GenericClass(Base64Binary.class), Base64Binary.class.getDeclaredMethod("parseBase64Binary", String.class)), ANYSIMPLETYPE);
			XSTypeDirectory _boolean = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "boolean"), new NativeBinding.GenericClass(IXSBoolean.class), new NativeBinding.GenericClass(Boolean.class), IXSBoolean.class.getDeclaredMethod("parseBoolean", String.class)), ANYSIMPLETYPE);
			XSTypeDirectory _long = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "long"), new NativeBinding.GenericClass(IXSLong.class), new NativeBinding.GenericClass(Long.class), Long.class.getDeclaredMethod("parseLong", String.class)), ANYSIMPLETYPE);
			XSTypeDirectory _int = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "int"), new NativeBinding.GenericClass(IXSInt.class), new NativeBinding.GenericClass(Integer.class), Integer.class.getDeclaredMethod("parseInt", String.class)), _long);
			XSTypeDirectory _short = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "short"), new NativeBinding.GenericClass(IXSShort.class), new NativeBinding.GenericClass(Short.class), Short.class.getDeclaredMethod("parseShort", String.class)), _int);
			XSTypeDirectory _byte = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "byte"), new NativeBinding.GenericClass(IXSByte.class), new NativeBinding.GenericClass(Byte.class), Byte.class.getDeclaredMethod("parseByte", String.class)), _short);
			XSTypeDirectory date = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "date"), new NativeBinding.GenericClass(IXSDate.class), new NativeBinding.GenericClass(Date.class), Date.class.getDeclaredMethod("parseDate", String.class)), ANYSIMPLETYPE);
			XSTypeDirectory dateTime = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "dateTime"), new NativeBinding.GenericClass(IXSDateTime.class), new NativeBinding.GenericClass(DateTime.class), DateTime.class.getDeclaredMethod("parseDateTime", String.class)), ANYSIMPLETYPE);
			XSTypeDirectory decimal = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "decimal"), new NativeBinding.GenericClass(IXSDecimal.class), new NativeBinding.GenericClass(Decimal.class), Decimal.class.getDeclaredMethod("parseDecimal", String.class)), ANYSIMPLETYPE);
			XSTypeDirectory _double = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "double"), new NativeBinding.GenericClass(IXSDouble.class), new NativeBinding.GenericClass(Double.class), Double.class.getDeclaredMethod("parseDouble", String.class)), ANYSIMPLETYPE);
			XSTypeDirectory duration = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "duration"), new NativeBinding.GenericClass(IXSDuration.class), new NativeBinding.GenericClass(Duration.class), Duration.class.getDeclaredMethod("parseDuration", String.class)), ANYSIMPLETYPE);
			XSTypeDirectory _float = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "float"), new NativeBinding.GenericClass(IXSFloat.class), new NativeBinding.GenericClass(Float.class), Float.class.getDeclaredMethod("parseFloat", String.class)), ANYSIMPLETYPE);
			XSTypeDirectory gDay = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "gDay"), new NativeBinding.GenericClass(IXSGDay.class), new NativeBinding.GenericClass(Day.class), Day.class.getDeclaredMethod("parseDay", String.class)), ANYSIMPLETYPE);
			XSTypeDirectory gMonth = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "gMonth"), new NativeBinding.GenericClass(IXSGMonth.class), new NativeBinding.GenericClass(Month.class), Month.class.getDeclaredMethod("parseMonth", String.class)), ANYSIMPLETYPE);
			XSTypeDirectory gMonthDay = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "gMonthDay"), new NativeBinding.GenericClass(IXSGMonthDay.class), new NativeBinding.GenericClass(MonthDay.class), MonthDay.class.getDeclaredMethod("parseMonthDay", String.class)), ANYSIMPLETYPE);
			XSTypeDirectory gYear = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "gYear"), new NativeBinding.GenericClass(IXSGYear.class), new NativeBinding.GenericClass(Year.class), Year.class.getDeclaredMethod("parseYear", String.class)), ANYSIMPLETYPE);
			XSTypeDirectory gYearMonth = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "gYearMonth"), new NativeBinding.GenericClass(IXSGYearMonth.class), new NativeBinding.GenericClass(YearMonth.class), YearMonth.class.getDeclaredMethod("parseYearMonth", String.class)), ANYSIMPLETYPE);
			XSTypeDirectory hexBinary = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "hexBinary"), new NativeBinding.GenericClass(IXSHexBinary.class), new NativeBinding.GenericClass(HexBinary.class), HexBinary.class.getDeclaredMethod("parseHexBinary", String.class)), ANYSIMPLETYPE);
			XSTypeDirectory integer = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "integer"), new NativeBinding.GenericClass(IXSInteger.class), new NativeBinding.GenericClass(Integer.class), Integer.class.getDeclaredMethod("parseInt", String.class)), decimal);
			XSTypeDirectory language = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "language"), new NativeBinding.GenericClass(IXSLanguage.class), new NativeBinding.GenericClass(Language.class), Language.class.getDeclaredMethod("parseLanguage", String.class)), token);
			XSTypeDirectory positiveInteger = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "positiveInteger"), new NativeBinding.GenericClass(IXSPositiveInteger.class), new NativeBinding.GenericClass(Integer.class), Integer.class.getDeclaredMethod("parseInt", String.class)), integer);
			XSTypeDirectory negativeInteger = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "negativeInteger"), new NativeBinding.GenericClass(IXSNegativeInteger.class), new NativeBinding.GenericClass(Integer.class), Integer.class.getDeclaredMethod("parseInt", String.class)), positiveInteger);
			XSTypeDirectory NOTATION = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "NOTATION"), new NativeBinding.GenericClass(IXSNOTATION.class), new NativeBinding.GenericClass(NotationType.class), NotationType.class.getDeclaredMethod("parseNotation", String.class)), ANYSIMPLETYPE);
			XSTypeDirectory nonNegativeInteger = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "nonNegativeInteger"), new NativeBinding.GenericClass(IXSNonNegativeInteger.class), new NativeBinding.GenericClass(Integer.class), Integer.class.getDeclaredMethod("parseInt", String.class)), integer);
			XSTypeDirectory nonPositiveInteger = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "nonPositiveInteger"), new NativeBinding.GenericClass(IXSNonPositiveInteger.class), new NativeBinding.GenericClass(Integer.class), Integer.class.getDeclaredMethod("parseInt", String.class)), integer);
			XSTypeDirectory time = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "time"), new NativeBinding.GenericClass(IXSTime.class), new NativeBinding.GenericClass(Time.class), Time.class.getDeclaredMethod("parseTime", String.class)), ANYSIMPLETYPE);
			XSTypeDirectory unsignedLong = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "unsignedLong"), new NativeBinding.GenericClass(IXSUnsignedLong.class), new NativeBinding.GenericClass(Long.class), Long.class.getDeclaredMethod("parseLong", String.class)), nonNegativeInteger);
			XSTypeDirectory unsignedInt = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "unsignedInt"), new NativeBinding.GenericClass(IXSUnsignedInt.class), new NativeBinding.GenericClass(Integer.class), Integer.class.getDeclaredMethod("parseInt", String.class)), unsignedLong);
			XSTypeDirectory unsignedShort = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "unsignedShort"), new NativeBinding.GenericClass(IXSUnsignedShort.class), new NativeBinding.GenericClass(Short.class), Short.class.getDeclaredMethod("parseShort", String.class)), unsignedInt);
			XSTypeDirectory unsignedByte = new XSTypeDirectory(new NativeBinding(BindingQName.getInstance(BindingQName.XS.getNamespaceURI(), "unsignedByte"), new NativeBinding.GenericClass(IXSUnsignedByte.class), new NativeBinding.GenericClass(Byte.class), Byte.class.getDeclaredMethod("parseByte", String.class)), unsignedShort);
		}
		catch(NoSuchMethodException e)
		{
			throw new ExceptionInInitializerError(e);
		}
	}

	private final NativeBinding nativeBinding;
	private final String nativeFactory;

	private XSTypeDirectory(NativeBinding nativeBinding, XSTypeDirectory superType)
	{
		this.nativeBinding = nativeBinding;
		if(nativeBinding.getFactoryMethod() != null)
			nativeFactory = nativeBinding.getFactoryMethod().getDeclaringClass().getName() + "." + nativeBinding.getFactoryMethod().getName();
		else
			nativeFactory = null;

		defaultTypes.put(nativeBinding.getName(), this);
		if(superType != null)
			typeHierarchy.put(nativeBinding.getName(), superType.getNativeBinding().getName());
	}

	public static XSTypeDirectory parseType(BindingQName name)
	{
		return defaultTypes.get(name);
	}

	public static BindingQName lookupSuperType(BindingQName name)
	{
		if(name == null)
			return null;

		return typeHierarchy.get(name);
	}
}
