package org.safris.xml.toolkit.test.binding.regression;

import _0_assertion.oasis_names_tc_saml_1.ISamlSubjectStatementAbstractType;
import _0_assertion.oasis_names_tc_saml_1.SamlAction;
import _0_assertion.oasis_names_tc_saml_1.SamlAdvice;
import _0_assertion.oasis_names_tc_saml_1.SamlAssertion;
import _0_assertion.oasis_names_tc_saml_1.SamlAssertionIDReference;
import _0_assertion.oasis_names_tc_saml_1.SamlAttribute;
import _0_assertion.oasis_names_tc_saml_1.SamlAttributeDesignator;
import _0_assertion.oasis_names_tc_saml_1.SamlAttributeStatement;
import _0_assertion.oasis_names_tc_saml_1.SamlAttributeValue;
import _0_assertion.oasis_names_tc_saml_1.SamlAudience;
import _0_assertion.oasis_names_tc_saml_1.SamlAudienceRestrictionCondition;
import _0_assertion.oasis_names_tc_saml_1.SamlAuthenticationStatement;
import _0_assertion.oasis_names_tc_saml_1.SamlAuthorityBinding;
import _0_assertion.oasis_names_tc_saml_1.SamlAuthorizationDecisionStatement;
import _0_assertion.oasis_names_tc_saml_1.SamlConditions;
import _0_assertion.oasis_names_tc_saml_1.SamlConfirmationMethod;
import _0_assertion.oasis_names_tc_saml_1.SamlEvidence;
import _0_assertion.oasis_names_tc_saml_1.SamlNameIdentifier;
import _0_assertion.oasis_names_tc_saml_1.SamlSubject;
import _0_assertion.oasis_names_tc_saml_1.SamlSubjectConfirmation;
import _0_assertion.oasis_names_tc_saml_1.SamlSubjectConfirmationData;
import _0_assertion.oasis_names_tc_saml_1.SamlSubjectLocality;
import org.safris.xml.toolkit.test.binding.regression.DsRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.RegressionTest;
import org.safris.xml.toolkit.test.binding.regression.SamlRegressionTest;

public class SamlRegressionTest extends RegressionTest
{
	private static final String namespaceURI = "urn:oasis:names:tc:Saml:1.0:assertion";

	public static String getNamespaceURI()
	{
		return namespaceURI;
	}

	private static RegressionTest instance = new SamlRegressionTest();

	public static void main(String[] args)
	{
		getAssertion();
		getAttributeDesignator();
	}

	public static SamlAssertionIDReference getAssertionIDReference()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlAssertionIDReference binding = new SamlAssertionIDReference();
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlAssertion getAssertion()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlAssertion binding = new SamlAssertion();
		binding.setSamlAdvice(getAdvice());
		binding.setSamlAssertionIDAttr(new SamlAssertion.SamlAssertionIDAttr(getRandomString()));
		while(Math.random() < ADD_SEED)
			binding.addSamlAttributeStatement(getAttributeStatement());
		while(Math.random() < ADD_SEED)
			binding.addSamlAuthenticationStatement(getAuthenticationStatement());
		while(Math.random() < ADD_SEED)
			binding.addSamlAuthorizationDecisionStatement(getAuthorizationDecisionStatement());
		binding.setSamlConditions(getConditions());
		do
		binding.addSamlStatement(getStatement());
		while(Math.random() < ADD_SEED);
		while(Math.random() < ADD_SEED)
			binding.addSamlSubjectStatement(getSubjectStatement());
		binding.setSamlIssueInstantAttr(new SamlAssertion.SamlIssueInstantAttr(getRandomDateTime()));
		binding.setSamlIssuerAttr(new SamlAssertion.SamlIssuerAttr(getRandomString()));
		binding.setSamlMajorVersionAttr(new SamlAssertion.SamlMajorVersionAttr(getRandomInteger()));
		binding.setSamlMinorVersionAttr(new SamlAssertion.SamlMinorVersionAttr(getRandomInteger()));
		binding.setDsSignature(DsRegressionTest.getSignature());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlConditions getConditions()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlConditions binding = new SamlConditions();
		while(Math.random() < ADD_SEED)
			binding.addSamlAudienceRestrictionCondition(getAudienceRestrictionCondition());
		while(Math.random() < ADD_SEED)
			binding.addSamlCondition(getCondition());
		binding.setSamlNotBeforeAttr(new SamlConditions.SamlNotBeforeAttr(getRandomDateTime()));
		binding.setSamlNotOnOrAfterAttr(new SamlConditions.SamlNotOnOrAfterAttr(getRandomDateTime()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	// FIXME: Condition is a direct element of an abstract complexType.
	public static SamlAudienceRestrictionCondition getCondition()
	{
		return getAudienceRestrictionCondition();
	}

	public static SamlAudienceRestrictionCondition getAudienceRestrictionCondition()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlAudienceRestrictionCondition binding = new SamlAudienceRestrictionCondition();
		do
		binding.addSamlAudience(getAudience());
		while(Math.random() < ADD_SEED);

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlAudience getAudience()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlAudience binding = new SamlAudience();
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlAdvice getAdvice()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlAdvice binding = new SamlAdvice();
		while(Math.random() < ADD_SEED)
			binding.addSamlAssertion(getAssertion());
		while(Math.random() < ADD_SEED)
			binding.addSamlAssertionIDReference(getAssertionIDReference());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	// FIXME: Statement is a direct element of an abstract complexType.
	public static ISamlSubjectStatementAbstractType getStatement()
	{
		return getAuthenticationStatement();
	}

	// FIXME: SubjectStatement is a direct element of an abstract complexType.
	public static ISamlSubjectStatementAbstractType getSubjectStatement()
	{
		return getAttributeStatement();
	}

	public static SamlSubject getSubject()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlSubject binding = new SamlSubject();
		binding.setSamlNameIdentifier(getNameIdentifier());
		binding.setSamlSubjectConfirmation(getSubjectConfirmation());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlNameIdentifier getNameIdentifier()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlNameIdentifier binding = new SamlNameIdentifier();
		binding.setSamlFormatAttr(new SamlNameIdentifier.SamlFormatAttr(getRandomString()));
		binding.setSamlNameQualifierAttr(new SamlNameIdentifier.SamlNameQualifierAttr(getRandomString()));
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlSubjectConfirmation getSubjectConfirmation()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlSubjectConfirmation binding = new SamlSubjectConfirmation();
		do
		binding.addSamlConfirmationMethod(getConfirmationMethod());
		while(Math.random() < ADD_SEED);
		binding.setDsKeyInfo(DsRegressionTest.getKeyInfo());
		binding.setSamlSubjectConfirmationData(getSubjectConfirmationData());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlSubjectConfirmationData getSubjectConfirmationData()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlSubjectConfirmationData binding = new SamlSubjectConfirmationData();
//		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlConfirmationMethod getConfirmationMethod()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlConfirmationMethod binding = new SamlConfirmationMethod();
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlAuthenticationStatement getAuthenticationStatement()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlAuthenticationStatement binding = new SamlAuthenticationStatement();
		binding.setSamlAuthenticationInstantAttr(new SamlAuthenticationStatement.SamlAuthenticationInstantAttr(getRandomDateTime()));
		binding.setSamlAuthenticationMethodAttr(new SamlAuthenticationStatement.SamlAuthenticationMethodAttr(getRandomString()));
		while(Math.random() < ADD_SEED)
			binding.addSamlAuthorityBinding(getAuthorityBinding());
		binding.setSamlSubject(getSubject());
		binding.setSamlSubjectLocality(getSubjectLocality());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlSubjectLocality getSubjectLocality()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlSubjectLocality binding = new SamlSubjectLocality();
		binding.setSamlDNSAddressAttr(new SamlSubjectLocality.SamlDNSAddressAttr(getRandomString()));
		binding.setSamlIPAddressAttr(new SamlSubjectLocality.SamlIPAddressAttr(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlAuthorityBinding getAuthorityBinding()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlAuthorityBinding binding = new SamlAuthorityBinding();
		binding.setSamlAuthorityKindAttr(new SamlAuthorityBinding.SamlAuthorityKindAttr(getRandomQName()));
		binding.setSamlBindingAttr(new SamlAuthorityBinding.SamlBindingAttr(getRandomString()));
		binding.setSamlLocationAttr(new SamlAuthorityBinding.SamlLocationAttr(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlAuthorizationDecisionStatement getAuthorizationDecisionStatement()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlAuthorizationDecisionStatement binding = new SamlAuthorizationDecisionStatement();
		do
		binding.addSamlAction(getAction());
		while(Math.random() < ADD_SEED);
		double random = Math.random();
		if(random < 1 / 3)
			binding.setSamlDecisionAttr(new SamlAuthorizationDecisionStatement.SamlDecisionAttr(SamlAuthorizationDecisionStatement.SamlDecisionAttr.INDETERMINATE));
		else if(random < 2 / 3)
			binding.setSamlDecisionAttr(new SamlAuthorizationDecisionStatement.SamlDecisionAttr(SamlAuthorizationDecisionStatement.SamlDecisionAttr.DENY));
		else
			binding.setSamlDecisionAttr(new SamlAuthorizationDecisionStatement.SamlDecisionAttr(SamlAuthorizationDecisionStatement.SamlDecisionAttr.PERMIT));
		if(Math.random() < ADD_SEED)
			binding.setSamlEvidence(getEvidence());
		binding.setSamlResourceAttr(new SamlAuthorizationDecisionStatement.SamlResourceAttr(getRandomString()));
		binding.setSamlSubject(getSubject());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlAction getAction()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlAction binding = new SamlAction();
		binding.setSamlNamespaceAttr(new SamlAction.SamlNamespaceAttr(getRandomString()));
		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlEvidence getEvidence()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlEvidence binding = new SamlEvidence();
		do
		binding.addSamlAssertion(getAssertion());
		while(Math.random() < ADD_SEED);
		do
		binding.addSamlAssertionIDReference(getAssertionIDReference());
		while(Math.random() < ADD_SEED);

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlAttributeStatement getAttributeStatement()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlAttributeStatement binding = new SamlAttributeStatement();
		do
		binding.addSamlAttribute(getAttribute());
		while(Math.random() < ADD_SEED);
		binding.setSamlSubject(getSubject());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlAttributeDesignator getAttributeDesignator()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlAttributeDesignator binding = new SamlAttributeDesignator();
		binding.setSamlAttributeNameAttr(new SamlAttributeDesignator.SamlAttributeNameAttr(getRandomString()));
		binding.setSamlAttributeNamespaceAttr(new SamlAttributeDesignator.SamlAttributeNamespaceAttr(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlAttribute getAttribute()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlAttribute binding = new SamlAttribute();
		binding.setSamlAttributeNameAttr(new SamlAttribute.SamlAttributeNameAttr(getRandomString()));
		binding.setSamlAttributeNamespaceAttr(new SamlAttribute.SamlAttributeNamespaceAttr(getRandomString()));
		do
		binding.addSamlAttributeValue(getAttributeValue());
		while(Math.random() < ADD_SEED);

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static SamlAttributeValue getAttributeValue()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		SamlAttributeValue binding = new SamlAttributeValue();
//		binding.setTEXT(getRandomString());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}
}
