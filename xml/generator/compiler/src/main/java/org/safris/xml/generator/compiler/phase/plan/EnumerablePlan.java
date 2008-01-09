package org.safris.xml.generator.compiler.phase.plan;

import java.util.LinkedHashSet;
import org.safris.xml.generator.compiler.phase.plan.element.EnumerationPlan;

public interface EnumerablePlan
{
	public boolean hasEnumerations();
	public boolean hasSuperEnumerations();
	public LinkedHashSet<EnumerationPlan> getEnumerations();
}
