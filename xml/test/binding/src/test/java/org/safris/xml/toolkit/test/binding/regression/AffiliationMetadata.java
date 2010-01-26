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
