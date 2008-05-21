package org.safris.xml.toolkit.test.binding.regression;

import liberty_metadata_2003_08.$md_affiliationDescriptorType;
import liberty_metadata_2003_08.$md_entityDescriptorType;
import org.junit.Ignore;

@Ignore("Make this a real test!")
public class AffiliationMetadata extends Metadata
{
	private static String DEFAULT_HOST = "aol-4";
	private static String DEFAULT_DOMAIN = "liberty-iop.biz";
	private static String host = DEFAULT_HOST;
	private static String domain = DEFAULT_DOMAIN;

	public static void main(String[] args)
	{
		if(args.length == 2)
		{
			host = args[0];
			domain = args[1];
		}

		System.out.println(getAffiliationDescriptor());
	}

	public static $md_affiliationDescriptorType getAffiliationDescriptor()
	{
		$md_affiliationDescriptorType affiliationDescriptor = new $md_entityDescriptorType._AffiliationDescriptor();
		affiliationDescriptor.add_AffiliateMember(new $md_affiliationDescriptorType._AffiliateMember("https://aol-3." + domain + "/metadata.xml"));
		affiliationDescriptor.add_AffiliateMember(new $md_affiliationDescriptorType._AffiliateMember("https://aol-4." + domain + "/metadata.xml"));
		affiliationDescriptor.set_affiliationID$(new $md_affiliationDescriptorType._affiliationID$("https://aol-3." + domain + "/affiliation.xml"));
		affiliationDescriptor.set_affiliationOwnerID$(new $md_affiliationDescriptorType._affiliationOwnerID$("https://aol-3." + domain + "/metadata.xml"));
		return affiliationDescriptor;
	}
}
