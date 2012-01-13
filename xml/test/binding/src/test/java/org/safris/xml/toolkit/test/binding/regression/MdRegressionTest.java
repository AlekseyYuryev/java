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

import liberty_metadata_2003_08.md_EntitiesDescriptor;
import liberty_metadata_2003_08.md_EntityDescriptor;
import liberty_metadata_2003_08.md_Extension;
import liberty_metadata_2003_08.md_Status;
import liberty_metadata_2003_08.md_cacheDuration$;
import liberty_metadata_2003_08.md_validUntil$;
import org.junit.Ignore;
import org.w3.x2000.x09.xmldsig.ds_KeyInfo;

@Ignore("Make this a real test!")
public class MdRegressionTest extends RegressionTest {
  private static final String namespaceURI = "urn:liberty:metadata:2003-08";

  public static String getNamespaceURI() {
    return namespaceURI;
  }

  private static RegressionTest instance = new MdRegressionTest();

  public static void main(String[] args) {
    getEntitiesDescriptor();
  }

  public static md_Status getStatus() {
    boolean verifiable = isVerifiable();
    if (verifiable)
      setVerifiable(false);

    md_Status binding = new md_Status();
    if (Math.random() < RECURSION_SEED)
      binding.addmd_Status(getStatus());
    binding.set_code$(new md_Status._code$(getRandomQName()));

    if (verifiable) {
      verify(binding);
      setVerifiable(true);
    }

    return binding;
  }

  public static md_Extension getExtension() {
    boolean verifiable = isVerifiable();
    if (verifiable)
      setVerifiable(false);

    md_Extension binding = new md_Extension();
    do
    binding.addAny(instance.getAny());
    while(Math.random() < ADD_SEED);

    if (verifiable) {
      verify(binding);
      setVerifiable(true);
    }

    return binding;
  }

  public static md_EntityDescriptor getEntityDescriptor() {
    boolean verifiable = isVerifiable();
    if (verifiable)
      setVerifiable(false);

    md_EntityDescriptor binding = new md_EntityDescriptor();
    // FIXME: This HAS to be ratified!!!! ($t's in a sequence)
    while (Math.random() < ADD_SEED)
      binding.add_SPDescriptor(ServiceProviderMetadata.getSPDescriptor());
    while (Math.random() < ADD_SEED)
      binding.add_IDPDescriptor(IdentityProviderMetadata.getIDPDescriptor());
    binding.set_id$(new md_EntityDescriptor._id$(getRandomString()));
    binding.setmd_validUntil$(new md_validUntil$(getRandomDateTime()));
    binding.setmd_cacheDuration$(new md_cacheDuration$(getRandomDuration()));

    if (verifiable) {
      verify(binding);
      setVerifiable(true);
    }

    return binding;
  }

  public static md_EntitiesDescriptor getEntitiesDescriptor() {
    boolean verifiable = isVerifiable();
    if (verifiable)
      setVerifiable(false);

    md_EntitiesDescriptor binding = new md_EntitiesDescriptor();
    for (int i = 0; i < 2 || Math.random() < ADD_SEED; i++)
      binding.addmd_EntityDescriptor(getEntityDescriptor());

    if (verifiable) {
      verify(binding);
      setVerifiable(true);
    }

    return binding;
  }

  public static ds_KeyInfo getKeyInfo() {
    boolean verifiable = isVerifiable();
    if (verifiable)
      setVerifiable(false);

    ds_KeyInfo binding = new ds_KeyInfo();
    while (Math.random() < ADD_SEED)
      binding.addAny(instance.getAny());
    do
      binding.addds_KeyName(DsRegressionTest.getKeyName());
    while(Math.random() < ADD_SEED);
    do
      binding.addds_KeyValue(DsRegressionTest.getKeyValue());
    while(Math.random() < ADD_SEED);
    do
      binding.addds_MgmtData(DsRegressionTest.getMgmtData());
    while(Math.random() < ADD_SEED);
    do
      binding.addds_PGPData(DsRegressionTest.getPGPData());
    while(Math.random() < ADD_SEED);
    do
      binding.addds_RetrievalMethod(DsRegressionTest.getRetrievalMethod());
    while(Math.random() < ADD_SEED);
    do
      binding.addds_SPKIData(DsRegressionTest.getSPKIData());
    while(Math.random() < ADD_SEED);
    do
      binding.addds_X509Data(DsRegressionTest.getX509Data());
    while(Math.random() < ADD_SEED);

    if (verifiable) {
      verify(binding);
      setVerifiable(true);
    }

    return binding;
  }
}
