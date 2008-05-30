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

package org.safris.xml.toolkit.test.binding.regression;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

// FIXME: Finish this!
// FIXME: Relocate this!
public class SOAP
{
    public static void main(String[] args) throws Exception
	{
		if(args.length  < 2)
		{
			System.err.println("Usage:  java SOAP http://soapURL soapEnvelopefile.xml [SOAPAction]");
			System.err.println("SOAPAction is optional.");
			System.exit(1);
		}

		String soapURL = args[0];
		String xmlFile2Send = args[1];

		String soapAction = "";
		if(args.length  > 2)
			soapAction = args[2];

		// Create the connection where we're going to send the file.
		URL url = new URL(soapURL);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection)connection;

		// Open the input file. After we copy it to a byte array, we can see
		// how big it is so that we can set the HTTP Cotent-Length
		// property. (See complete e-mail below for more on this.)

		FileInputStream fileInputStream = new FileInputStream(xmlFile2Send);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		// Copy the SOAP file to the open connection.
		copy(fileInputStream, byteArrayOutputStream);
		fileInputStream.close();

		byte[] bytes = byteArrayOutputStream.toByteArray();

		// Set the appropriate HTTP parameters.
		httpURLConnection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
		httpURLConnection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		httpURLConnection.setRequestProperty("SOAPAction", soapAction);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setDoInput(true);

		// Everything's set up; send the XML that was read in to b.
		OutputStream outputStream = httpURLConnection.getOutputStream();
		outputStream.write(bytes);
		outputStream.close();

		// Read the response and write it to standard out.
		InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String inputLine = null;
		while((inputLine = bufferedReader.readLine()) != null)
			System.out.println(inputLine);

		bufferedReader.close();
    }

	// copy method from From E.R. Harold's book "Java $/O"
	public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException
	{
		synchronized(inputStream)
		{
			synchronized(outputStream)
			{
				byte[] buffer = new byte[256];
				while(true)
				{
					int bytesRead = inputStream.read(buffer);
					if(bytesRead == -1)
						break;

					outputStream.write(buffer, 0, bytesRead);
				}
			}
		}
	}
}
