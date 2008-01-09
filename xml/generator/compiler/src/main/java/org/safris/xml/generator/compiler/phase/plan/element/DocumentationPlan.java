package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.DocumentationModel;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class DocumentationPlan extends Plan<DocumentationModel>
{
	private final String text;
	private String preparedDocumentation = null;

	public static String getMetaDocumentation()
	{
		return " * @author Source generated with: <u>XML Toolkit for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>\n";
	}

	public DocumentationPlan(DocumentationModel model, Plan parent)
	{
		super(model, parent);
		this.text = model.getText();
	}

	public String getDocumentation()
	{
		if(preparedDocumentation != null)
			return preparedDocumentation;

		if(text.trim().length() == 0)
			return preparedDocumentation = "\t/**\n" + getMetaDocumentation() + " */\n";

		String text = this.text;
		text = text.replace("\t", " ");
		text = text.replace("\n\n", "^^^^");
		text = text.replace("\n", " ");
		text = text.replace("^^^^", "\n\n");
		String fixedSpaces = null;
		do
		{
			fixedSpaces = text;
			text = fixedSpaces.replace("  ", " ");
		}
		while(!text.equals(fixedSpaces));

		do
		{
			fixedSpaces = text;
			text = fixedSpaces.replace("\n\n", "\n \n *");
		}
		while(!text.equals(fixedSpaces));

		text = text.trim();
		String formatted = "\t/**\n";
		int start = 0;
		int end = 0;

		while((end = text.indexOf(" ", end + 74)) != -1)
		{
			int index = text.indexOf("\n", start);
			if(index != -1 && index < end)
				end = index;

			formatted += "\t * " + text.substring(start, end) + "\n";
			start = end + 1;
		}

		preparedDocumentation = formatted + "\t * " + text.substring(start, text.length()).replace(" * ", "") + "\n\n *\n" + getMetaDocumentation() + "\t */\n";
		preparedDocumentation = preparedDocumentation.replaceAll("\n[ \t]*\n", "\n");
		do
		{
			fixedSpaces = preparedDocumentation;
			preparedDocumentation = fixedSpaces.replace("* *", "*");
		}
		while(!preparedDocumentation.equals(fixedSpaces));

		do
		{
			fixedSpaces = preparedDocumentation;
			preparedDocumentation = fixedSpaces.replace(" *\n *\n", " *\n");
		}
		while(!preparedDocumentation.equals(fixedSpaces));

		return preparedDocumentation;
	}
}
