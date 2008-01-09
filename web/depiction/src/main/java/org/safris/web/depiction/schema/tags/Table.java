package org.safris.web.depiction.schema.tags;

/**
 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
*/

public class Table extends org.safris.web.depiction.schema.xhtml.tags.ITaglib<org.safris.xml.bind.runtime.lang.ComplexType>
{
	private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "table");

	static
	{
		getElementBindings().put(NAME, org.safris.web.depiction.schema.tags.Table.class.getName());
	}

	private org.safris.web.depiction.schema.tags.Table.ColumnsAttr _columnsAttr = null;
	private org.safris.web.depiction.schema.tags.Table.RowsAttr _rowsAttr = null;
	private org.safris.web.depiction.schema.tags.Table.TableClassAttr _tableClassAttr = null;
	private org.safris.web.depiction.schema.tags.Table.RowClassAttr _rowClassAttr = null;
	private org.safris.web.depiction.schema.tags.Table.ColumnClassAttr _columnClassAttr = null;
	private org.safris.web.depiction.schema.tags.Table.VarAttr _varAttr = null;

	public Table(org.safris.web.depiction.schema.xhtml.tags.ITaglib<org.safris.xml.bind.runtime.lang.ComplexType> binding)
	{
		super(binding);
		if(binding instanceof org.safris.web.depiction.schema.tags.Table)
		{
			org.safris.web.depiction.schema.tags.Table element = (org.safris.web.depiction.schema.tags.Table)binding;
			this._columnsAttr = element._columnsAttr != null ? element._columnsAttr.clone() : null;
			this._rowsAttr = element._rowsAttr != null ? element._rowsAttr.clone() : null;
			this._tableClassAttr = element._tableClassAttr != null ? element._tableClassAttr.clone() : null;
			this._rowClassAttr = element._rowClassAttr != null ? element._rowClassAttr.clone() : null;
			this._columnClassAttr = element._columnClassAttr != null ? element._columnClassAttr.clone() : null;
			this._varAttr = element._varAttr != null ? element._varAttr.clone() : null;
		}
	}

	public Table()
	{
		super();
	}

	public void setColumnsAttr(org.safris.web.depiction.schema.tags.Table.ColumnsAttr _columnsAttr)
	{
		this._columnsAttr = _columnsAttr;
	}

	public org.safris.web.depiction.schema.tags.Table.ColumnsAttr getColumnsAttr()
	{
		return _columnsAttr;
	}

	public void setRowsAttr(org.safris.web.depiction.schema.tags.Table.RowsAttr _rowsAttr)
	{
		this._rowsAttr = _rowsAttr;
	}

	public org.safris.web.depiction.schema.tags.Table.RowsAttr getRowsAttr()
	{
		return _rowsAttr;
	}

	public void setTableClassAttr(org.safris.web.depiction.schema.tags.Table.TableClassAttr _tableClassAttr)
	{
		this._tableClassAttr = _tableClassAttr;
	}

	public org.safris.web.depiction.schema.tags.Table.TableClassAttr getTableClassAttr()
	{
		return _tableClassAttr;
	}

	public void setRowClassAttr(org.safris.web.depiction.schema.tags.Table.RowClassAttr _rowClassAttr)
	{
		this._rowClassAttr = _rowClassAttr;
	}

	public org.safris.web.depiction.schema.tags.Table.RowClassAttr getRowClassAttr()
	{
		return _rowClassAttr;
	}

	public void setColumnClassAttr(org.safris.web.depiction.schema.tags.Table.ColumnClassAttr _columnClassAttr)
	{
		this._columnClassAttr = _columnClassAttr;
	}

	public org.safris.web.depiction.schema.tags.Table.ColumnClassAttr getColumnClassAttr()
	{
		return _columnClassAttr;
	}

	public void setVarAttr(org.safris.web.depiction.schema.tags.Table.VarAttr _varAttr)
	{
		this._varAttr = _varAttr;
	}

	public org.safris.web.depiction.schema.tags.Table.VarAttr getVarAttr()
	{
		return _varAttr;
	}

	protected javax.xml.namespace.QName _getName()
	{
		return NAME;
	}

	public org.w3c.dom.Element marshal() throws org.safris.xml.bind.runtime.lang.MarshalException, org.safris.xml.bind.runtime.lang.ValidationException
	{
		org.w3c.dom.Element root = createElementNS(_getName().getNamespaceURI(), _getName().getLocalPart());
		org.w3c.dom.Element element = marshal(root, _getName(), _getTypeName());
		if(org.safris.xml.bind.runtime.util.Validator.getSystemValidator() != null)
			org.safris.xml.bind.runtime.util.Validator.getSystemValidator().validateMarshal(element);

		return element;
	}

	protected org.w3c.dom.Element marshal(org.w3c.dom.Element parent, javax.xml.namespace.QName name, javax.xml.namespace.QName typeName) throws org.safris.xml.bind.runtime.lang.MarshalException
	{
		org.w3c.dom.Element element = super.marshal(parent, name, typeName);
		if(_columnsAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_columnsAttr, "columns", element));
		}

		if(_rowsAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_rowsAttr, "rows", element));
		}

		if(_tableClassAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_tableClassAttr, "tableClass", element));
		}

		if(_rowClassAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_rowClassAttr, "rowClass", element));
		}

		if(_columnClassAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_columnClassAttr, "columnClass", element));
		}

		if(_varAttr != null)
		{
			element.setAttributeNodeNS(org.safris.xml.bind.runtime.lang.Binding.marshalAttr(_varAttr, "var", element));
		}

		return element;
	}

	protected void parse(org.w3c.dom.Element element) throws org.safris.xml.bind.runtime.lang.UnmarshalException, org.safris.xml.bind.runtime.lang.ValidationException
	{
		org.w3c.dom.NamedNodeMap namedNodeMap = element.getAttributes();
		org.w3c.dom.Node attribute = null;
		for(int i = 0; i < namedNodeMap.getLength(); i++)
		{
			attribute = namedNodeMap.item(i);
			if(attribute == null || XMLNS.getLocalPart().equals(attribute.getPrefix()))
			{
				continue;
			}
			else if("columns".equals(attribute.getLocalName()))
			{
				this._columnsAttr = (org.safris.web.depiction.schema.tags.Table.ColumnsAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.Table.ColumnsAttr.class, element, attribute);
			}
			else if("rows".equals(attribute.getLocalName()))
			{
				this._rowsAttr = (org.safris.web.depiction.schema.tags.Table.RowsAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.Table.RowsAttr.class, element, attribute);
			}
			else if("tableClass".equals(attribute.getLocalName()))
			{
				this._tableClassAttr = (org.safris.web.depiction.schema.tags.Table.TableClassAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.Table.TableClassAttr.class, element, attribute);
			}
			else if("rowClass".equals(attribute.getLocalName()))
			{
				this._rowClassAttr = (org.safris.web.depiction.schema.tags.Table.RowClassAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.Table.RowClassAttr.class, element, attribute);
			}
			else if("columnClass".equals(attribute.getLocalName()))
			{
				this._columnClassAttr = (org.safris.web.depiction.schema.tags.Table.ColumnClassAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.Table.ColumnClassAttr.class, element, attribute);
			}
			else if("var".equals(attribute.getLocalName()))
			{
				this._varAttr = (org.safris.web.depiction.schema.tags.Table.VarAttr)org.safris.xml.bind.runtime.lang.Binding._parseAttr(org.safris.web.depiction.schema.tags.Table.VarAttr.class, element, attribute);
			}
		}

		super.parse(element);
	}

	public org.safris.web.depiction.schema.tags.Table clone()
	{
		return new org.safris.web.depiction.schema.tags.Table(this);
	}

	public boolean equals(java.lang.Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof org.safris.web.depiction.schema.tags.Table))
			return false;

		org.safris.web.depiction.schema.tags.Table binding = (org.safris.web.depiction.schema.tags.Table)obj;
		if((this._columnsAttr == null && binding._columnsAttr != null) || (this._columnsAttr != null && !this._columnsAttr.equals(binding._columnsAttr)))
			return false;

		if((this._rowsAttr == null && binding._rowsAttr != null) || (this._rowsAttr != null && !this._rowsAttr.equals(binding._rowsAttr)))
			return false;

		if((this._tableClassAttr == null && binding._tableClassAttr != null) || (this._tableClassAttr != null && !this._tableClassAttr.equals(binding._tableClassAttr)))
			return false;

		if((this._rowClassAttr == null && binding._rowClassAttr != null) || (this._rowClassAttr != null && !this._rowClassAttr.equals(binding._rowClassAttr)))
			return false;

		if((this._columnClassAttr == null && binding._columnClassAttr != null) || (this._columnClassAttr != null && !this._columnClassAttr.equals(binding._columnClassAttr)))
			return false;

		if((this._varAttr == null && binding._varAttr != null) || (this._varAttr != null && !this._varAttr.equals(binding._varAttr)))
			return false;

		return super.equals(obj);
	}

	public int hashCode()
	{
		java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
		stringBuffer.append(_columnsAttr != null ? _columnsAttr.hashCode() : 0).append("-");
		stringBuffer.append(_rowsAttr != null ? _rowsAttr.hashCode() : 0).append("-");
		stringBuffer.append(_tableClassAttr != null ? _tableClassAttr.hashCode() : 0).append("-");
		stringBuffer.append(_rowClassAttr != null ? _rowClassAttr.hashCode() : 0).append("-");
		stringBuffer.append(_columnClassAttr != null ? _columnClassAttr.hashCode() : 0).append("-");
		stringBuffer.append(_varAttr != null ? _varAttr.hashCode() : 0).append("-");
		return stringBuffer.toString().hashCode();
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class ColumnsAttr extends org.safris.xml.bind.runtime.types.PositiveIntegerType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "columns");

		public ColumnsAttr(org.safris.xml.bind.runtime.types.PositiveIntegerType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public ColumnsAttr(java.lang.Integer value)
		{
			super(value);
		}

		protected ColumnsAttr(java.lang.String value)
		{
			super(value);
		}

		protected ColumnsAttr()
		{
			super();
		}

		public java.lang.Integer getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.Integer value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public org.safris.web.depiction.schema.tags.Table.ColumnsAttr clone()
		{
			return new org.safris.web.depiction.schema.tags.Table.ColumnsAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class RowsAttr extends org.safris.xml.bind.runtime.types.PositiveIntegerType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "rows");

		public RowsAttr(org.safris.xml.bind.runtime.types.PositiveIntegerType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public RowsAttr(java.lang.Integer value)
		{
			super(value);
		}

		protected RowsAttr(java.lang.String value)
		{
			super(value);
		}

		protected RowsAttr()
		{
			super();
		}

		public java.lang.Integer getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.Integer value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public org.safris.web.depiction.schema.tags.Table.RowsAttr clone()
		{
			return new org.safris.web.depiction.schema.tags.Table.RowsAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class TableClassAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "tableClass");

		public TableClassAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public TableClassAttr(java.lang.String value)
		{
			super(value);
		}

		protected TableClassAttr()
		{
			super();
		}

		public java.lang.String getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.String value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public org.safris.web.depiction.schema.tags.Table.TableClassAttr clone()
		{
			return new org.safris.web.depiction.schema.tags.Table.TableClassAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class RowClassAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "rowClass");

		public RowClassAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public RowClassAttr(java.lang.String value)
		{
			super(value);
		}

		protected RowClassAttr()
		{
			super();
		}

		public java.lang.String getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.String value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public org.safris.web.depiction.schema.tags.Table.RowClassAttr clone()
		{
			return new org.safris.web.depiction.schema.tags.Table.RowClassAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class ColumnClassAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "columnClass");

		public ColumnClassAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public ColumnClassAttr(java.lang.String value)
		{
			super(value);
		}

		protected ColumnClassAttr()
		{
			super();
		}

		public java.lang.String getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.String value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public org.safris.web.depiction.schema.tags.Table.ColumnClassAttr clone()
		{
			return new org.safris.web.depiction.schema.tags.Table.ColumnClassAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}

	/**
	 * @author Source generated with: <u>xml Bind for Java</u> by <b>Seva Safris &lt;seva@safris.org&gt;</b>
	*/

	public static class VarAttr extends org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute>
	{
		private static final javax.xml.namespace.QName NAME = new javax.xml.namespace.QName("http://web.safris.org/depiction/tags.xsd", "var");

		public VarAttr(org.safris.xml.bind.runtime.types.StringType<org.safris.xml.bind.runtime.lang.Attribute> binding)
		{
			super(binding);
		}

		public VarAttr(java.lang.String value)
		{
			super(value);
		}

		protected VarAttr()
		{
			super();
		}

		public java.lang.String getValue()
		{
			return super.getTEXT();
		}

		public void setValue(java.lang.String value)
		{
			super.setTEXT(value);
		}

		protected javax.xml.namespace.QName _getName()
		{
			return NAME;
		}

		protected org.w3c.dom.Attr marshalAttr(java.lang.String name, org.w3c.dom.Element parent) throws org.safris.xml.bind.runtime.lang.MarshalException
		{
			return super.marshalAttr(name, parent);
		}

		public org.safris.web.depiction.schema.tags.Table.VarAttr clone()
		{
			return new org.safris.web.depiction.schema.tags.Table.VarAttr(this);
		}

		public boolean equals(java.lang.Object obj)
		{
			return super.equals(obj);
		}

		public int hashCode()
		{
			java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(java.lang.String.valueOf(super.hashCode())).append("-");
			return stringBuffer.toString().hashCode();
		}
	}
}