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
 * $Id: MacroCommand.java,v 1.1 2004/02/16 15:19:04 nwalsh Exp $
 */
package com.nwalsh.jpegrdf.commands;

import java.util.ArrayList;
import java.util.List;

import com.nwalsh.jpegrdf.SystemKit;

public class MacroCommand implements Command {

	private List commandList;
private Object receiver;

	public MacroCommand() {
		commandList = new ArrayList();
	}

	public void add(Command command) {
		commandList.add(command);
	}

	public void removeCommand(Command command) {
		commandList.remove(command);
	}
	
	public int size(){
		return commandList.size();
	}

	public void execute() {
		Command command;
		for (int i = 0; i < commandList.size(); i++) {
			
			command = (Command) commandList.get(i);
			command.setReceiver(receiver);
			SystemKit.debug("Macro calling "+command.getClass());
			command.execute();
		
		}
	}

	public void setReceiver(Object receiver) {
		this.receiver = receiver;
	}
}
