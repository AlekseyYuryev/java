package org.safris.xml.generator.compiler.phase.plan;

import org.safris.commons.util.logging.ExitSevereError;
import org.safris.commons.util.xml.BindingQName;
import org.safris.commons.util.xml.Prefix;
import org.safris.xml.generator.compiler.phase.plan.AnyablePlan;
import org.safris.xml.generator.compiler.phase.plan.Plan;
import org.safris.xml.generator.lexer.phase.model.NamedModel;
import org.safris.xml.generator.module.phase.Nameable;

public abstract class NamedPlan<T extends NamedModel> extends Plan<T> implements Nameable<Plan>
{
	private final BindingQName name;

	public NamedPlan(T model, Plan parent)
	{
		super(model, parent);
		if(model.getName() != null)
		{
			final Prefix prefix = model.getName().getPrefix();
			if(prefix == null)
				throw new ExitSevereError("[ERROR] No prefix exists for namespace {" + model.getName().getNamespaceURI() + "}. Is the binding for this namespace defined in the bindings configuration?");

			name = BindingQName.getInstance(model.getName().getNamespaceURI(), model.getName().getLocalPart(), prefix.toStringLowerCase());
		}
		else
			name = null;

		if(this instanceof AnyablePlan)
			return;

		if(name == null)
			throw new IllegalArgumentException(getClass().getSimpleName() + " with no name? what's going on?");
	}

	public final BindingQName getName()
	{
		return name;
	}
}
