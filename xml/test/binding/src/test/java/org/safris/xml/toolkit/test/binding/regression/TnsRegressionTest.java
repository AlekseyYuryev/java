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

import org.junit.Ignore;
import org.xmlsoap.schemas.soap.envelope.tns_Body;
import org.xmlsoap.schemas.soap.envelope.tns_Envelope;
import org.xmlsoap.schemas.soap.envelope.tns_Fault;
import org.xmlsoap.schemas.soap.envelope.tns_Header;

@Ignore("Make this a real test!")
public class TnsRegressionTest extends RegressionTest {
  private static final String namespaceURI = "http://schemas.xmlsoap.org/soap/envelope/";

  public static String getNamespaceURI() {
    return namespaceURI;
  }

  private static RegressionTest instance = new DscRegressionTest();

  public static void main(String[] args) {
    getEnvelope();
    getFault();
  }

  public static tns_Envelope getEnvelope() {
    boolean verifiable = isVerifiable();
    if (verifiable)
      setVerifiable(false);

    tns_Envelope binding = new tns_Envelope();
    while (Math.random() < ADD_SEED)
      binding.addtns_Header(getHeader());
    binding.addtns_Body(getBody());
    while (Math.random() < ADD_SEED)
      binding.addAny(instance.getAny());
//          while(Math.random() < ADD_SEED)
//              binding.addAnyATTR(instance.getAny$ibute(getRandomString()));

    if (verifiable) {
      verify(binding);
      setVerifiable(true);
    }
    return binding;
  }

  public static tns_Header getHeader() {
    boolean verifiable = isVerifiable();
    if (verifiable)
      setVerifiable(false);

    tns_Header binding = new tns_Header();
    while (Math.random() < ADD_SEED)
      binding.addAny(instance.getAny());
//          do
//              binding.addAnyATTR(instance.getAny$ibute(getRandomString()));
//          while(Math.random() < ADD_SEED);

    if (verifiable) {
      verify(binding);
      setVerifiable(true);
    }
    return binding;
  }

  public static tns_Body getBody() {
    boolean verifiable = isVerifiable();
    if (verifiable)
      setVerifiable(false);

    tns_Body binding = new tns_Body();
    while (Math.random() < ADD_SEED)
      binding.addAny(instance.getAny());
//      do
//          binding.addAnyATTR(instance.getAny$ibute(getRandomString()));
//      while(Math.random() < ADD_SEED);

    if (verifiable) {
      verify(binding);
      setVerifiable(true);
    }
    return binding;
  }

  public static tns_Fault getFault() {
    boolean verifiable = isVerifiable();
    if (verifiable)
      setVerifiable(false);

    tns_Fault binding = new tns_Fault();
    binding.add_faultcode(new tns_Fault._faultcode(getRandomQName()));
    binding.add_faultstring(new tns_Fault._faultstring(getRandomString()));
    binding.add_faultactor(new tns_Fault._faultactor(getRandomString()));
    tns_Fault._detail detail = new tns_Fault._detail();
    while (Math.random() < ADD_SEED)
      detail.addAny(instance.getAny());
//      while(Math.random() < ADD_SEED)
//          detail.addAnyATTR(instance.getAny$ibute(getRandomString()));
    binding.add_detail(detail);

    if (verifiable) {
      verify(binding);
      setVerifiable(true);
    }
    return binding;
  }
}
