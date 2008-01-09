package org.safris.xml.generator.compiler.phase.plan;

import java.util.LinkedHashSet;
import org.safris.xml.generator.compiler.phase.plan.element.AttributePlan;

public interface AttributablePlan
{
	public LinkedHashSet<AttributePlan> getAttributes();
}
