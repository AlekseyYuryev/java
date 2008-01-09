package org.safris.xml.toolkit.test.binding.regression;

import _0_protocol.oasis_names_tc_saml_1.ISamlpQueryAbstractType;
import _0_protocol.oasis_names_tc_saml_1.ISamlpSubjectQueryAbstractType;
import _0_protocol.oasis_names_tc_saml_1.SamlpAssertionArtifact;
import _0_protocol.oasis_names_tc_saml_1.SamlpAttributeQuery;
import _0_protocol.oasis_names_tc_saml_1.SamlpAuthenticationQuery;
import _0_protocol.oasis_names_tc_saml_1.SamlpAuthorizationDecisionQuery;
import _0_protocol.oasis_names_tc_saml_1.SamlpRequest;
import _0_protocol.oasis_names_tc_saml_1.SamlpRespondWith;
import _0_protocol.oasis_names_tc_saml_1.SamlpResponse;
import _0_protocol.oasis_names_tc_saml_1.SamlpStatus;
import _0_protocol.oasis_names_tc_saml_1.SamlpStatusCode;
import _0_protocol.oasis_names_tc_saml_1.SamlpStatusDetail;
import _0_protocol.oasis_names_tc_saml_1.SamlpStatusMessage;
import org.safris.xml.toolkit.test.binding.regression.DsRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.RegressionTest;
import org.safris.xml.toolkit.test.binding.regression.SamlRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.SamlpRegressionTest;

public class SamlpRegressionTest extends RegressionTest
{
	private static final String namespaceURI = "urn:oasis:names:tc:Saml:1.0:protocol";

	public static String getNamespaceURI()
	{
		return namespaceURI;
	}

	private static RegressionTest instance = new SamlpRegressionTest();

	public static void main(String[] args)
	{
		getRequest();
		getResponse();
	}

	public static SamlpRespondWith getRespondWith()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlpRespondWith binding = new SamlpRespondWith();
		binding.setTEXT(getRandomQName());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlpRequest getRequest()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlpRequest binding = new SamlpRequest();
		double random = Math.random();
		do
		binding.addSamlpRespondWith(getRespondWith());
		while(Math.random() < ADD_SEED);
		binding.setSamlpIssueInstantAttr(new SamlpRequest.SamlpIssueInstantAttr(getRandomDateTime()));
		binding.setSamlpMajorVersionAttr(new SamlpRequest.SamlpMajorVersionAttr(getRandomInteger()));
		binding.setSamlpMinorVersionAttr(new SamlpRequest.SamlpMinorVersionAttr(getRandomInteger()));
		binding.setSamlpRequestIDAttr(new SamlpRequest.SamlpRequestIDAttr(getRandomString()));
		binding.setDsSignature(DsRegressionTest.getSignature());
		if(random < 1 / 7)
			do
			binding.addSamlpAssertionArtifact(getAssertionArtifact());
			while(Math.random() < ADD_SEED);
		else if(random < 2 / 7)
			do
			binding.addSamlAssertionIDReference(SamlRegressionTest.getAssertionIDReference());
			while(Math.random() < ADD_SEED);
		else if(random < 3 / 7)
			binding.setSamlpAttributeQuery(getAttributeQuery());
		else if(random < 4 / 7)
			binding.setSamlpAuthenticationQuery(getAuthenticationQuery());
		else if(random < 5 / 7)
			binding.setSamlpAuthorizationDecisionQuery(getAuthorizationDecisionQuery());
		else if(random < 6 / 7)
			binding.setSamlpQuery(getQuery());
		else
			binding.setSamlpSubjectQuery(getSubjectQuery());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlpAssertionArtifact getAssertionArtifact()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlpAssertionArtifact binding = new SamlpAssertionArtifact();
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	// FIXME: Query is a direct element of an abstract complexType.
	public static ISamlpQueryAbstractType getQuery()
	{
		return getAttributeQuery();
	}

	// FIXME: SubjectQuery is a direct element of an abstract complexType.
	public static ISamlpSubjectQueryAbstractType getSubjectQuery()
	{
		return getAttributeQuery();
	}

	public static SamlpAuthenticationQuery getAuthenticationQuery()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlpAuthenticationQuery binding = new SamlpAuthenticationQuery();
		binding.setSamlpAuthenticationMethodAttr(new SamlpAuthenticationQuery.SamlpAuthenticationMethodAttr(getRandomString()));
		binding.setSamlSubject(SamlRegressionTest.getSubject());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlpAttributeQuery getAttributeQuery()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlpAttributeQuery binding = new SamlpAttributeQuery();
		while(Math.random() < ADD_SEED)
			binding.addSamlAttributeDesignator(SamlRegressionTest.getAttributeDesignator());

		binding.setSamlpResourceAttr(new SamlpAttributeQuery.SamlpResourceAttr(getRandomString()));
		binding.setSamlSubject(SamlRegressionTest.getSubject());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlpAuthorizationDecisionQuery getAuthorizationDecisionQuery()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlpAuthorizationDecisionQuery binding = new SamlpAuthorizationDecisionQuery();
		do
		binding.addSamlAction(SamlRegressionTest.getAction());
		while(Math.random() < ADD_SEED);
		binding.setSamlpResourceAttr(new SamlpAuthorizationDecisionQuery.SamlpResourceAttr(getRandomString()));
		binding.setSamlSubject(SamlRegressionTest.getSubject());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlpResponse getResponse()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlpResponse binding = new SamlpResponse();
		while(Math.random() < ADD_SEED)
			binding.addSamlAssertion(SamlRegressionTest.getAssertion());
		binding.setSamlpInResponseToAttr(new SamlpResponse.SamlpInResponseToAttr(getRandomString()));
		binding.setSamlpIssueInstantAttr(new SamlpResponse.SamlpIssueInstantAttr(getRandomDateTime()));
		binding.setSamlpMajorVersionAttr(new SamlpResponse.SamlpMajorVersionAttr(getRandomInteger()));
		binding.setSamlpMinorVersionAttr(new SamlpResponse.SamlpMinorVersionAttr(getRandomInteger()));
		binding.setSamlpRecipientAttr(new SamlpResponse.SamlpRecipientAttr(getRandomString()));
		binding.setSamlpResponseIDAttr(new SamlpResponse.SamlpResponseIDAttr(getRandomString()));
		binding.setDsSignature(DsRegressionTest.getSignature());
		binding.setSamlpStatus(getStatus());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlpStatus getStatus()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlpStatus binding = new SamlpStatus();
		binding.setSamlpStatusCode(getStatusCode());
		binding.setSamlpStatusDetail(getStatusDetail());
		binding.setSamlpStatusMessage(getStatusMessage());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlpStatusCode getStatusCode()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlpStatusCode binding = new SamlpStatusCode();

		// NOTE: We do not want a continuous recursion.
		if(Math.random() < RECURSION_SEED)
			binding.setSamlpStatusCode(getStatusCode());
		binding.setSamlpValueAttr(new SamlpStatusCode.SamlpValueAttr(getRandomQName()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlpStatusMessage getStatusMessage()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlpStatusMessage binding = new SamlpStatusMessage();
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlpStatusDetail getStatusDetail()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlpStatusDetail binding = new SamlpStatusDetail();
		while(Math.random() < ADD_SEED)
			binding.addANY(instance.getAny());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}
}
