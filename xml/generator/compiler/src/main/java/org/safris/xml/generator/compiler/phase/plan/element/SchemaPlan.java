package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.SchemaModel;
import org.safris.xml.generator.compiler.phase.plan.Plan;

public class SchemaPlan extends Plan<SchemaModel>
{
	public SchemaPlan(SchemaModel model, Plan parent)
	{
		super(model, parent);
	}
}
