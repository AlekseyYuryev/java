package org.safris.xml.generator.lexer.phase.model.element;

import java.lang.ref.Reference;
import java.util.HashMap;
import java.util.Map;
import org.safris.commons.util.xml.BindingQName;
import org.safris.xml.generator.lexer.phase.Referenceable;
import org.safris.xml.generator.lexer.phase.model.AliasModel;
import org.safris.xml.generator.lexer.phase.model.DocumentableModel;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.module.phase.StaticReferenceManager;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class NotationModel extends AliasModel implements DocumentableModel
{
	private String _public = null;
	private String system = null;

	protected NotationModel(Node node, Model parent)
	{
		super(node, parent);
		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("public".equals(attribute.getLocalName()))
				_public = attribute.getNodeValue();
			else if("system".equals(attribute.getLocalName()))
				system = attribute.getNodeValue();
		}
	}

	public final String getSystem()
	{
		return system;
	}

	public final String getPublic()
	{
		return _public;
	}

	public static class Reference extends NotationModel implements Referenceable
	{
		private static final Map<BindingQName,Reference> all = StaticReferenceManager.manageMap(new HashMap<BindingQName,Reference>());

		protected Reference(Model parent)
		{
			super(null, parent);
		}

		public static Reference parseGroup(BindingQName name)
		{
			Reference type = all.get(name);
			if(type != null)
				return type;

			type = new Reference(null);
			type.setName(name);
			Reference.all.put(name, type);
			return type;
		}
	}
}
