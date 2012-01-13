/*  Copyright Safris Software 2006
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.safris.commons.util;

import java.util.ArrayList;
import java.util.List;

public class HashTree<T> {
  private List<Node<T>> children = null;

  public void setChildren(List<Node<T>> children) {
    this.children = children;
  }

  public List<Node<T>> getChildren() {
    return children;
  }

  public boolean hasChildren() {
    return children != null;
  }

  public void addChild(Node<T> node) {
    if (children == null)
      children = new ArrayList<Node<T>>();

    children.add(node);
  }

  public Node<T> getChild(int index) {
    return children.get(index);
  }

  public static class Node<T> {
    private final T value;
    private List<Node<T>> children = null;

    public Node(T value) {
      this.value = value;
    }

    public boolean hasChildren() {
      return children != null;
    }

    public List<Node<T>> getChildren() {
      return children;
    }

    public T getValue() {
      return value;
    }

    public void addChild(Node<T> node) {
      if (children == null)
        children = new ArrayList<Node<T>>();

      children.add(node);
    }

    public Node<T> getChild(int index) {
      return children.get(index);
    }
  }
}
