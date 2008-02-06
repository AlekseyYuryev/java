package org.safris.commons.util;

import java.util.ArrayList;
import java.util.List;

public class HashTree<T>
{
	private List<Node<T>> children = null;

	public void setChildren(List<Node<T>> children)
	{
		this.children = children;
	}

	public List<Node<T>> getChildren()
	{
		return children;
	}

	public boolean hasChildren()
	{
		return children != null;
	}

	public void addChild(Node<T> node)
	{
		if(children == null)
			children = new ArrayList<Node<T>>();

		children.add(node);
	}

	public Node<T> getChild(int index)
	{
		return children.get(index);
	}

	public static class Node<T>
	{
		private final T value;
		private List<Node<T>> children = null;

		public Node(T value)
		{
			this.value = value;
		}

		public boolean hasChildren()
		{
			return children != null;
		}

		public List<Node<T>> getChildren()
		{
			return children;
		}

		public T getValue()
		{
			return value;
		}

		public void addChild(Node<T> node)
		{
			if(children == null)
				children = new ArrayList<Node<T>>();

			children.add(node);
		}

		public Node<T> getChild(int index)
		{
			return children.get(index);
		}
	}
}
