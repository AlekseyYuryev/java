package org.safris.xml.toolkit.test.binding.regression;

import liberty_sb_2003_08.SbConsent;
import liberty_sb_2003_08.SbCorrelation;
import liberty_sb_2003_08.SbUsageDirective;
import org.safris.xml.toolkit.test.binding.regression.DscRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.RegressionTest;
import org.xmlsoap.schemas.soap.envelope.TnsActorAttr;
import org.xmlsoap.schemas.soap.envelope.TnsMustUnderstandAttr;

public class SbRegressionTest extends RegressionTest
{
	private static final String namespaceURI = "urn:liberty:sb:2003-08";

	public static String getNamespaceURI()
	{
		return namespaceURI;
	}

	private static RegressionTest instance = new DscRegressionTest();

	public static void main(String[] args)
	{
		getCorrelation();
		getConsent();
		getUsageDirective();
	}

	public static SbCorrelation getCorrelation()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SbCorrelation binding = new SbCorrelation();
		binding.setSbMessageIDAttr(new SbCorrelation.SbMessageIDAttr(getRandomString()));
		binding.setSbRefToMessageIDAttr(new SbCorrelation.SbRefToMessageIDAttr(getRandomString()));
		binding.setSbTimestampAttr(new SbCorrelation.SbTimestampAttr(getRandomDateTime()));
		binding.setSbIdAttr(new SbCorrelation.SbIdAttr(getRandomString()));
		binding.setTnsMustUnderstandAttr(new TnsMustUnderstandAttr(getRandomBoolean()));
		binding.setTnsActorAttr(new TnsActorAttr(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SbConsent getConsent()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SbConsent binding = new SbConsent();
		binding.setSbUriAttr(new SbConsent.SbUriAttr(getRandomString()));
		binding.setSbTimestampAttr(new SbConsent.SbTimestampAttr(getRandomDateTime()));
		binding.setSbIdAttr(new SbConsent.SbIdAttr(getRandomString()));
		binding.setTnsMustUnderstandAttr(new TnsMustUnderstandAttr(getRandomBoolean()));
		binding.setTnsActorAttr(new TnsActorAttr(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SbUsageDirective getUsageDirective()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SbUsageDirective binding = new SbUsageDirective();
		binding.setSbIdAttr(new SbUsageDirective.SbIdAttr(getRandomString()));
		binding.setTnsMustUnderstandAttr(new TnsMustUnderstandAttr(getRandomBoolean()));
		binding.setTnsActorAttr(new TnsActorAttr(getRandomString()));
		binding.setSbRefAttr(new SbUsageDirective.SbRefAttr(getRandomString()));
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
}
