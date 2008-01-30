package org.safris.xml.generator.compiler.processor.plan.element;

import org.safris.xml.generator.lexer.processor.model.element.SchemaModel;
import org.safris.xml.generator.compiler.processor.plan.Plan;

public class SchemaPlan extends Plan<SchemaModel>
{
	public SchemaPlan(SchemaModel model, Plan parent)
	{
		super(model, parent);
	}
}
