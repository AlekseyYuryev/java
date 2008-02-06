package org.safris.xml.generator.compiler.runtime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLDecoder;
import java.security.SecureClassLoader;
import org.safris.commons.io.Files;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.WeakClassLoader;

public class WeakClassLoader extends SecureClassLoader
{
	private final java.lang.ClassLoader parent;
	
	public WeakClassLoader()
	{
		super();
		this.parent = null;
	}
	
	public WeakClassLoader(java.lang.ClassLoader parent)
	{
		super(parent);
		this.parent = parent;
	}
	
	public Class loadClass(String name) throws ClassNotFoundException
	{
		return loadClass(name, false);
 	}
	
	public synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException
	{
		WeakReference<Class<?>> ref = new WeakReference<Class<?>>(findClass(name));
		Class<?> cls = ref.get();
		if(resolve)
			resolveClass(cls);
		
		return cls;
    }
	
	protected Class<?> findClass(String name) throws ClassNotFoundException
	{
		if(Binding.class.getName().equals(name))
		{
			if(parent != null)
				return parent.loadClass(name);
			else
				return WeakClassLoader.getSystemClassLoader().loadClass(name);
		}
		
		String fileName = name;
		fileName = fileName.replace('.', '/');
		if(!fileName.startsWith("/"))
			fileName = '/' + fileName;
		
		fileName += ".class";
		URL url = WeakClassLoader.class.getResource("/");
		String decodedUrl = null;
		try
		{
			decodedUrl = URLDecoder.decode(url.getFile(), "UTF-8");
		}
		catch(UnsupportedEncodingException e)
		{
			System.err.println("ClassLoader: findClass(" + name + ")\n" + e.getMessage());
		}
		
		Class<?> bindingClass = null;
		try
		{
			byte[] bytes = Files.getBytes(new File(decodedUrl + fileName));
			bindingClass = defineClass(name, bytes, 0, bytes.length);
		}
		catch(FileNotFoundException e)
		{
			if(parent != null)
				return parent.loadClass(name);
			else
				return WeakClassLoader.getSystemClassLoader().loadClass(name);
		}
		catch(IOException e)
		{
			System.err.println("ClassLoader: findClass(" + name + ")\n" + e.getMessage());
		}
		
		return bindingClass;
	}
}
