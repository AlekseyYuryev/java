package org.safris.xml.generator.compiler.lang;

import org.safris.commons.xml.Prefix;
import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.element.AttributeModel;
import org.safris.xml.generator.lexer.processor.model.element.ElementModel;
import org.safris.xml.generator.lexer.processor.model.element.NotationModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;

public abstract class JavaBinding
{
	private final static String ATTRIBUTE_SUFFIX = "Attr";
	private final static String NOTATION_SUFFIX = "Notation";
	private final static String COMPLEXTYPE_PREFIX = "I";

	public static String getInstanceName(Model model)
	{
		if(!(model instanceof Nameable) || ((Nameable)model).getName() == null)
			throw new CompilerError("javabinding");

		final Prefix prefix = ((Nameable)model).getName().getPrefix();
		if(model instanceof AttributeModel)
			return "_" + prefix.toStringLowerCase() + toTitleCase(((SimpleTypeModel)model).getName().getLocalPart()) + ATTRIBUTE_SUFFIX;
		else if(model instanceof ElementModel)
			return "_" + prefix.toStringLowerCase() + toTitleCase(((SimpleTypeModel)model).getName().getLocalPart());
		else if(model instanceof NotationModel)
			return "_" + prefix.toStringLowerCase() + toTitleCase(((NotationModel)model).getName().getLocalPart()) + NOTATION_SUFFIX;
		else if(model instanceof SimpleTypeModel)
			return "_" + COMPLEXTYPE_PREFIX.toLowerCase() + prefix.toStringTitleCase() + toTitleCase(((SimpleTypeModel)model).getName().getLocalPart());

		throw new CompilerError("something wrong here");
	}

	public static String getClassName(Model model)
	{
		if(model == null)
			return null;

		if(!(model instanceof Nameable) || ((Nameable)model).getName() == null)
			throw new CompilerError("javabinding");

		final String pkg = ((Nameable)model).getName().getNamespaceURI().getPackageName() + ".";
		final Prefix prefix = ((Nameable)model).getName().getPrefix();
		if(model instanceof AttributeModel)
			return pkg + prefix.toStringTitleCase() + toTitleCase(((SimpleTypeModel)model).getName().getLocalPart()) + ATTRIBUTE_SUFFIX;
		else if(model instanceof ElementModel)
			return pkg + prefix.toStringTitleCase() + toTitleCase(((SimpleTypeModel)model).getName().getLocalPart());
		else if(model instanceof NotationModel)
			return pkg + prefix.toStringTitleCase() + toTitleCase(((NotationModel)model).getName().getLocalPart()) + NOTATION_SUFFIX;
		else if(model instanceof SimpleTypeModel)
			return pkg + COMPLEXTYPE_PREFIX + prefix.toStringTitleCase() + toTitleCase(((SimpleTypeModel)model).getName().getLocalPart());

		throw new CompilerError("something wrong here");
	}

	public static String getClassSimpleName(Model model)
	{
		if(!(model instanceof Nameable) || ((Nameable)model).getName() == null)
			throw new CompilerError("javabinding");

		final Prefix prefix = ((Nameable)model).getName().getPrefix();
		if(model instanceof AttributeModel)
			return prefix.toStringTitleCase() + toTitleCase(((SimpleTypeModel)model).getName().getLocalPart()) + ATTRIBUTE_SUFFIX;
		else if(model instanceof ElementModel)
			return prefix.toStringTitleCase() + toTitleCase(((SimpleTypeModel)model).getName().getLocalPart());
		else if(model instanceof NotationModel)
			return prefix.toStringTitleCase() + toTitleCase(((NotationModel)model).getName().getLocalPart()) + NOTATION_SUFFIX;
		else if(model instanceof SimpleTypeModel)
			return COMPLEXTYPE_PREFIX + prefix.toStringTitleCase() + toTitleCase(((SimpleTypeModel)model).getName().getLocalPart());

		throw new CompilerError("something wrong here");
	}

	protected static String toLowerCase(String string)
	{
		string = javaCase(string);

		if(string.length() == 1)
			return string.toLowerCase();

		int index = 0;
		char[] chars = string.toCharArray();
		for(int i = 0; i < chars.length; i++)
		{
			index = i;
			if(('0' <= chars[i] && chars[i] <= '9') || 'a' <= chars[i] && chars[i] <= 'z')
				break;
		}

		if(index == 1)
			return string.substring(0, 1).toLowerCase() + string.substring(1, string.length());
		else if(index == string.length() - 1)
			return string.toLowerCase();
		else if(index > 1)
			return string.substring(0, index - 1).toLowerCase() + string.substring(index - 1, string.length());
		else
			return string;
	}

	protected static String toTitleCase(String string)
	{
		if(string == null)
			return null;

		string = javaCase(string);

		// make sure that the fully qualified names are not changed
		if(string.indexOf(".") != -1)
			return string;

		return string.substring(0, 1).toUpperCase() + string.substring(1, string.length());
	}

	private static String legalizeForJava(String string)
	{
		return string.replace('-', '_').replace("#", "");
	}

	private static String javaCase(String string)
	{
		if(string == null)
			return null;

		int start = 0;
		int end = 0;
		while(start < (end = string.indexOf("-", start)))
		{
			if(end != -1)
				string = string.substring(0, end) + string.substring(end + 1, end + 2).toUpperCase() + string.substring(end + 2, string.length());

			start = end + 1;
		}

		start = 0;
		while(start < (end = string.indexOf(".", start)))
		{
			if(end != -1)
				string = string.substring(0, end) + string.substring(end + 1, end + 2).toUpperCase() + string.substring(end + 2, string.length());

			start = end + 1;
		}

		return legalizeForJava(string);
	}
}
