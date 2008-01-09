package org.safris.xml.toolkit.test.binding.regression;

import liberty_metadata_2003_08.IMdAffiliationDescriptorType;
import liberty_metadata_2003_08.IMdEntityDescriptorType;

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

	public static IMdAffiliationDescriptorType getAffiliationDescriptor()
	{
		IMdAffiliationDescriptorType affiliationDescriptor = new IMdEntityDescriptorType.MdAffiliationDescriptor();
		affiliationDescriptor.addMdAffiliateMember(new IMdAffiliationDescriptorType.MdAffiliateMember("https://aol-3." + domain + "/metadata.xml"));
		affiliationDescriptor.addMdAffiliateMember(new IMdAffiliationDescriptorType.MdAffiliateMember("https://aol-4." + domain + "/metadata.xml"));
		affiliationDescriptor.setMdAffiliationIDAttr(new IMdAffiliationDescriptorType.MdAffiliationIDAttr("https://aol-3." + domain + "/affiliation.xml"));
		affiliationDescriptor.setMdAffiliationOwnerIDAttr(new IMdAffiliationDescriptorType.MdAffiliationOwnerIDAttr("https://aol-3." + domain + "/metadata.xml"));
		return affiliationDescriptor;
	}
}
