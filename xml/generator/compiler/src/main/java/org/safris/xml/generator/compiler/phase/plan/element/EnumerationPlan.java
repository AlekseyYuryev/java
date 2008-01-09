package org.safris.xml.generator.compiler.phase.plan.element;

import javax.xml.namespace.QName;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.lexer.phase.model.element.EnumerationModel;

public class EnumerationPlan extends Plan<EnumerationModel>
{
	private String declarationName = null;
	private final QName value;

	public static String getDeclarationName(QName value)
	{
		String string = null;
		if(47 < value.getLocalPart().charAt(0) && value.getLocalPart().charAt(0) < 58)
			string = "_" + value.getLocalPart();
		else
			string = value.getLocalPart();

		if(value.getPrefix() != null && value.getPrefix().toString().length() != 0)
			string = value.getPrefix() + "_" + string;

		string = string.replace('.', '_');
		string = string.replace('-', '_');
		return string.toUpperCase();
	}

	public EnumerationPlan(EnumerationModel model, Plan parent)
	{
		super(model, parent);
		this.value = model.getValue();
	}

	public QName getValue()
	{
		return value;
	}

	public String getDeclarationName()
	{
		if(declarationName != null)
			return declarationName;

		if(getModel().getValue().getLocalPart().length() == 0)
			throw new CompilerError("The localPart of this enumeration cannot be length == 0");

		return declarationName = getDeclarationName(getModel().getValue());
	}
}
