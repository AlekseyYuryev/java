/*  Copyright Safris Software 2010
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

package org.safris.commons.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import javax.servlet.http.HttpServletResponse;

public final class Downloads {
  /**
   * Send the given file as a byte array to the servlet response. If attachment is set to true,
   * then show a "Save as" dialogue, else show the file inline in the browser or let the
   * operating system open it in the right application.
   * @param response The HttpServletResponse to be used.
   * @param bytes The file contents in a byte array.
   * @param fileName The file name.
   * @param attachment Download as attachment?
   */
  public static void downloadFile(HttpServletResponse response, byte[] bytes, String fileName, boolean attachment) throws IOException {
    // Wrap the byte array in a ByteArrayInputStream and pass it through another method.
    downloadFile(response, new ByteArrayInputStream(bytes), fileName, attachment);
  }

  /**
   * Send the given file as a File object to the servlet response. If attachment is set to true,
   * then show a "Save as" dialogue, else show the file inline in the browser or let the
   * operating system open it in the right application.
   * @param response The HttpServletResponse to be used.
   * @param file The file as a File object.
   * @param attachment Download as attachment?
   */
  public static void downloadFile(HttpServletResponse response, File file, boolean attachment) throws IOException {
    // Prepare stream.
    BufferedInputStream input = null;
    try {
      // Wrap the file in a BufferedInputStream and pass it through another method.
      input = new BufferedInputStream(new FileInputStream(file));
      downloadFile(response, input, file.getName(), attachment);
    }
    finally {
      // Gently close stream.
      if (input != null) {
        try {
          input.close();
        }
        catch (IOException e) {
          final String message = "Closing file " + file.getPath() + " failed.";
          // Do your thing with the exception and the message. Print it, log it or mail it.
          System.err.println(message);
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Send the given file as an InputStream to the servlet response. If attachment is set to true,
   * then show a "Save as" dialogue, else show the file inline in the browser or let the
   * operating system open it in the right application.
   * @param response The HttpServletResponse to be used.
   * @param input The file contents in an InputStream.
   * @param fileName The file name.
   * @param attachment Download as attachment?
   */
  public static void downloadFile(HttpServletResponse response, InputStream input, String fileName, boolean attachment) throws IOException {
    // Prepare stream.
    BufferedOutputStream output = null;

    try {
      // Prepare.
      final String disposition = attachment ? "attachment" : "inline";
      String contentType = URLConnection.guessContentTypeFromName(fileName);
      int contentLength = input.available();

      // If content type is unknown, then set the default value.
      // For all content types, see: http://www.w3schools.com/media/media_mimeref.asp
      if (contentType == null)
        contentType = "application/octet-stream";

      // Init servlet response.
      response.reset();
      response.setContentLength(contentLength);
      response.setContentType(contentType);
      response.setHeader("Content-disposition", disposition + "; filename=\"" + fileName + "\"");
      output = new BufferedOutputStream(response.getOutputStream());

      // Write file contents to response.
      while (contentLength-- > 0)
        output.write(input.read());

      // Finalize task.
      output.flush();
    }
    finally {
      // Gently close stream.
      if (output != null) {
        try {
          output.close();
        }
        catch (IOException e) {
          final String message = "Closing HttpServletResponse#getOutputStream() failed.";
          // Do your thing with the exception and the message. Print it, log it or mail it.
          System.err.println(message);
          e.printStackTrace();
        }
      }
    }
  }

  private Downloads() {
  }
}
