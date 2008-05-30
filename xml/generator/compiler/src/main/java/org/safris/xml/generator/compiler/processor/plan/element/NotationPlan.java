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

package org.safris.xml.generator.compiler.processor.plan.element;

import org.safris.xml.generator.lexer.processor.model.element.NotationModel;
import org.safris.xml.generator.compiler.processor.plan.AliasPlan;
import org.safris.xml.generator.compiler.processor.plan.Plan;

public class NotationPlan extends AliasPlan<NotationModel>
{
	private final String _public;
	private final String system;

	public NotationPlan(NotationModel model, Plan parent)
	{
		super(model, parent);
		this._public = model.getPublic();
		this.system = model.getSystem();
	}

	public String getPublic()
	{
		return _public;
	}

	public String getSystem()
	{
		return system;
	}
}
