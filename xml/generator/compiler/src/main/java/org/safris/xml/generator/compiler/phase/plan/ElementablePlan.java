package org.safris.xml.generator.compiler.phase.plan;

import java.util.LinkedHashSet;
import org.safris.xml.generator.compiler.phase.plan.element.ElementPlan;

public interface ElementablePlan
{
	public LinkedHashSet<ElementPlan> getElements();
}
