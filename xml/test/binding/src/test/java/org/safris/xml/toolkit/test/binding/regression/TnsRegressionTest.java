/*  Copyright 2010 Safris Technologies Inc.
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
