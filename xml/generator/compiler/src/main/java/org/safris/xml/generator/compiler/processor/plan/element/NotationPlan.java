package org.safris.xml.generator.compiler.processor.plan.element;

import org.safris.xml.generator.lexer.processor.model.element.NotationModel;
import org.safris.xml.generator.compiler.processor.plan.AliasPlan;
import org.safris.xml.generator.compiler.processor.plan.Plan;

public class NotationPlan extends AliasPlan<NotationModel>
{
	private final String _public;
	private final String system;

	public NotationPlan(NotationModel model, Plan parent)
	{
		super(model, parent);
		this._public = model.getPublic();
		this.system = model.getSystem();
	}

	public String getPublic()
	{
		return _public;
	}

	public String getSystem()
	{
		return system;
	}
}
