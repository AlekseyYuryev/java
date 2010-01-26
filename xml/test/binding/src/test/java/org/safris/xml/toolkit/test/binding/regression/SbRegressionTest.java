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

import liberty_sb_2003_08.sb_Consent;
import liberty_sb_2003_08.sb_Correlation;
import liberty_sb_2003_08.sb_UsageDirective;
import org.junit.Ignore;
import org.xmlsoap.schemas.soap.envelope.tns_actor$;
import org.xmlsoap.schemas.soap.envelope.tns_mustUnderstand$;

@Ignore("Make this a real test!")
public class SbRegressionTest extends RegressionTest {
    private static final String namespaceURI = "urn:liberty:sb:2003-08";

    public static String getNamespaceURI() {
        return namespaceURI;
    }

    private static RegressionTest instance = new SbRegressionTest();

    public static void main(String[] args) {
        getCorrelation();
        getConsent();
        getUsageDirective();
    }

    public static sb_Correlation getCorrelation() {
        boolean verifiable = isVerifiable();
        if (verifiable)
            setVerifiable(false);

        sb_Correlation binding = new sb_Correlation();
        binding.set_messageID$(new sb_Correlation._messageID$(getRandomString()));
        binding.set_refToMessageID$(new sb_Correlation._refToMessageID$(getRandomString()));
        binding.set_timestamp$(new sb_Correlation._timestamp$(getRandomDateTime()));
        binding.set_id$(new sb_Correlation._id$(getRandomString()));
        binding.settns_mustUnderstand$(new tns_mustUnderstand$(getRandomBoolean()));
        binding.settns_actor$(new tns_actor$(getRandomString()));

        if (verifiable) {
            verify(binding);
            setVerifiable(true);
        }

        return binding;
    }

    public static sb_Consent getConsent() {
        boolean verifiable = isVerifiable();
        if (verifiable)
            setVerifiable(false);

        sb_Consent binding = new sb_Consent();
        binding.set_uri$(new sb_Consent._uri$(getRandomString()));
        binding.set_timestamp$(new sb_Consent._timestamp$(getRandomDateTime()));
        binding.set_id$(new sb_Consent._id$(getRandomString()));
        binding.settns_mustUnderstand$(new tns_mustUnderstand$(getRandomBoolean()));
        binding.settns_actor$(new tns_actor$(getRandomString()));

        if (verifiable) {
            verify(binding);
            setVerifiable(true);
        }

        return binding;
    }

    public static sb_UsageDirective getUsageDirective() {
        boolean verifiable = isVerifiable();
        if (verifiable)
            setVerifiable(false);

        sb_UsageDirective binding = new sb_UsageDirective();
        binding.set_id$(new sb_UsageDirective._id$(getRandomString()));
        binding.settns_mustUnderstand$(new tns_mustUnderstand$(getRandomBoolean()));
        binding.settns_actor$(new tns_actor$(getRandomString()));
        binding.set_ref$(new sb_UsageDirective._ref$(getRandomString()));
        do
            binding.addAny(instance.getAny());
        while(Math.random() < ADD_SEED);

        if (verifiable) {
            verify(binding);
            setVerifiable(true);
        }

        return binding;
    }
}
