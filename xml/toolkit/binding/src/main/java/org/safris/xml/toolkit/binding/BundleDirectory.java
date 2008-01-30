package org.safris.xml.toolkit.binding;

import org.safris.xml.generator.lexer.phase.composite.SchemaComposite;
import org.safris.xml.generator.module.phase.ElementModule;
import org.safris.xml.generator.module.phase.HandlerDirectory;
import org.safris.xml.generator.module.phase.Phase;
import org.safris.xml.toolkit.binding.Bundle;

public class BundleDirectory implements HandlerDirectory<SchemaComposite,Bundle>
{
	private BundlePhase phase = null;

	public BundleDirectory()
	{
		phase = new BundlePhase();
	}

	public ElementModule<Bundle> lookup(SchemaComposite key, Bundle parent)
	{
		return phase;
	}

	public Phase<SchemaComposite, Bundle> getPhase()
	{
		return phase;
	}


	public void clear()
	{
		phase = null;
	}
}
