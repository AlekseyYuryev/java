package org.safris.web.depiction.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Table extends TagSupport
{
	private String rowClass = null;
	private String columnClass = null;
	private String tableClass = null;
	private int columns = 1;
	private int column = 0;
	private int rows = 1;
	private int row = 0;
	private int cell = 0;
	private String var = null;
	
	public void setRowClass(Object rowClass)
	{
		if(rowClass == null)
		{
			this.rowClass = null;
			return;
		}
		
		this.rowClass = rowClass.toString();
	}
	
	public void setColumnClass(Object columnClass)
	{
		if(columnClass == null)
		{
			this.columnClass = null;
			return;
		}
		
		this.columnClass = columnClass.toString();
	}
	
	public void setTableClass(Object tableClass)
	{
		if(tableClass == null)
		{
			this.tableClass = null;
			return;
		}
		
		this.tableClass = tableClass.toString();
	}
	
	public void setColumns(Object columns)
	{
		if(columns == null)
		{
			this.columns = 1;
			return;
		}
		
		try
		{
			this.columns = Integer.parseInt(columns.toString());
		}
		catch(NumberFormatException e)
		{
			this.columns = 1;
		}
	}
	
	public void setRows(Object rows)
	{
		if(rows == null)
		{
			this.rows = 1;
			return;
		}
		
		try
		{
			this.rows = Integer.parseInt(rows.toString());
		}
		catch(NumberFormatException e)
		{
			this.rows = 1;
		}
	}
	
	public void setVar(Object var)
	{
		if(var == null)
		{
			this.var = null;
			return;
		}
		
		this.var = var.toString();
	}
	
	private void appendTable(StringBuffer stringBuffer)
	{
		stringBuffer.append("<table");
		if(tableClass != null)
			stringBuffer.append(" class=\"" + tableClass + "\"");
		
		stringBuffer.append(">");
	}
	
	private void appendTr(StringBuffer stringBuffer)
	{
		stringBuffer.append("<tr");
		if(rowClass != null)
			stringBuffer.append(" class=\"" + rowClass + "\"");
		
		stringBuffer.append(">");
	}
	
	private void appendTd(StringBuffer stringBuffer)
	{
		stringBuffer.append("<td");
		if(columnClass != null)
			stringBuffer.append(" class=\"" + columnClass + "\"");
		
		stringBuffer.append(">");
	}
	
	public int doStartTag() throws JspException
	{
		if(columns == 0 || rows == 0)
			return SKIP_BODY;
		
		column = 0;
		row = 0;
		cell = 0;
		StringBuffer buffer = new StringBuffer();
		appendTable(buffer);
		appendTr(buffer);
		appendTd(buffer);
		JspWriter out = pageContext.getOut();
		try
		{
			out.write(buffer.toString(), 0, buffer.length());
		}
		catch(IOException e)
		{
			throw new JspException(e);
		}
		
		pageContext.setAttribute(var, cell);
		return EVAL_BODY_INCLUDE;
	}
	
	public int doAfterBody() throws JspException
	{
		cell++;
		StringBuffer buffer = new StringBuffer();
		if(++column == columns)
		{
			column = 0;
			if(row++ == rows - 1)
				return SKIP_BODY;
			
			buffer.append("</td></tr>");
			appendTr(buffer);
			appendTd(buffer);
		}
		else
		{
			buffer.append("</td>");
			appendTd(buffer);
		}
		
		JspWriter out = pageContext.getOut();
		try
		{
			out.write(buffer.toString(), 0, buffer.length());
		}
		catch(IOException e)
		{
			throw new JspException(e);
		}
		
		pageContext.setAttribute("column", column);
		pageContext.setAttribute("row", row);
		pageContext.setAttribute(var, cell);
		return EVAL_BODY_AGAIN;
	}
	
	public int doEndTag() throws JspException
	{
		if(columns == 0 || rows == 0)
			return SKIP_BODY;
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("</td></tr></table>");
		
		JspWriter out = pageContext.getOut();
		try
		{
			out.write(buffer.toString(), 0, buffer.length());
		}
		catch(IOException e)
		{
			throw new JspException(e);
		}
		return EVAL_BODY_INCLUDE;
	}
}
