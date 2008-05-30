/*  Copyright 2008 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
