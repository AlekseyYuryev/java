package org.safris.xml.generator.compiler.processor.plan;

import java.util.LinkedHashSet;
import org.safris.xml.generator.compiler.processor.plan.element.AttributePlan;

public interface AttributablePlan
{
	public LinkedHashSet<AttributePlan> getAttributes();
}
