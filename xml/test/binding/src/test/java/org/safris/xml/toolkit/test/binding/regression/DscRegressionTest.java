package org.safris.xml.toolkit.test.binding.regression;

import liberty_disco_2003_08.IIsfServiceInstanceType;
import liberty_disco_2003_08.IsfAuthenticateSessionContext;
import liberty_disco_2003_08.IsfAuthorizeRequester;
import liberty_disco_2003_08.IsfEncryptedResourceID;
import liberty_disco_2003_08.IsfExtension;
import liberty_disco_2003_08.IsfOptions;
import liberty_disco_2003_08.IsfResourceID;
import liberty_disco_2003_08.IsfResourceOffering;
import liberty_disco_2003_08.IsfServiceType;
import liberty_disco_2003_08.IsfStatus;
import org.safris.xml.toolkit.test.binding.regression.DscRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.LibRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.RegressionTest;
import org.safris.xml.toolkit.test.binding.regression.XencRegressionTest;

public class DscRegressionTest extends RegressionTest
{
	private static final String namespaceURI = "urn:liberty:disco:2003-08";

	public static String getNamespaceURI()
	{
		return namespaceURI;
	}

	private static RegressionTest instance = new DscRegressionTest();

	public static void main(String[] args)
	{
		getExtension();
		getAuthorizeRequester();
		getAuthenticateSessionContext();
	}

	public static IsfStatus getStatus()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		IsfStatus binding = new IsfStatus();
		if(Math.random() < RECURSION_SEED)
			binding.setIsfStatus(getStatus());
		binding.setIsfCodeAttr(new IsfStatus.IsfCodeAttr(getRandomQName()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static IsfExtension getExtension()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		IsfExtension binding = new IsfExtension();
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

	public static IsfServiceType getServiceType()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		IsfServiceType binding = new IsfServiceType(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static IsfOptions getOptions()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		IsfOptions binding = new IsfOptions();
		while(Math.random() < ADD_SEED)
			binding.addIsfOption(new IsfOptions.IsfOption(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static IsfResourceOffering getResourceOffering()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		IsfResourceOffering binding = new IsfResourceOffering();
		double random = Math.random();
		if(random < 1 / 2)
			binding.setIsfResourceID(getResourceID());
		else
			binding.setIsfEncryptedResourceID(getEncryptedResourceID());
		binding.setIsfEntryIDAttr(new IsfResourceOffering.IsfEntryIDAttr(getRandomString()));
		binding.setIsfServiceInstance(getServiceInstance());
		if(Math.random() < RECURSION_SEED)
			binding.setIsfOptions(getOptions());
		if(Math.random() < RECURSION_SEED)
			binding.setIsfAbstract(new IsfResourceOffering.IsfAbstract(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static IsfResourceID getResourceID()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		IsfResourceID binding = new IsfResourceID();
		binding.setIsfIdAttr(new IsfResourceID.IsfIdAttr(getRandomString()));
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static IsfEncryptedResourceID getEncryptedResourceID()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		IsfEncryptedResourceID binding = new IsfEncryptedResourceID();
		binding.setXencEncryptedData(XencRegressionTest.getEncryptedData());
		binding.setXencEncryptedKey(XencRegressionTest.getEncryptedKey());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static final IIsfServiceInstanceType getServiceInstance()
	{
		IIsfServiceInstanceType binding = new IIsfServiceInstanceType()
		{
			protected IIsfServiceInstanceType inherits()
			{
				return null;
			}
		};
		IIsfServiceInstanceType.IsfDescription description = new IIsfServiceInstanceType.IsfDescription();

		do
		{
			do
			description.addIsfCredentialRef(new IIsfServiceInstanceType.IsfDescription.IsfCredentialRef(getRandomString()));
			while(Math.random() < ADD_SEED);
			do
			description.addIsfSecurityMechID(new IIsfServiceInstanceType.IsfDescription.IsfSecurityMechID(getRandomString()));
			while(Math.random() < ADD_SEED);
			description.setIsfEndpoint(new IIsfServiceInstanceType.IsfDescription.IsfEndpoint(getRandomString()));
			description.setIsfServiceNameRef(new IIsfServiceInstanceType.IsfDescription.IsfServiceNameRef(getRandomQName()));
			description.setIsfSoapAction(new IIsfServiceInstanceType.IsfDescription.IsfSoapAction(getRandomString()));
			description.setIsfWsdlURI(new IIsfServiceInstanceType.IsfDescription.IsfWsdlURI(getRandomString()));
			binding.addIsfDescription(description);
		}
		while(Math.random() < ADD_SEED);
		binding.setIsfProviderID(new IIsfServiceInstanceType.IsfProviderID(LibRegressionTest.getProviderID().getTEXT()));
		binding.setIsfServiceType(getServiceType());
		return binding;
	}

	public static IsfAuthorizeRequester getAuthorizeRequester()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		IsfAuthorizeRequester binding = new IsfAuthorizeRequester();
		while(Math.random() < ADD_SEED)
			binding.setIsfDescriptionIDRefsAttr(new IsfAuthorizeRequester.IsfDescriptionIDRefsAttr(getRandomStrings()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static IsfAuthenticateSessionContext getAuthenticateSessionContext()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		IsfAuthenticateSessionContext binding = new IsfAuthenticateSessionContext();
		while(Math.random() < ADD_SEED)
			binding.setIsfDescriptionIDRefsAttr(new IsfAuthenticateSessionContext.IsfDescriptionIDRefsAttr(getRandomStrings()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}
}
