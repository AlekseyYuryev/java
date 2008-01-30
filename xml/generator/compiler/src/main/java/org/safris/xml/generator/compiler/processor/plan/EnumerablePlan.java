package org.safris.xml.generator.compiler.processor.plan;

import java.util.LinkedHashSet;
import org.safris.xml.generator.compiler.processor.plan.element.EnumerationPlan;

public interface EnumerablePlan
{
	public boolean hasEnumerations();
	public boolean hasSuperEnumerations();
	public LinkedHashSet<EnumerationPlan> getEnumerations();
}
