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
