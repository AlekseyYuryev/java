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

import liberty_disco_2003_08.$isf_ServiceInstanceType;
import liberty_disco_2003_08.isf_AuthenticateSessionContext;
import liberty_disco_2003_08.isf_AuthorizeRequester;
import liberty_disco_2003_08.isf_EncryptedResourceID;
import liberty_disco_2003_08.isf_Extension;
import liberty_disco_2003_08.isf_Options;
import liberty_disco_2003_08.isf_ResourceID;
import liberty_disco_2003_08.isf_ResourceOffering;
import liberty_disco_2003_08.isf_ServiceType;
import liberty_disco_2003_08.isf_Status;
import org.junit.Ignore;

@Ignore("Make this a real test!")
public class DscRegressionTest extends RegressionTest {
    private static final String namespaceURI = "urn:liberty:disco:2003-08";

    public static String getNamespaceURI() {
        return namespaceURI;
    }

    private static RegressionTest instance = new DscRegressionTest();

    public static void main(String[] args) {
        getExtension();
        getAuthorizeRequester();
        getAuthenticateSessionContext();
    }

    public static isf_Status getStatus() {
        boolean verifiable = isVerifiable();
        if (verifiable)
            setVerifiable(false);

        isf_Status binding = new isf_Status();
        if (Math.random() < RECURSION_SEED)
            binding.addisf_Status(getStatus());
        binding.set_code$(new isf_Status._code$(getRandomQName()));

        if (verifiable) {
            verify(binding);
            setVerifiable(true);
        }

        return binding;
    }

    public static isf_Extension getExtension() {
        boolean verifiable = isVerifiable();
        if (verifiable)
            setVerifiable(false);

        isf_Extension binding = new isf_Extension();
        do
            binding.addAny(instance.getAny());
        while(Math.random() < ADD_SEED);

        if (verifiable) {
            verify(binding);
            setVerifiable(true);
        }

        return binding;
    }

    public static isf_ServiceType getServiceType() {
        boolean verifiable = isVerifiable();
        if (verifiable)
            setVerifiable(false);

        isf_ServiceType binding = new isf_ServiceType(getRandomString());

        if (verifiable) {
            verify(binding);
            setVerifiable(true);
        }

        return binding;
    }

    public static isf_Options getOptions() {
        boolean verifiable = isVerifiable();
        if (verifiable)
            setVerifiable(false);

        isf_Options binding = new isf_Options();
        while (Math.random() < ADD_SEED)
            binding.add_Option(new isf_Options._Option(getRandomString()));

        if (verifiable) {
            verify(binding);
            setVerifiable(true);
        }

        return binding;
    }

    public static isf_ResourceOffering getResourceOffering() {
        boolean verifiable = isVerifiable();
        if (verifiable)
            setVerifiable(false);

        isf_ResourceOffering binding = new isf_ResourceOffering();
        double random = Math.random();
        if (random < 1 / 2)
            binding.addisf_ResourceID(getResourceID());
        else
            binding.addisf_EncryptedResourceID(getEncryptedResourceID());
        binding.set_entryID$(new isf_ResourceOffering._entryID$(getRandomString()));
        binding.add_ServiceInstance(getServiceInstance());
        if (Math.random() < RECURSION_SEED)
            binding.addisf_Options(getOptions());
        if (Math.random() < RECURSION_SEED)
            binding.add_Abstract(new isf_ResourceOffering._Abstract(getRandomString()));

        if (verifiable) {
            verify(binding);
            setVerifiable(true);
        }

        return binding;
    }

    public static isf_ResourceID getResourceID() {
        boolean verifiable = isVerifiable();
        if (verifiable)
            setVerifiable(false);

        isf_ResourceID binding = new isf_ResourceID();
        binding.set_id$(new isf_ResourceID._id$(getRandomString()));
        binding.setText(getRandomString());

        if (verifiable) {
            verify(binding);
            setVerifiable(true);
        }

        return binding;
    }

    public static isf_EncryptedResourceID getEncryptedResourceID() {
        boolean verifiable = isVerifiable();
        if (verifiable)
            setVerifiable(false);

        isf_EncryptedResourceID binding = new isf_EncryptedResourceID();
        binding.addxenc_EncryptedData(XencRegressionTest.getEncryptedData());
        binding.addxenc_EncryptedKey(XencRegressionTest.getEncryptedKey());

        if (verifiable) {
            verify(binding);
            setVerifiable(true);
        }

        return binding;
    }

    public static final $isf_ServiceInstanceType getServiceInstance() {
        $isf_ServiceInstanceType binding = new $isf_ServiceInstanceType() {
            protected $isf_ServiceInstanceType inherits() {
                return null;
            }
        };
        $isf_ServiceInstanceType._Description description = new $isf_ServiceInstanceType._Description();

        do {
            do
                description.add_CredentialRef(new $isf_ServiceInstanceType._Description._CredentialRef(getRandomString()));
            while(Math.random() < ADD_SEED);
            do
                description.add_SecurityMechID(new $isf_ServiceInstanceType._Description._SecurityMechID(getRandomString()));
            while(Math.random() < ADD_SEED);
            description.add_Endpoint(new $isf_ServiceInstanceType._Description._Endpoint(getRandomString()));
            description.add_ServiceNameRef(new $isf_ServiceInstanceType._Description._ServiceNameRef(getRandomQName()));
            description.add_SoapAction(new $isf_ServiceInstanceType._Description._SoapAction(getRandomString()));
            description.add_WsdlURI(new $isf_ServiceInstanceType._Description._WsdlURI(getRandomString()));
            binding.add_Description(description);
        }
        while(Math.random() < ADD_SEED);
        binding.add_ProviderID(new $isf_ServiceInstanceType._ProviderID(LibRegressionTest.getProviderID().getText()));
        binding.addisf_ServiceType(getServiceType());
        return binding;
    }

    public static isf_AuthorizeRequester getAuthorizeRequester() {
        boolean verifiable = isVerifiable();
        if (verifiable)
            setVerifiable(false);

        isf_AuthorizeRequester binding = new isf_AuthorizeRequester();
        while (Math.random() < ADD_SEED)
            binding.set_descriptionIDRefs$(new isf_AuthorizeRequester._descriptionIDRefs$(getRandomStrings()));

        if (verifiable) {
            verify(binding);
            setVerifiable(true);
        }

        return binding;
    }

    public static isf_AuthenticateSessionContext getAuthenticateSessionContext() {
        boolean verifiable = isVerifiable();
        if (verifiable)
            setVerifiable(false);

        isf_AuthenticateSessionContext binding = new isf_AuthenticateSessionContext();
        while (Math.random() < ADD_SEED)
            binding.set_descriptionIDRefs$(new isf_AuthenticateSessionContext._descriptionIDRefs$(getRandomStrings()));

        if (verifiable) {
            verify(binding);
            setVerifiable(true);
        }

        return binding;
    }
}
