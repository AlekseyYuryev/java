package org.safris.xml.generator.compiler.processor.plan;

import java.util.LinkedHashSet;
import org.safris.xml.generator.compiler.processor.plan.element.ElementPlan;

public interface ElementablePlan
{
	public LinkedHashSet<ElementPlan> getElements();
}
