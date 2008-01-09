package org.safris.xml.toolkit.test.binding.regression;

import liberty_metadata_2003_08.MdCacheDurationAttr;
import liberty_metadata_2003_08.MdEntitiesDescriptor;
import liberty_metadata_2003_08.MdEntityDescriptor;
import liberty_metadata_2003_08.MdExtension;
import liberty_metadata_2003_08.MdStatus;
import liberty_metadata_2003_08.MdValidUntilAttr;
import org.safris.xml.toolkit.test.binding.regression.DsRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.IdentityProviderMetadata;
import org.safris.xml.toolkit.test.binding.regression.MdRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.RegressionTest;
import org.safris.xml.toolkit.test.binding.regression.ServiceProviderMetadata;
import org.w3.x2000.x09.xmldsig.DsKeyInfo;

public class MdRegressionTest extends RegressionTest
{
	private static final String namespaceURI = "urn:liberty:metadata:2003-08";

	public static String getNamespaceURI()
	{
		return namespaceURI;
	}

	private static RegressionTest instance = new MdRegressionTest();

	public static void main(String[] args)
	{
		getEntitiesDescriptor();
	}

	public static MdStatus getStatus()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		MdStatus binding = new MdStatus();
		if(Math.random() < RECURSION_SEED)
			binding.setMdStatus(getStatus());
		binding.setMdCodeAttr(new MdStatus.MdCodeAttr(getRandomQName()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static MdExtension getExtension()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		MdExtension binding = new MdExtension();
		do
		binding.addANY(instance.getAny());
		while(Math.random() < ADD_SEED);

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static MdEntityDescriptor getEntityDescriptor()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		MdEntityDescriptor binding = new MdEntityDescriptor();
		// FIXME: This HAS to be ratified!!!! (It's in a sequence)
		while(Math.random() < ADD_SEED)
			binding.addMdSPDescriptor(ServiceProviderMetadata.getSPDescriptor());
		while(Math.random() < ADD_SEED)
			binding.addMdIDPDescriptor(IdentityProviderMetadata.getIDPDescriptor());
		binding.setMdIdAttr(new MdEntityDescriptor.MdIdAttr(getRandomString()));
		binding.setMdValidUntilAttr(new MdValidUntilAttr(getRandomDateTime()));
		binding.setMdCacheDurationAttr(new MdCacheDurationAttr(getRandomDuration()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static MdEntitiesDescriptor getEntitiesDescriptor()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		MdEntitiesDescriptor binding = new MdEntitiesDescriptor();
		for(int i = 0; i < 2 || Math.random() < ADD_SEED; i++)
			binding.addMdEntityDescriptor(getEntityDescriptor());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static DsKeyInfo getKeyInfo()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		DsKeyInfo binding = new DsKeyInfo();
		while(Math.random() < ADD_SEED)
			binding.addANY(instance.getAny());
		do
		binding.addDsKeyName(DsRegressionTest.getKeyName());
		while(Math.random() < ADD_SEED);
		do
		binding.addDsKeyValue(DsRegressionTest.getKeyValue());
		while(Math.random() < ADD_SEED);
		do
		binding.addDsMgmtData(DsRegressionTest.getMgmtData());
		while(Math.random() < ADD_SEED);
		do
		binding.addDsPGPData(DsRegressionTest.getPGPData());
		while(Math.random() < ADD_SEED);
		do
		binding.addDsRetrievalMethod(DsRegressionTest.getRetrievalMethod());
		while(Math.random() < ADD_SEED);
		do
		binding.addDsSPKIData(DsRegressionTest.getSPKIData());
		while(Math.random() < ADD_SEED);
		do
		binding.addDsX509Data(DsRegressionTest.getX509Data());
		while(Math.random() < ADD_SEED);

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}
}
