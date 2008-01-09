package org.safris.xml.generator.compiler.phase.plan.element;

import org.safris.xml.generator.lexer.phase.model.element.NotationModel;
import org.safris.xml.generator.compiler.phase.plan.AliasPlan;
import org.safris.xml.generator.compiler.phase.plan.Plan;

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
