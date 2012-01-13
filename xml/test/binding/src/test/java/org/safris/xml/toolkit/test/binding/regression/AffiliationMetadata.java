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

package org.safris.xml.toolkit.test.binding.regression;

import liberty_metadata_2003_08.$md_affiliationDescriptorType;
import liberty_metadata_2003_08.$md_entityDescriptorType;
import org.junit.Ignore;

@Ignore("Make this a real test!")
public class AffiliationMetadata extends Metadata {
  private static String DEFAULT_HOST = "aol-4";
  private static String DEFAULT_DOMAIN = "liberty-iop.biz";
  private static String host = DEFAULT_HOST;
  private static String domain = DEFAULT_DOMAIN;

  public static void main(String[] args) {
    if (args.length == 2) {
      host = args[0];
      domain = args[1];
    }

    System.out.println(getAffiliationDescriptor());
  }

  public static $md_affiliationDescriptorType getAffiliationDescriptor() {
    final $md_affiliationDescriptorType affiliationDescriptor = new $md_entityDescriptorType._AffiliationDescriptor();
    affiliationDescriptor.add_AffiliateMember(new $md_affiliationDescriptorType._AffiliateMember("https://aol-3." + domain + "/metadata.xml"));
    affiliationDescriptor.add_AffiliateMember(new $md_affiliationDescriptorType._AffiliateMember("https://aol-4." + domain + "/metadata.xml"));
    affiliationDescriptor.set_affiliationID$(new $md_affiliationDescriptorType._affiliationID$("https://aol-3." + domain + "/affiliation.xml"));
    affiliationDescriptor.set_affiliationOwnerID$(new $md_affiliationDescriptorType._affiliationOwnerID$("https://aol-3." + domain + "/metadata.xml"));
    return affiliationDescriptor;
  }
}
