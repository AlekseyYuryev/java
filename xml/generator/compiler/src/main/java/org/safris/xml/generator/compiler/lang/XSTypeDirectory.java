package org.safris.xml.generator.compiler.lang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.NotationType;
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
import org.safris.xml.generator.lexer.lang.UniqueQName;
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
	private static final Map<UniqueQName,XSTypeDirectory> defaultTypes = new HashMap<UniqueQName,XSTypeDirectory>();
	private static final Map<UniqueQName,UniqueQName> typeHierarchy = new HashMap<UniqueQName,UniqueQName>();

	// may not need this...
	public static final XSTypeDirectory TYPE = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "Type"), new NativeBinding.GenericClass(Binding.class)), null);
	public static final XSTypeDirectory ANYTYPE = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "anyType"), new NativeBinding.GenericClass(IXSAnySimpleType.class), new NativeBinding.GenericClass(Object.class)), null);

	public static final XSTypeDirectory ANYSIMPLETYPE = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "anySimpleType"), new NativeBinding.GenericClass(IXSAnySimpleType.class), new NativeBinding.GenericClass(Object.class)), null);

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
			final XSTypeDirectory ENTITIES = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "ENTITIES"), new NativeBinding.GenericClass(IXSENTITIES.class), new NativeBinding.GenericClass(List.class, String.class)), ANYSIMPLETYPE);
			final XSTypeDirectory string = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "string"), new NativeBinding.GenericClass(IXSString.class), new NativeBinding.GenericClass(String.class)), ANYSIMPLETYPE);
			final XSTypeDirectory normalizedString = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "normalizedString"), new NativeBinding.GenericClass(IXSNormalizedString.class), new NativeBinding.GenericClass(String.class)), string);
			final XSTypeDirectory token = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "token"), new NativeBinding.GenericClass(IXSToken.class), new NativeBinding.GenericClass(String.class)), normalizedString);
			final XSTypeDirectory Name = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "Name"), new NativeBinding.GenericClass(IXSName.class), new NativeBinding.GenericClass(String.class)), token);
			final XSTypeDirectory NCName = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "NCName"), new NativeBinding.GenericClass(IXSNCName.class), new NativeBinding.GenericClass(String.class)), Name);
			final XSTypeDirectory ENTITY = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "ENTITY"), new NativeBinding.GenericClass(IXSENTITY.class), new NativeBinding.GenericClass(String.class)), NCName);
			final XSTypeDirectory ID = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "ID"), new NativeBinding.GenericClass(IXSID.class), new NativeBinding.GenericClass(String.class)), NCName);
			final XSTypeDirectory IDREF = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "IDREF"), new NativeBinding.GenericClass(IXSIDREF.class), new NativeBinding.GenericClass(String.class)), NCName);
			final XSTypeDirectory IDREFS = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "IDREFS"), new NativeBinding.GenericClass(IXSIDREFS.class), new NativeBinding.GenericClass(List.class, String.class)), ANYSIMPLETYPE);
			final XSTypeDirectory NMTOKEN = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "NMTOKEN"), new NativeBinding.GenericClass(IXSNMTOKEN.class), new NativeBinding.GenericClass(String.class)), token);
			final XSTypeDirectory NMTOKENS = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "NMTOKENS"), new NativeBinding.GenericClass(IXSNMTOKENS.class), new NativeBinding.GenericClass(List.class, String.class)), ANYSIMPLETYPE);
			QNAME = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "QName"), new NativeBinding.GenericClass(IXSQName.class), new NativeBinding.GenericClass(QName.class), QName.class.getDeclaredMethod("valueOf", String.class)), ANYSIMPLETYPE);
			final XSTypeDirectory anyURI = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "anyURI"), new NativeBinding.GenericClass(IXSAnyURI.class), new NativeBinding.GenericClass(String.class)), ANYSIMPLETYPE);
			final XSTypeDirectory base64Binary = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "base64Binary"), new NativeBinding.GenericClass(IXSBase64Binary.class), new NativeBinding.GenericClass(Base64Binary.class), Base64Binary.class.getDeclaredMethod("parseBase64Binary", String.class)), ANYSIMPLETYPE);
			final XSTypeDirectory _boolean = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "boolean"), new NativeBinding.GenericClass(IXSBoolean.class), new NativeBinding.GenericClass(Boolean.class), IXSBoolean.class.getDeclaredMethod("parseBoolean", String.class)), ANYSIMPLETYPE);
			final XSTypeDirectory _long = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "long"), new NativeBinding.GenericClass(IXSLong.class), new NativeBinding.GenericClass(Long.class), Long.class.getDeclaredMethod("parseLong", String.class)), ANYSIMPLETYPE);
			final XSTypeDirectory _int = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "int"), new NativeBinding.GenericClass(IXSInt.class), new NativeBinding.GenericClass(Integer.class), Integer.class.getDeclaredMethod("parseInt", String.class)), _long);
			final XSTypeDirectory _short = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "short"), new NativeBinding.GenericClass(IXSShort.class), new NativeBinding.GenericClass(Short.class), Short.class.getDeclaredMethod("parseShort", String.class)), _int);
			final XSTypeDirectory _byte = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "byte"), new NativeBinding.GenericClass(IXSByte.class), new NativeBinding.GenericClass(Byte.class), Byte.class.getDeclaredMethod("parseByte", String.class)), _short);
			final XSTypeDirectory date = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "date"), new NativeBinding.GenericClass(IXSDate.class), new NativeBinding.GenericClass(Date.class), Date.class.getDeclaredMethod("parseDate", String.class)), ANYSIMPLETYPE);
			final XSTypeDirectory dateTime = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "dateTime"), new NativeBinding.GenericClass(IXSDateTime.class), new NativeBinding.GenericClass(DateTime.class), DateTime.class.getDeclaredMethod("parseDateTime", String.class)), ANYSIMPLETYPE);
			final XSTypeDirectory decimal = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "decimal"), new NativeBinding.GenericClass(IXSDecimal.class), new NativeBinding.GenericClass(Decimal.class), Decimal.class.getDeclaredMethod("parseDecimal", String.class)), ANYSIMPLETYPE);
			final XSTypeDirectory _double = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "double"), new NativeBinding.GenericClass(IXSDouble.class), new NativeBinding.GenericClass(Double.class), Double.class.getDeclaredMethod("parseDouble", String.class)), ANYSIMPLETYPE);
			final XSTypeDirectory duration = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "duration"), new NativeBinding.GenericClass(IXSDuration.class), new NativeBinding.GenericClass(Duration.class), Duration.class.getDeclaredMethod("parseDuration", String.class)), ANYSIMPLETYPE);
			final XSTypeDirectory _float = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "float"), new NativeBinding.GenericClass(IXSFloat.class), new NativeBinding.GenericClass(Float.class), Float.class.getDeclaredMethod("parseFloat", String.class)), ANYSIMPLETYPE);
			final XSTypeDirectory gDay = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "gDay"), new NativeBinding.GenericClass(IXSGDay.class), new NativeBinding.GenericClass(Day.class), Day.class.getDeclaredMethod("parseDay", String.class)), ANYSIMPLETYPE);
			final XSTypeDirectory gMonth = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "gMonth"), new NativeBinding.GenericClass(IXSGMonth.class), new NativeBinding.GenericClass(Month.class), Month.class.getDeclaredMethod("parseMonth", String.class)), ANYSIMPLETYPE);
			final XSTypeDirectory gMonthDay = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "gMonthDay"), new NativeBinding.GenericClass(IXSGMonthDay.class), new NativeBinding.GenericClass(MonthDay.class), MonthDay.class.getDeclaredMethod("parseMonthDay", String.class)), ANYSIMPLETYPE);
			final XSTypeDirectory gYear = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "gYear"), new NativeBinding.GenericClass(IXSGYear.class), new NativeBinding.GenericClass(Year.class), Year.class.getDeclaredMethod("parseYear", String.class)), ANYSIMPLETYPE);
			final XSTypeDirectory gYearMonth = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "gYearMonth"), new NativeBinding.GenericClass(IXSGYearMonth.class), new NativeBinding.GenericClass(YearMonth.class), YearMonth.class.getDeclaredMethod("parseYearMonth", String.class)), ANYSIMPLETYPE);
			final XSTypeDirectory hexBinary = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "hexBinary"), new NativeBinding.GenericClass(IXSHexBinary.class), new NativeBinding.GenericClass(HexBinary.class), HexBinary.class.getDeclaredMethod("parseHexBinary", String.class)), ANYSIMPLETYPE);
			final XSTypeDirectory integer = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "integer"), new NativeBinding.GenericClass(IXSInteger.class), new NativeBinding.GenericClass(Integer.class), Integer.class.getDeclaredMethod("parseInt", String.class)), decimal);
			final XSTypeDirectory language = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "language"), new NativeBinding.GenericClass(IXSLanguage.class), new NativeBinding.GenericClass(Language.class), Language.class.getDeclaredMethod("parseLanguage", String.class)), token);
			final XSTypeDirectory positiveInteger = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "positiveInteger"), new NativeBinding.GenericClass(IXSPositiveInteger.class), new NativeBinding.GenericClass(Integer.class), Integer.class.getDeclaredMethod("parseInt", String.class)), integer);
			final XSTypeDirectory negativeInteger = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "negativeInteger"), new NativeBinding.GenericClass(IXSNegativeInteger.class), new NativeBinding.GenericClass(Integer.class), Integer.class.getDeclaredMethod("parseInt", String.class)), positiveInteger);
			final XSTypeDirectory NOTATION = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "NOTATION"), new NativeBinding.GenericClass(IXSNOTATION.class), new NativeBinding.GenericClass(NotationType.class), NotationType.class.getDeclaredMethod("parseNotation", String.class)), ANYSIMPLETYPE);
			final XSTypeDirectory nonNegativeInteger = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "nonNegativeInteger"), new NativeBinding.GenericClass(IXSNonNegativeInteger.class), new NativeBinding.GenericClass(Integer.class), Integer.class.getDeclaredMethod("parseInt", String.class)), integer);
			final XSTypeDirectory nonPositiveInteger = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "nonPositiveInteger"), new NativeBinding.GenericClass(IXSNonPositiveInteger.class), new NativeBinding.GenericClass(Integer.class), Integer.class.getDeclaredMethod("parseInt", String.class)), integer);
			final XSTypeDirectory time = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "time"), new NativeBinding.GenericClass(IXSTime.class), new NativeBinding.GenericClass(Time.class), Time.class.getDeclaredMethod("parseTime", String.class)), ANYSIMPLETYPE);
			final XSTypeDirectory unsignedLong = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "unsignedLong"), new NativeBinding.GenericClass(IXSUnsignedLong.class), new NativeBinding.GenericClass(Long.class), Long.class.getDeclaredMethod("parseLong", String.class)), nonNegativeInteger);
			final XSTypeDirectory unsignedInt = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "unsignedInt"), new NativeBinding.GenericClass(IXSUnsignedInt.class), new NativeBinding.GenericClass(Integer.class), Integer.class.getDeclaredMethod("parseInt", String.class)), unsignedLong);
			final XSTypeDirectory unsignedShort = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "unsignedShort"), new NativeBinding.GenericClass(IXSUnsignedShort.class), new NativeBinding.GenericClass(Short.class), Short.class.getDeclaredMethod("parseShort", String.class)), unsignedInt);
			final XSTypeDirectory unsignedByte = new XSTypeDirectory(new NativeBinding(UniqueQName.getInstance(UniqueQName.XS.getNamespaceURI(), "unsignedByte"), new NativeBinding.GenericClass(IXSUnsignedByte.class), new NativeBinding.GenericClass(Byte.class), Byte.class.getDeclaredMethod("parseByte", String.class)), unsignedShort);
		}
		catch(NoSuchMethodException e)
		{
			throw new ExceptionInInitializerError(e);
		}
	}

	private final NativeBinding nativeBinding;
	private final String nativeFactory;

	private XSTypeDirectory(NativeBinding nativeBinding, final XSTypeDirectory superType)
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

	public static final XSTypeDirectory parseType(UniqueQName name)
	{
		return defaultTypes.get(name);
	}

	public static UniqueQName lookupSuperType(UniqueQName name)
	{
		if(name == null)
			return null;

		return typeHierarchy.get(name);
	}
}
