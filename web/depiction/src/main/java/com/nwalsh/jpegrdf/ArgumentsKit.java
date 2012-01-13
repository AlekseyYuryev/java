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
 * $Id: ArgumentsKit.java,v 1.1 2004/02/16 15:19:03 nwalsh Exp $
 */
package com.nwalsh.jpegrdf;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;



/**
 * The Kit
 */
public class ArgumentsKit {

  private ArgumentTaker target;
  private Map commandsMap;
  boolean prepareMethods = false;

  public ArgumentsKit(ArgumentTaker targetClass) {
    this.target = targetClass;
    commandsMap = new HashMap();
  }

  public void put(String key, String value) {
    commandsMap.put(key, value);
  }

  public String get(String key) {
    Object value = commandsMap.get(key);
    if (value == null) {
      return null;
    }
    return (String) value;
  }

  public boolean invoke(String key) {
    Method method = getMethod(key);
    if (method == null) {
      return false;
    }
    Object[] arguments = null;
    if (method.getParameterTypes().length == 1) {
      // load value from command line args
      arguments =
        new Object[] { target.nextArgument()};
    }
    if (method.getParameterTypes().length == 2) {
      // load value from command line args
      arguments =
        new Object[] {
          target.nextArgument(),
          target.nextArgument()};
    }
    return invoke(method, arguments);
  }

  private boolean invoke(
    Method method,
    Object[] arguments) {
    try {
      method.invoke(target, arguments);
    } catch (Exception exception) {
      System.err.println(
        "Couln't invoke method "
          + method.getName());
      exception.printStackTrace();
    }
    return true; // argument known
  }

  private Method getMethod(String key) {
    Object value = commandsMap.get(key);
    if (value == null) { // argument unknown
      return null;
    }
    String methodName = (String) value;

    Class targetClass = target.getClass();
    Class[] parameterTypes = null;
    Method method = null;

    try { // no-parameter method
      method =
        targetClass.getMethod(
          methodName,
          parameterTypes);
    } catch (NoSuchMethodException notOneException) {

      parameterTypes = new Class[] { String.class };
      try { // one string-parameter method
        method =
          targetClass.getMethod(
            methodName,
            parameterTypes);
      } catch (NoSuchMethodException notTwoException) {
        parameterTypes =
                					new Class[] {
                						String.class,
                						String.class };
        try { // two string-parameters method
          method =
                		targetClass.getMethod(
                			methodName,
                			parameterTypes);
        
                
        } catch (NoSuchMethodException giveUpException) {
          System.err.println(
            "No suitable method found "
              + methodName);
          giveUpException.printStackTrace();
        }

      }
  
    }
    return method;
  }
}


