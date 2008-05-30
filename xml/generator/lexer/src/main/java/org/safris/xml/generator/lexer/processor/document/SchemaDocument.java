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

package org.safris.xml.generator.lexer.processor.document;

import java.net.URL;
import java.util.Collection;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.xml.generator.lexer.processor.reference.SchemaReference;
import org.w3c.dom.Document;

public class SchemaDocument implements PipelineEntity
{
	private final SchemaReference schemaReference;
	private final Document document;
	private Collection<URL> includes;

	public SchemaDocument(SchemaReference schemaReference, Document document)
	{
		this.schemaReference = schemaReference;
		this.document = document;
	}

	public SchemaReference getSchemaReference()
	{
		return schemaReference;
	}

	public Document getDocument()
	{
		return document;
	}

	public Collection<URL> getIncludes()
	{
		return includes;
	}

	public void setIncludes(Collection<URL> includes)
	{
		this.includes = includes;
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof SchemaDocument))
			return false;

		final SchemaDocument that = (SchemaDocument)obj;
		return schemaReference.equals(that.schemaReference);
	}

	public int hashCode()
	{
		return (schemaReference != null ? schemaReference.hashCode() : -1) + (document != null ? 1 : -1);
	}
}
