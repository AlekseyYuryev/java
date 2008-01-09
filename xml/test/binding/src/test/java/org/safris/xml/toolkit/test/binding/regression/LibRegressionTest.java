package org.safris.xml.toolkit.test.binding.regression;

import _0_assertion.oasis_names_tc_saml_1.ISamlStatementAbstractType;
import liberty_iff_2003_08.ILibAuthenticationStatementType;
import liberty_iff_2003_08.LibAffiliationID;
import liberty_iff_2003_08.LibAssertion;
import liberty_iff_2003_08.LibAuthnContext;
import liberty_iff_2003_08.LibAuthnRequest;
import liberty_iff_2003_08.LibAuthnRequestEnvelope;
import liberty_iff_2003_08.LibAuthnResponse;
import liberty_iff_2003_08.LibAuthnResponseEnvelope;
import liberty_iff_2003_08.LibConsentAttr;
import liberty_iff_2003_08.LibExtension;
import liberty_iff_2003_08.LibFederationTerminationNotification;
import liberty_iff_2003_08.LibGetComplete;
import liberty_iff_2003_08.LibIDPEntries;
import liberty_iff_2003_08.LibIDPEntry;
import liberty_iff_2003_08.LibIDPList;
import liberty_iff_2003_08.LibIDPProvidedNameIdentifier;
import liberty_iff_2003_08.LibLogoutRequest;
import liberty_iff_2003_08.LibLogoutResponse;
import liberty_iff_2003_08.LibNameIDPolicy;
import liberty_iff_2003_08.LibOldProvidedNameIdentifier;
import liberty_iff_2003_08.LibProtocolProfile;
import liberty_iff_2003_08.LibProviderID;
import liberty_iff_2003_08.LibRegisterNameIdentifierRequest;
import liberty_iff_2003_08.LibRegisterNameIdentifierResponse;
import liberty_iff_2003_08.LibRelayState;
import liberty_iff_2003_08.LibSPProvidedNameIdentifier;
import org.safris.xml.toolkit.test.binding.regression.AcRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.DsRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.LibRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.RegressionTest;
import org.safris.xml.toolkit.test.binding.regression.SamlRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.SamlpRegressionTest;

public class LibRegressionTest extends RegressionTest
{
	private static final String namespaceURI = "urn:liberty:iff:2003-08";

	public static String getNamespaceURI()
	{
		return namespaceURI;
	}

	private static RegressionTest instance = new LibRegressionTest();

	public static void main(String[] args)
	{
		getAuthnRequest();
		getAuthnRequestEnvelope();
		getAuthnResponse();
		getAuthnResponseEnvelope();
		getRegisterNameIdentifierRequest();
		getRegisterNameIdentifierResponse();
		getFederationTerminationNotification();
		getLogoutRequest();
		getLogoutResponse();
	}

	public static LibProviderID getProviderID()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibProviderID binding = new LibProviderID();
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static LibAffiliationID getAffiliationID()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibAffiliationID binding = new LibAffiliationID();
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static LibAuthnRequest getAuthnRequest()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibAuthnRequest binding = new LibAuthnRequest();
		binding.setSamlpIssueInstantAttr(new LibAuthnRequest.SamlpIssueInstantAttr(getRandomDateTime()));
		binding.setSamlpMajorVersionAttr(new LibAuthnRequest.SamlpMajorVersionAttr(getRandomInteger()));
		binding.setSamlpMinorVersionAttr(new LibAuthnRequest.SamlpMinorVersionAttr(getRandomInteger()));
		while(Math.random() < ADD_SEED)
			binding.addSamlpRespondWith(SamlpRegressionTest.getRespondWith());
		binding.setSamlpRequestIDAttr(new LibAuthnRequest.SamlpRequestIDAttr(getRandomString()));
		binding.setDsSignature(DsRegressionTest.getSignature());

		while(Math.random() < ADD_SEED)
			binding.addLibExtension(getExtension());
		binding.setLibProviderID(getProviderID());
		binding.setLibAffiliationID(getAffiliationID());
		binding.setLibNameIDPolicy(getNameIDPolicy());
		binding.setLibForceAuthn(new LibAuthnRequest.LibForceAuthn(getRandomBoolean()));
		binding.setLibIsPassive(new LibAuthnRequest.LibIsPassive(getRandomBoolean()));
		binding.setLibProtocolProfile(getProtocolProfile());
		binding.setLibAssertionConsumerServiceID(new LibAuthnRequest.LibAssertionConsumerServiceID(getRandomString()));

		LibAuthnContext.LibAuthnContextComparison authnContextComparison = null;
		double random = Math.random();
		if(random < 1 / 3)
			authnContextComparison = new LibAuthnContext.LibAuthnContextComparison(LibAuthnContext.LibAuthnContextComparison.BETTER);
		else if(random < 2 / 3)
			authnContextComparison = new LibAuthnContext.LibAuthnContextComparison(LibAuthnContext.LibAuthnContextComparison.EXACT);
		else
			authnContextComparison = new LibAuthnContext.LibAuthnContextComparison(LibAuthnContext.LibAuthnContextComparison.MINIMUM);

		LibAuthnContext authnContext = new LibAuthnContext();
		authnContext.setLibAuthnContextComparison(authnContextComparison);

		if(Math.random() < CHOICE_SEED)
			do
			authnContext.addLibAuthnContextClassRef(new LibAuthnContext.LibAuthnContextClassRef(getRandomString()));
			while(Math.random() < ADD_SEED);
		else
			do
			authnContext.addLibAuthnContextStatementRef(new LibAuthnContext.LibAuthnContextStatementRef(getRandomString()));
			while(Math.random() < ADD_SEED);

		binding.setLibAuthnContext(authnContext);
		binding.setLibRelayState(getRelayState());
		binding.setLibConsentAttr(new LibConsentAttr(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static LibNameIDPolicy getNameIDPolicy()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibNameIDPolicy binding;
		double random = Math.random();
		if(random < 1 / 4)
			binding = new LibNameIDPolicy(LibNameIDPolicy.FEDERATED);
		else if(random < 1 / 2)
			binding = new LibNameIDPolicy(LibNameIDPolicy.NONE);
		else if(random < 3 / 4)
			binding = new LibNameIDPolicy(LibNameIDPolicy.ONETIME);
		else
			binding = new LibNameIDPolicy(LibNameIDPolicy.ANY);

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static LibRelayState getRelayState()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibRelayState binding = new LibRelayState();
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static LibProtocolProfile getProtocolProfile()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibProtocolProfile binding = new LibProtocolProfile();
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static LibAuthnContext getAuthnContext()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibAuthnContext binding = new LibAuthnContext();
		if(Math.random() < CHOICE_SEED)
			do
			binding.addLibAuthnContextClassRef(new LibAuthnContext.LibAuthnContextClassRef(getRandomString()));
			while(Math.random() < ADD_SEED);
		else
			do
			binding.addLibAuthnContextStatementRef(new LibAuthnContext.LibAuthnContextStatementRef(getRandomString()));
			while(Math.random() < ADD_SEED);

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static LibAuthnResponse getAuthnResponse()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibAuthnResponse binding = new LibAuthnResponse();
		while(Math.random() < ADD_SEED)
			binding.addSamlAssertion(getAssertion());
		binding.setSamlpInResponseToAttr(new LibAuthnResponse.SamlpInResponseToAttr(getRandomString()));
		binding.setSamlpIssueInstantAttr(new LibAuthnResponse.SamlpIssueInstantAttr(getRandomDateTime()));
		binding.setSamlpMajorVersionAttr(new LibAuthnResponse.SamlpMajorVersionAttr(getRandomInteger()));
		binding.setSamlpMinorVersionAttr(new LibAuthnResponse.SamlpMinorVersionAttr(getRandomInteger()));
		binding.setSamlpRecipientAttr(new LibAuthnResponse.SamlpRecipientAttr(getRandomString()));
		binding.setSamlpResponseIDAttr(new LibAuthnResponse.SamlpResponseIDAttr(getRandomString()));
		binding.setDsSignature(DsRegressionTest.getSignature());
		binding.setSamlpStatus(SamlpRegressionTest.getStatus());

		while(Math.random() < ADD_SEED)
			binding.addLibExtension(getExtension());
		binding.setLibProviderID(getProviderID());
		binding.setLibRelayState(getRelayState());
		binding.setLibConsentAttr(new LibConsentAttr(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static LibAssertion getAssertion()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibAssertion binding = new LibAssertion();
		do
		binding.addSamlAttributeStatement(SamlRegressionTest.getAttributeStatement());
		while(Math.random() < ADD_SEED);
		while(Math.random() < ADD_SEED)
			binding.addSamlAuthenticationStatement(getAuthenticationStatement());
		while(Math.random() < ADD_SEED)
			binding.addSamlAuthorizationDecisionStatement(SamlRegressionTest.getAuthorizationDecisionStatement());
		while(Math.random() < ADD_SEED)
			binding.addSamlStatement(SamlRegressionTest.getStatement());
		while(Math.random() < ADD_SEED)
			binding.addSamlSubjectStatement(SamlRegressionTest.getSubjectStatement());
		if(Math.random() < ADD_SEED)
			binding.setSamlAdvice(SamlRegressionTest.getAdvice());
		binding.setSamlAssertionIDAttr(new LibAssertion.SamlAssertionIDAttr(getRandomString()));
		if(Math.random() < ADD_SEED)
			binding.setSamlConditions(SamlRegressionTest.getConditions());
		binding.setSamlIssueInstantAttr(new LibAssertion.SamlIssueInstantAttr(getRandomDateTime()));
		binding.setSamlIssuerAttr(new LibAssertion.SamlIssuerAttr(getRandomString()));
		binding.setSamlMajorVersionAttr(new LibAssertion.SamlMajorVersionAttr(getRandomInteger()));
		binding.setSamlMinorVersionAttr(new LibAssertion.SamlMinorVersionAttr(getRandomInteger()));
		if(Math.random() < ADD_SEED)
			binding.setDsSignature(DsRegressionTest.getSignature());

		binding.setLibInResponseToAttr(new LibAssertion.LibInResponseToAttr(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static LibAuthnRequestEnvelope getAuthnRequestEnvelope()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibAuthnRequestEnvelope binding = new LibAuthnRequestEnvelope();
		binding.setLibAuthnRequest(getAuthnRequest());
		binding.setLibProviderID(getProviderID());
		binding.setLibProviderName(new LibAuthnRequestEnvelope.LibProviderName(getRandomString()));
		binding.setLibAssertionConsumerServiceURL(new LibAuthnRequestEnvelope.LibAssertionConsumerServiceURL(getRandomString()));
		binding.setLibIDPList(getIDPList());
		binding.setLibIsPassive(new LibAuthnRequestEnvelope.LibIsPassive(getRandomBoolean()));

		while(Math.random() < ADD_SEED)
			binding.addLibExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static LibIDPList getIDPList()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibIDPList binding = new LibIDPList();
		binding.setLibIDPEntries(getIDPEntries());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public final static LibIDPEntry getIDPEntry()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibIDPEntry binding = new LibIDPEntry();
		binding.setLibProviderID(getProviderID());
		binding.setLibProviderName(new LibIDPEntry.LibProviderName(getRandomString()));
		binding.setLibLoc(new LibIDPEntry.LibLoc(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static LibIDPEntries getIDPEntries()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibIDPEntries binding = new LibIDPEntries();
		do
		binding.addLibIDPEntry(getIDPEntry());
		while(Math.random() < ADD_SEED);

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static LibGetComplete getGetComplete()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibGetComplete binding = new LibGetComplete();
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static LibAuthnResponseEnvelope getAuthnResponseEnvelope()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibAuthnResponseEnvelope binding = new LibAuthnResponseEnvelope();
		binding.setLibAuthnResponse(getAuthnResponse());
		binding.setLibAssertionConsumerServiceURL(new LibAuthnResponseEnvelope.LibAssertionConsumerServiceURL(getRandomString()));
		while(Math.random() < ADD_SEED)
			binding.addLibExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static LibRegisterNameIdentifierRequest getRegisterNameIdentifierRequest()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibRegisterNameIdentifierRequest binding = new LibRegisterNameIdentifierRequest();
		while(Math.random() < ADD_SEED)
			binding.addSamlpRespondWith(SamlpRegressionTest.getRespondWith());
		binding.setSamlpIssueInstantAttr(new LibRegisterNameIdentifierRequest.SamlpIssueInstantAttr(getRandomDateTime()));
		binding.setSamlpMajorVersionAttr(new LibRegisterNameIdentifierRequest.SamlpMajorVersionAttr(getRandomInteger()));
		binding.setSamlpMinorVersionAttr(new LibRegisterNameIdentifierRequest.SamlpMinorVersionAttr(getRandomInteger()));
		binding.setSamlpRequestIDAttr(new LibRegisterNameIdentifierRequest.SamlpRequestIDAttr(getRandomString()));
		binding.setDsSignature(DsRegressionTest.getSignature());

		while(Math.random() < ADD_SEED)
			binding.addLibExtension(getExtension());
		binding.setLibProviderID(getProviderID());
		binding.setLibIDPProvidedNameIdentifier(getIDPProvidedNameIdentifier());
		binding.setLibSPProvidedNameIdentifier(getSPProvidedNameIdentifier());
		binding.setLibOldProvidedNameIdentifier(getOldProvidedNameIdentifier());
		binding.setLibRelayState(getRelayState());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static LibIDPProvidedNameIdentifier getIDPProvidedNameIdentifier()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibIDPProvidedNameIdentifier binding = new LibIDPProvidedNameIdentifier();
		binding.setSamlFormatAttr(new LibIDPProvidedNameIdentifier.SamlFormatAttr(getRandomString()));
		binding.setSamlNameQualifierAttr(new LibIDPProvidedNameIdentifier.SamlNameQualifierAttr(getRandomString()));
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static LibSPProvidedNameIdentifier getSPProvidedNameIdentifier()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibSPProvidedNameIdentifier binding = new LibSPProvidedNameIdentifier();
		binding.setSamlFormatAttr(new LibSPProvidedNameIdentifier.SamlFormatAttr(getRandomString()));
		binding.setSamlNameQualifierAttr(new LibSPProvidedNameIdentifier.SamlNameQualifierAttr(getRandomString()));
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static LibOldProvidedNameIdentifier getOldProvidedNameIdentifier()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibOldProvidedNameIdentifier binding = new LibOldProvidedNameIdentifier();
		binding.setSamlFormatAttr(new LibOldProvidedNameIdentifier.SamlFormatAttr(getRandomString()));
		binding.setSamlNameQualifierAttr(new LibOldProvidedNameIdentifier.SamlNameQualifierAttr(getRandomString()));
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static LibRegisterNameIdentifierResponse getRegisterNameIdentifierResponse()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibRegisterNameIdentifierResponse binding = new LibRegisterNameIdentifierResponse();
		binding.setSamlpInResponseToAttr(new LibRegisterNameIdentifierResponse.SamlpInResponseToAttr(getRandomString()));
		binding.setSamlpIssueInstantAttr(new LibRegisterNameIdentifierResponse.SamlpIssueInstantAttr(getRandomDateTime()));
		binding.setSamlpMajorVersionAttr(new LibRegisterNameIdentifierResponse.SamlpMajorVersionAttr(getRandomInteger()));
		binding.setSamlpMinorVersionAttr(new LibRegisterNameIdentifierResponse.SamlpMinorVersionAttr(getRandomInteger()));
		binding.setSamlpRecipientAttr(new LibRegisterNameIdentifierResponse.SamlpRecipientAttr(getRandomString()));
		binding.setSamlpResponseIDAttr(new LibRegisterNameIdentifierResponse.SamlpResponseIDAttr(getRandomString()));
		binding.setDsSignature(DsRegressionTest.getSignature());

		while(Math.random() < ADD_SEED)
			binding.addLibExtension(getExtension());
		binding.setLibProviderID(getProviderID());
		binding.setSamlpStatus(SamlpRegressionTest.getStatus());
		binding.setLibRelayState(getRelayState());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static LibFederationTerminationNotification getFederationTerminationNotification()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibFederationTerminationNotification binding = new LibFederationTerminationNotification();
		while(Math.random() < ADD_SEED)
			binding.addSamlpRespondWith(SamlpRegressionTest.getRespondWith());
		binding.setSamlpIssueInstantAttr(new LibFederationTerminationNotification.SamlpIssueInstantAttr(getRandomDateTime()));
		binding.setSamlpMajorVersionAttr(new LibFederationTerminationNotification.SamlpMajorVersionAttr(getRandomInteger()));
		binding.setSamlpMinorVersionAttr(new LibFederationTerminationNotification.SamlpMinorVersionAttr(getRandomInteger()));
		binding.setSamlpRequestIDAttr(new LibFederationTerminationNotification.SamlpRequestIDAttr(getRandomString()));
		binding.setDsSignature(DsRegressionTest.getSignature());

		while(Math.random() < ADD_SEED)
			binding.addLibExtension(getExtension());
		binding.setLibProviderID(getProviderID());
		binding.setSamlNameIdentifier(getIDPProvidedNameIdentifier());
		binding.setLibConsentAttr(new LibConsentAttr(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static LibLogoutRequest getLogoutRequest()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibLogoutRequest binding = new LibLogoutRequest();
		while(Math.random() < ADD_SEED)
			binding.addSamlpRespondWith(SamlpRegressionTest.getRespondWith());
		binding.setSamlpIssueInstantAttr(new LibLogoutRequest.SamlpIssueInstantAttr(getRandomDateTime()));
		binding.setSamlpMajorVersionAttr(new LibLogoutRequest.SamlpMajorVersionAttr(getRandomInteger()));
		binding.setSamlpMinorVersionAttr(new LibLogoutRequest.SamlpMinorVersionAttr(getRandomInteger()));
		binding.setSamlpRequestIDAttr(new LibLogoutRequest.SamlpRequestIDAttr(getRandomString()));
		binding.setDsSignature(DsRegressionTest.getSignature());

		while(Math.random() < ADD_SEED)
			binding.addLibExtension(getExtension());
		binding.setLibProviderID(getProviderID());
		binding.setSamlNameIdentifier(getSPProvidedNameIdentifier());
		binding.setLibSessionIndex(new LibLogoutRequest.LibSessionIndex(getRandomString()));
		binding.setLibRelayState(getRelayState());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static LibLogoutResponse getLogoutResponse()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibLogoutResponse binding = new LibLogoutResponse();
		while(Math.random() < ADD_SEED)
			binding.addLibExtension(getExtension());
		binding.setLibProviderID(getProviderID());
		binding.setSamlpStatus(SamlpRegressionTest.getStatus());
		binding.setLibRelayState(getRelayState());

		binding.setSamlpInResponseToAttr(new LibLogoutResponse.SamlpInResponseToAttr(getRandomString()));
		binding.setSamlpIssueInstantAttr(new LibLogoutResponse.SamlpIssueInstantAttr(getRandomDateTime()));
		binding.setSamlpMajorVersionAttr(new LibLogoutResponse.SamlpMajorVersionAttr(getRandomInteger()));
		binding.setSamlpMinorVersionAttr(new LibLogoutResponse.SamlpMinorVersionAttr(getRandomInteger()));
		binding.setSamlpRecipientAttr(new LibLogoutResponse.SamlpRecipientAttr(getRandomString()));
		binding.setSamlpResponseIDAttr(new LibLogoutResponse.SamlpResponseIDAttr(getRandomString()));
		binding.setDsSignature(DsRegressionTest.getSignature());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	// NOTE: There is not element that encompases this type directly.
	public static final ILibAuthenticationStatementType getAuthenticationStatement()
	{
		ILibAuthenticationStatementType binding = new ILibAuthenticationStatementType()
		{
			protected ISamlStatementAbstractType inherits()
			{
				return null;
			}
		};
		binding.setSamlAuthenticationInstantAttr(new ILibAuthenticationStatementType.SamlAuthenticationInstantAttr(getRandomDateTime()));
		binding.setSamlAuthenticationMethodAttr(new ILibAuthenticationStatementType.SamlAuthenticationMethodAttr(getRandomString()));
		while(Math.random() < ADD_SEED)
			binding.addSamlAuthorityBinding(SamlRegressionTest.getAuthorityBinding());
		binding.setSamlSubject(SamlRegressionTest.getSubject());
		binding.setSamlSubjectLocality(SamlRegressionTest.getSubjectLocality());

		ILibAuthenticationStatementType.LibAuthnContext authnContext = new ILibAuthenticationStatementType.LibAuthnContext();
//		authnContext.setLibAuthnContextClassRef(new LibAuthenticationStatementType.AuthnContext.AuthnContextClassRef(getRandomString()));
		if(Math.random() < CHOICE_SEED)
			authnContext.setAcAuthenticationContextStatement(AcRegressionTest.getAuthenticationContextStatement());
//		else
		//			authnContext.setLibAuthnContextStatementRef(new LibAuthenticationStatementType.AuthnContext.AuthnContextStatementRef(getRandomString()));
		binding.setLibAuthnContext(authnContext);

		return binding;
	}

	public static LibExtension getExtension()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		LibExtension binding = new LibExtension();
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
