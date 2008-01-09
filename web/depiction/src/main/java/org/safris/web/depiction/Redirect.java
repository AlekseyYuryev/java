package org.safris.web.depiction;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

public class Redirect
{
	public static void postRedirect(HttpServletResponse response, String location, Map postParameters, Map getParameters)
	{
		response.resetBuffer();

		if(getParameters != null)
		{
			StringBuffer stringBuffer = new StringBuffer(location);
			stringBuffer.append("?");
			Set entries = getParameters.entrySet();
			Iterator iterator = entries.iterator();
			Map.Entry entry = null;
			while(iterator.hasNext())
			{
				entry = (Map.Entry)iterator.next();
				if(((String)entry.getValue()).length() > 0)
					stringBuffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}

			location = stringBuffer.deleteCharAt(stringBuffer.length() - 1).toString();
		}

		StringBuffer stringBuffer = new StringBuffer("<html><body onLoad='document.forms[0].submit()'><form action='");
		stringBuffer.append(location);
		stringBuffer.append("' method='POST'>");
		if(postParameters != null && postParameters.size() != 0)
		{
			Map.Entry[] entries = (Map.Entry[])postParameters.entrySet().toArray(new Map.Entry[postParameters.size()]);
			for(int i = 0; i < entries.length; i++)
			{
				stringBuffer.append("<input name='").append(entries[i].getKey()).append("' value=\"");
				String[] values = null;
				if(entries[i].getValue() instanceof String[])
				{
					values = (String[])entries[i].getValue();
					for(int j = 0; j < values.length - 1; j++)
					{
						if(values[j].length() != 0)
						{
							stringBuffer.append(values[j]);
							if(values[j + 1].length() != 0)
								stringBuffer.append("&");
						}
					}

					if(values[values.length - 1].length() != 0)
						stringBuffer.append(values[values.length - 1]);
				}
				else if(((String)entries[i].getValue()).length() > 0)
				{
					stringBuffer.append(entries[i].getValue());
				}

				stringBuffer.append("\" type='hidden'>");
			}
		}

		stringBuffer.append("</form></body></html>");
		try
		{
			response.setContentType("text/html");
			response.setBufferSize(stringBuffer.length());
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(stringBuffer.toString().getBytes());
			response.flushBuffer();
			outputStream.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
