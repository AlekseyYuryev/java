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

/*
 * $Id: DeleteCommand.java,v 1.1 2004/02/16 15:19:03 nwalsh Exp $
 */
package com.nwalsh.jpegrdf.commands;

import com.nwalsh.jpegrdf.SystemKit;


public class DeleteCommand extends QNameBasedCommand {

	public DeleteCommand(String qname){
		super(qname);
	}
	
	public void execute() {
		SystemKit.debug("DeleteCommand.execute()");
		getProcessor().doDelete(getQName());
	}

}
