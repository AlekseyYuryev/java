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


