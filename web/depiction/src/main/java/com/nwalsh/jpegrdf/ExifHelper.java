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
 * $Id: ExifHelper.java,v 1.3 2004/05/27 13:41:38 nwalsh Exp $
 */
package com.nwalsh.jpegrdf;

import java.util.Enumeration;
import java.util.Hashtable;

import org.w3c.tools.jpeg.Exif;
import org.w3c.tools.jpeg.TagDecoder;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class ExifHelper {
  public static final String nsExif =
    "http://nwalsh.com/rdf/exif#";
  public static final String nsExifI =
    "http://nwalsh.com/rdf/exif-intrinsic#";
  public static final String nsExifGps =
    "http://nwalsh.com/rdf/exif-gps#";

  // EXIF namespace prefixes
  private String exifPrefix = null;
  private String exifiPrefix = null;

  // Intrinsic property numbers
  private static final int iHeight = 1;
  private static final int iWidth = 2;
  private static final int iComp = 3;
  private static final int iBPP = 4;
  private static final int iNumCC = 5;

  // Intrinsic property names
  private String sHeight = null;
  private String sWidth = null;
  private String sComp = null;
  private String sBPP = null;
  private String sNumCC = null;

  private Exif exif = new Exif();

  public boolean doExif(Model model, Resource subject) {
    Hashtable exifHash = exif.getTags();
    boolean xfer = false;

    if (exifHash != null) {
      transferExif(exifHash, model, subject);
      xfer = true;
    }

    exifHash = exif.getGpsTags();
    if (exifHash != null) {
      // Hack hack hack!
      // Latitude/longitude are more useful if the references are expressed in +/-
      String prefix = RDFKit.getNamespacePrefix(ExifHelper.nsExifGps);
      String ref = "";
      String value = "";

      if (exifHash.containsKey(prefix + ":latitudeRef")
    && exifHash.containsKey(prefix + ":latitude")) {
  ref = (String) exifHash.get(prefix + ":latitudeRef");
  if ("N".equals(ref)) {
    exifHash.remove(prefix + ":latitudeRef");
  } else if ("S".equals(ref)) {
    exifHash.remove(prefix + ":latitudeRef");
    value = (String) exifHash.get(prefix + ":latitude");
    exifHash.put(prefix + ":latitude", "-" + value);
  } else {
    SystemKit.message(1, "Unexpected latitute reference: " + ref);
  }
      }

      if (exifHash.containsKey(prefix + ":longitudeRef")
    && exifHash.containsKey(prefix + ":longitude")) {
  ref = (String) exifHash.get(prefix + ":longitudeRef");
  if ("E".equals(ref)) {
    exifHash.remove(prefix + ":longitudeRef");
  } else if ("W".equals(ref)) {
    exifHash.remove(prefix + ":longitudeRef");
    value = (String) exifHash.get(prefix + ":longitude");
    exifHash.put(prefix + ":longitude", "-" + value);
  } else {
    SystemKit.message(1, "Unexpected latitute reference: " + ref);
  }
      }

      transferExif(exifHash, model, subject);
      xfer = true;
    }

    return xfer;
  }

  public boolean containsExif(
    Model model,
    Resource subject) {
    return RDFKit.findProperty(
      model,
      subject,
      exifPrefix + ":");
  }

  public void transferExif(Hashtable exifTags,
         Model model,
         Resource subject) {

    for (Enumeration keys = exifTags.keys();
   keys.hasMoreElements();
   ) {
      String key = (String) keys.nextElement();
      String value = (String) exifTags.get(key);

      if (key.startsWith(":")) {
  SystemKit.message(3, "Unknown EXIF tag: " + key.substring(8) + " = " + value);
      } else if (key.indexOf(":") > 0) {
  int pos = key.indexOf(":");
  String prefix = key.substring(0, pos);
  String localName = key.substring(pos + 1);

  if (RDFKit.namespaces.containsKey(prefix)) {
    Property p = model.createProperty((String) RDFKit.namespaces.get(prefix),
              localName);

    Statement s =
      model.createStatement(subject, p, value);
    model.add(s);
  }
      } else {
  Property p = model.createProperty((String) RDFKit.namespaces.get("exif"),
            key);

  Statement s =
    model.createStatement(subject, p, value);
  model.add(s);
      }
    }
  }
  // Exif object wrapper methods
  public Exif getExif() {
    return exif;
  }

  public void setExif(Exif exif) {
    this.exif = exif;
  }

  public void addTag(int tagNo, String string) {
    exif.addTag(tagNo, string);
  }

  public void addGpsTag(int tagNo, String string) {
    exif.addGpsTag(tagNo, string);
  }

  // this lot probably redundant
  public void addDecoder(
    String name,
    TagDecoder decoder) {
    exif.addDecoder(name, decoder);
  }
  public void addHeight(String height) {
    exif.addHeight(height);
  }

  public void addWidth(String width) {
    exif.addWidth(width);
  }

  public void addCompression(String comp) {
    exif.addCompression(comp);
  }

  public void addBitsPerPixel(String sbpp) {
    exif.addBitsPerPixel(sbpp);
  }

  public void addNumberOfColorComponents(String numCC) {
    exif.addNumberOfColorComponents(numCC);
  }

  public Hashtable getTags() {
    return exif.getTags();
  }

  void initExifPrefixes() {
    // Consider the namespaces we know about...make sure
    // that exif:
    // and exifi: are in there
    exifPrefix = RDFKit.getNamespacePrefix(nsExif);
    exifiPrefix = RDFKit.getNamespacePrefix(nsExifI);
  }

  public void update(Model model, Resource subject) {
    Hashtable exifHash = exif.getTags();
    if (exifHash != null) { //??
      Hashtable exifTags = exif.getTags();

      if (sHeight != null
        && exifTags.containsKey(sHeight)) {
        RDFKit.updateLiteral(
          model,
          subject,
          sHeight,
          (String) exifTags.get(sHeight));
      }

      if (sWidth != null
        && exifTags.containsKey(sWidth)) {
        RDFKit.updateLiteral(
          model,
          subject,
          sWidth,
          (String) exifTags.get(sWidth));
      }

      if (sComp != null
        && exifTags.containsKey(sComp)) {
        RDFKit.updateLiteral(
          model,
          subject,
          sComp,
          (String) exifTags.get(sComp));
      }

      if (sBPP != null
        && exifTags.containsKey(sBPP)) {
        RDFKit.updateLiteral(
          model,
          subject,
          sBPP,
          (String) exifTags.get(sBPP));
      }

      if (sNumCC != null
        && exifTags.containsKey(sNumCC)) {
        RDFKit.updateLiteral(
          model,
          subject,
          sNumCC,
          (String) exifTags.get(sNumCC));
      }
    }
  }

  public void addTag(Resource rsrc, int tagNo) {
    if (tagNo == iHeight) {
      sHeight =
        exifiPrefix + ":" + rsrc.getLocalName();
      exif.addHeight(sHeight);
    } else if (tagNo == iWidth) {
      sWidth =
        exifiPrefix + ":" + rsrc.getLocalName();
      exif.addWidth(sWidth);
    } else if (tagNo == iComp) {
      sComp = exifiPrefix + ":" + rsrc.getLocalName();
      exif.addCompression(sComp);
    } else if (tagNo == iBPP) {
      sBPP = exifiPrefix + ":" + rsrc.getLocalName();
      exif.addBitsPerPixel(sBPP);
    } else if (tagNo == iNumCC) {
      sNumCC =
        exifiPrefix + ":" + rsrc.getLocalName();
      exif.addNumberOfColorComponents(sNumCC);
    }

  }

}
