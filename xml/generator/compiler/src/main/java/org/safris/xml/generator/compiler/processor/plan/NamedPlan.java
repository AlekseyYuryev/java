package org.safris.xml.generator.compiler.processor.plan;

import java.util.HashMap;
import java.util.Map;
import org.safris.commons.xml.Prefix;
import org.safris.xml.generator.compiler.lang.CompilerError;
import org.safris.xml.generator.compiler.processor.plan.AnyablePlan;
import org.safris.xml.generator.compiler.processor.plan.NamedPlan;
import org.safris.xml.generator.compiler.processor.plan.Plan;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.lexer.processor.model.NamedModel;

public abstract class NamedPlan<T extends NamedModel> extends Plan<T> implements Nameable<Plan>
{
	protected static NamedPlan parseNamedPlan(UniqueQName name)
	{
		return all.get(name);
	}

	private static final Map<UniqueQName,NamedPlan> all = new HashMap<UniqueQName,NamedPlan>();

	private final UniqueQName name;

	public NamedPlan(T model, Plan parent)
	{
		super(model, parent);
		if(model.getName() != null)
		{
			final Prefix prefix = model.getName().getPrefix();
			if(prefix == null)
				throw new CompilerError("[ERROR] No prefix exists for namespace {" + model.getName().getNamespaceURI() + "}. Is the binding for this namespace defined in the bindings configuration?");

			name = UniqueQName.getInstance(model.getName().getNamespaceURI(), model.getName().getLocalPart(), prefix.toString());
			all.put(name, this);
		}
		else
			name = null;

		if(this instanceof AnyablePlan)
			return;

		if(name == null)
			throw new IllegalArgumentException(getClass().getSimpleName() + " with no name? what's going on?");
	}

	public final UniqueQName getName()
	{
		return name;
	}
}
