package org.safris.xml.generator.compiler.runtime;

import java.util.ArrayList;
import java.util.List;

final class CompositeElementStore
{
	private final List<Binding> elements;
	private final List<ElementAudit<Binding>> elementAudits;

	protected CompositeElementStore(int initialCapacity)
	{
		this.elements = new GeneralElementList<Binding>(this, initialCapacity);
		this.elementAudits = new ArrayList<ElementAudit<Binding>>(initialCapacity);
    }

    protected CompositeElementStore()
	{
		this.elements = new GeneralElementList<Binding>(this);
		this.elementAudits = new ArrayList<ElementAudit<Binding>>();
    }

	protected int size()
	{
		return elements.size();
	}

	protected List<Binding> getElements()
	{
		return elements;
	}

	protected Binding getElement(int index)
	{
		return elements.get(index);
	}

	protected ElementAudit<Binding> getElementAudits(int index)
	{
		return elementAudits.get(index);
	}

	protected boolean add(Binding element, ElementAudit<Binding> elementAudit, boolean addToAudit)
	{
		synchronized(elements)
		{
			if(!elements.add(element))
				throw new RuntimeBindingException("Addition of element should have modified the elements list!");

			if(!elementAudits.add(elementAudit))
				throw new RuntimeBindingException("Addition of element should have modified the elementAudits list!");

			if(addToAudit && !elementAudit.addElement(element))
				throw new RuntimeBindingException("Addition of element should have modified the elementAudit list!");
		}

		return true;
	}

	protected void addBefore(Binding before, Binding element, ElementAudit<Binding> elementAudit)
	{
		synchronized(elements)
		{
			final int index = elements.indexOf(before);
			elements.add(index, element);
			elementAudits.add(index, elementAudit);
		}
	}

	protected void replace(Binding original, Binding element, ElementAudit<Binding> elementAudit)
	{
		synchronized(elements)
		{
			final int index = elements.indexOf(original);
			elements.set(index, element);
			elementAudits.set(index, elementAudit);
		}
	}

	protected boolean remove(Binding element)
	{
		synchronized(elements)
		{
			final int index = elements.indexOf(element);
			if(index < 0)
				return false;

			if(elements.remove(index) != element)
				throw new RuntimeBindingException("Element identities do not match. Report this please.");

			// NOTE: The remove() method is initiated from the value list, which
			// NOTE: means that it is responsible for removing its own element
			// NOTE: and it is not the responsibility of this method to remove
			// NOTE: that element from the value list of the ElementAudit.
			elementAudits.remove(index);
		}

		return true;
	}

	protected boolean remove(int index, Binding element)
	{
		synchronized(elements)
		{
			final ElementAudit elementAudit = elementAudits.remove(index);
			if(elementAudit != null)
				return ((SpecificElementList)elementAudit.getElements()).remove(element, false);
		}

		return false;
	}

	protected void clear()
	{
		synchronized(elements)
		{
			elements.clear();
			elementAudits.clear();
		}
	}

	public boolean equals(Object obj)
	{
		if(obj == this)
			return true;

		if(!(obj instanceof CompositeElementStore))
			return false;

		final CompositeElementStore that = (CompositeElementStore)obj;
		return elements.equals(that.elements) && elementAudits.equals(that.elementAudits);
	}

	public int hashCode()
	{
		return elements.hashCode();
	}
}
