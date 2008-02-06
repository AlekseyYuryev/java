package org.safris.commons.formatter;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class SourceFormat
{
	public static SourceFormat getDefaultFormat()
	{
		return new SourceFormat();
	}

	private List<FormatModule> modules = new LinkedList<FormatModule>();

	private SourceFormat()
	{
		addModule(new PackageModule());
		addModule(new DocumentationModule());
		addModule(new ImportModule());
		addModule(new MethodModule());
		addModule(new FieldModule());
		addModule(new ClassModule());
		addModule(new OpenBracketModule());
		addModule(new CloseBracketModule());
		addModule(new DeclarationModule());
		addModule(new StatementModule());
	}

	public void addModule(FormatModule module)
	{
		modules.add(module);
	}

	public String format(String unformated)
	{
		if(unformated == null)
			return "";

		FormatModule.restetDepth();

		String formated = "";
		try
		{
			StringTokenizer stringTokenizer = new StringTokenizer(unformated, "\t\n\r\f");
			String token = null;
			while(stringTokenizer.hasMoreTokens())
			{
				token = stringTokenizer.nextToken();
				formated = modules(formated, token);
			}
		}
		catch(Exception e)
		{
			throw new FormatError(e);
		}

		return formated;
	}

	private String modules(String formated, String token)
	{
		String formatedToken = token;
		for(int i = 0; i < modules.size(); i++)
		{
			FormatModule module = modules.get(i);
			token = module.format(formated, token);

			/*			if(FormatModule.getLastModule() instanceof OpenBracketModule && !formated.endsWith("\n") && !token.startsWith("\n"))
			 {
			 for(int j = 0; j < module.getDepth(); j++)
			 {
			 token = "\t" + token;
			 }
			 token = "\n" + token;
			 }*/

			if(!formatedToken.equals(token))
			{
				FormatModule.setLastModule(module);
				break;
			}

			formatedToken = token;
		}

		return formated += token;
	}
}
