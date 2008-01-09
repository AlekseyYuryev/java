package org.safris.xml.toolkit.test.binding.regression;

import liberty_ac_2003_08.AcActivationLimit;
import liberty_ac_2003_08.AcActivationLimitDuration;
import liberty_ac_2003_08.AcActivationLimitSession;
import liberty_ac_2003_08.AcActivationLimitUsages;
import liberty_ac_2003_08.AcActivationPin;
import liberty_ac_2003_08.AcAsymmetricDecryption;
import liberty_ac_2003_08.AcAsymmetricKeyAgreement;
import liberty_ac_2003_08.AcAuthenticationContextStatement;
import liberty_ac_2003_08.AcAuthenticationMethod;
import liberty_ac_2003_08.AcAuthenticator;
import liberty_ac_2003_08.AcAuthenticatorTransportProtocol;
import liberty_ac_2003_08.AcDeactivationCallCenter;
import liberty_ac_2003_08.AcDigSig;
import liberty_ac_2003_08.AcExtension;
import liberty_ac_2003_08.AcGeneration;
import liberty_ac_2003_08.AcGoverningAgreementRef;
import liberty_ac_2003_08.AcGoverningAgreements;
import liberty_ac_2003_08.AcHTTP;
import liberty_ac_2003_08.AcIPAddress;
import liberty_ac_2003_08.AcIPSec;
import liberty_ac_2003_08.AcIdentification;
import liberty_ac_2003_08.AcKeyActivation;
import liberty_ac_2003_08.AcKeySharing;
import liberty_ac_2003_08.AcKeyStorage;
import liberty_ac_2003_08.AcLength;
import liberty_ac_2003_08.AcMobileNetworkEndToEndEncryption;
import liberty_ac_2003_08.AcMobileNetworkNoEncryption;
import liberty_ac_2003_08.AcMobileNetworkRadioEncryption;
import liberty_ac_2003_08.AcOperationalProtection;
import liberty_ac_2003_08.AcPassword;
import liberty_ac_2003_08.AcPhysicalVerification;
import liberty_ac_2003_08.AcPreviousSession;
import liberty_ac_2003_08.AcPrincipalAuthenticationMechanism;
import liberty_ac_2003_08.AcPrivateKeyProtection;
import liberty_ac_2003_08.AcResumeSession;
import liberty_ac_2003_08.AcSSL;
import liberty_ac_2003_08.AcSecurityAudit;
import liberty_ac_2003_08.AcSharedSecretChallengeResponse;
import liberty_ac_2003_08.AcSharedSecretDynamicPlaintext;
import liberty_ac_2003_08.AcSmartcard;
import liberty_ac_2003_08.AcSwitchAudit;
import liberty_ac_2003_08.AcTechnicalProtection;
import liberty_ac_2003_08.AcTimeSyncToken;
import liberty_ac_2003_08.AcToken;
import liberty_ac_2003_08.AcWTLS;
import liberty_ac_2003_08.AcWrittenConsent;
import liberty_ac_2003_08.AcZeroKnowledge;
import org.safris.xml.toolkit.test.binding.regression.AcRegressionTest;
import org.safris.xml.toolkit.test.binding.regression.RegressionTest;

public class AcRegressionTest extends RegressionTest
{
	private static final String namespaceURI = "urn:liberty:ac:2003-08";

	public static String getNamespaceURI()
	{
		return namespaceURI;
	}

	private static RegressionTest instance = new AcRegressionTest();

	public static void main(String[] args)
	{
		getAuthenticationContextStatement();
	}

	public static AcAuthenticationContextStatement getAuthenticationContextStatement()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcAuthenticationContextStatement binding = new AcAuthenticationContextStatement();
		binding.setAcIdentification(getIdentification());
		binding.setAcTechnicalProtection(getTechnicalProtection());
		binding.setAcOperationalProtection(getOperationalProtection());
		binding.setAcAuthenticationMethod(getAuthenticationMethod());
		binding.setAcGoverningAgreements(getGoverningAgreements());
		binding.addAcExtension(getExtension());
		binding.setAcIDAttr(new AcAuthenticationContextStatement.AcIDAttr(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcIdentification getIdentification()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcIdentification binding = new AcIdentification();
		binding.setAcPhysicalVerification(getPhysicalVerification());
		binding.setAcWrittenConsent(getWrittenConsent());
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());
		double random = Math.random();
		if(random < 1 / 3)
			binding.setAcNymAttr(new AcIdentification.AcNymAttr(AcIdentification.AcNymAttr.ANONYMITY));
		else if(random < 2 / 3)
			binding.setAcNymAttr(new AcIdentification.AcNymAttr(AcIdentification.AcNymAttr.PSEUDONYMITY));
		else
			binding.setAcNymAttr(new AcIdentification.AcNymAttr(AcIdentification.AcNymAttr.VERINYMITY));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcPhysicalVerification getPhysicalVerification()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcPhysicalVerification binding = new AcPhysicalVerification();
		if(Math.random() < 0.5)
			binding.setAcCredentialLevelAttr(new AcPhysicalVerification.AcCredentialLevelAttr(AcPhysicalVerification.AcCredentialLevelAttr.PRIMARY));
		else
			binding.setAcCredentialLevelAttr(new AcPhysicalVerification.AcCredentialLevelAttr(AcPhysicalVerification.AcCredentialLevelAttr.SECONDARY));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcWrittenConsent getWrittenConsent()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcWrittenConsent binding = new AcWrittenConsent();
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcTechnicalProtection getTechnicalProtection()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcTechnicalProtection binding = new AcTechnicalProtection();
		binding.setAcPrivateKeyProtection(getPrivateKeyProtection());
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcPrivateKeyProtection getPrivateKeyProtection()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcPrivateKeyProtection binding = new AcPrivateKeyProtection();
		binding.setAcKeyActivation(getKeyActivation());
		binding.setAcKeyStorage(getKeyStorage());
		binding.setAcKeySharing(getKeySharing());
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcKeyActivation getKeyActivation()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcKeyActivation binding = new AcKeyActivation();
		binding.setAcActivationPin(getActivationPin());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcActivationPin getActivationPin()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcActivationPin binding = new AcActivationPin();
		binding.setAcActivationLimit(getActivationLimit());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcActivationLimit getActivationLimit()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcActivationLimit binding = new AcActivationLimit();
		double random = Math.random();
		if(random < 1 / 3)
			binding.setAcActivationLimitDuration(getActivationLimitDuration());
		else if(random < 2 / 3)
			binding.setAcActivationLimitSession(new AcActivationLimitSession());
		else
			binding.setAcActivationLimitUsages(getActivationLimitUsages());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcActivationLimitUsages getActivationLimitUsages()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcActivationLimitUsages binding = new AcActivationLimitUsages();
		binding.setAcNumberAttr(new AcActivationLimitUsages.AcNumberAttr(getRandomInteger()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcActivationLimitDuration getActivationLimitDuration()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcActivationLimitDuration binding = new AcActivationLimitDuration();
		binding.setAcDurationAttr(new AcActivationLimitDuration.AcDurationAttr(getRandomDuration()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcKeyStorage getKeyStorage()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcKeyStorage binding = new AcKeyStorage();
		double random = Math.random();
		if(random < 1 / 5)
			binding.setAcMediumAttr(new AcKeyStorage.AcMediumAttr(AcKeyStorage.AcMediumAttr.MEMORY));
		else if(random < 2 / 5)
			binding.setAcMediumAttr(new AcKeyStorage.AcMediumAttr(AcKeyStorage.AcMediumAttr.MOBILEAUTHCARD));
		else if(random < 3 / 5)
			binding.setAcMediumAttr(new AcKeyStorage.AcMediumAttr(AcKeyStorage.AcMediumAttr.MOBILEDEVICE));
		else if(random < 4 / 5)
			binding.setAcMediumAttr(new AcKeyStorage.AcMediumAttr(AcKeyStorage.AcMediumAttr.SMARTCARD));
		else
			binding.setAcMediumAttr(new AcKeyStorage.AcMediumAttr(AcKeyStorage.AcMediumAttr.TOKEN));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcKeySharing getKeySharing()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcKeySharing binding = new AcKeySharing();
		binding.setAcSharingAttr(new AcKeySharing.AcSharingAttr(getRandomBoolean()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcPassword getPassword()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcPassword binding = new AcPassword();
		binding.setAcLength(getLength());
		binding.setAcGeneration(getGeneration());
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcToken getToken()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcToken binding = new AcToken();
		binding.setAcTimeSyncToken(getTimeSyncToken());
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcTimeSyncToken getTimeSyncToken()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcTimeSyncToken binding = new AcTimeSyncToken();
		if(Math.random() < 0.5)
			binding.setAcDeviceTypeAttr(new AcTimeSyncToken.AcDeviceTypeAttr(AcTimeSyncToken.AcDeviceTypeAttr.HARDWARE));
		else
			binding.setAcDeviceTypeAttr(new AcTimeSyncToken.AcDeviceTypeAttr(AcTimeSyncToken.AcDeviceTypeAttr.SOFTWARE));
		binding.setAcSeedLengthAttr(new AcTimeSyncToken.AcSeedLengthAttr(getRandomInteger()));
		if(Math.random() < 0.5)
			binding.setAcDeviceInHandAttr(new AcTimeSyncToken.AcDeviceInHandAttr(AcTimeSyncToken.AcDeviceInHandAttr.FALSE));
		else
			binding.setAcDeviceInHandAttr(new AcTimeSyncToken.AcDeviceInHandAttr(AcTimeSyncToken.AcDeviceInHandAttr.TRUE));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcSmartcard getSmartcard()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcSmartcard binding = new AcSmartcard();
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcLength getLength()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcLength binding = new AcLength();
		binding.setAcMaxAttr(new AcLength.AcMaxAttr(getRandomInteger()));
		binding.setAcMinAttr(new AcLength.AcMinAttr(getRandomInteger()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcGeneration getGeneration()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcGeneration binding = new AcGeneration();
		if(Math.random() < 0.5)
			binding.setAcMechanismAttr(new AcGeneration.AcMechanismAttr(AcGeneration.AcMechanismAttr.AUTOMATIC));
		else
			binding.setAcMechanismAttr(new AcGeneration.AcMechanismAttr(AcGeneration.AcMechanismAttr.PRINCIPALCHOSEN));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcAuthenticationMethod getAuthenticationMethod()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcAuthenticationMethod binding = new AcAuthenticationMethod();
		binding.setAcPrincipalAuthenticationMechanism(getPrincipalAuthenticationMechanism());
		binding.setAcAuthenticator(getAuthenticator());
		binding.setAcAuthenticatorTransportProtocol(getAuthenticatorTransportProtocol());
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcPrincipalAuthenticationMechanism getPrincipalAuthenticationMechanism()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcPrincipalAuthenticationMechanism binding = new AcPrincipalAuthenticationMechanism();
		double random = Math.random();
		if(random < 1 / 3)
			binding.setAcPassword(getPassword());
		else if(random < 2 / 3)
			binding.setAcToken(getToken());
		else
			binding.setAcSmartcard(getSmartcard());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcAuthenticator getAuthenticator()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcAuthenticator binding = new AcAuthenticator();
		double random = Math.random();
		if(random < 1 / 11)
			binding.setAcPreviousSession(getPreviousSession());
		else if(random < 2 / 11)
			binding.setAcResumeSession(getResumeSession());
		else if(random < 3 / 11)
			binding.setAcDigSig(getDigSig());
		else if(random < 4 / 11)
			binding.setAcPassword(getPassword());
		else if(random < 5 / 11)
			binding.setAcZeroKnowledge(getZeroKnowledge());
		else if(random < 6 / 11)
			binding.setAcSharedSecretChallengeResponse(getSharedSecretChallengeResponse());
		else if(random < 7 / 11)
			binding.setAcSharedSecretDynamicPlaintext(getSharedSecretDynamicPlaintext());
		else if(random < 8 / 11)
			binding.setAcIPAddress(getIPAddress());
		else if(random < 9 / 11)
			binding.setAcAsymmetricDecryption(getAsymmetricDecryption());
		else if(random < 10 / 11)
			binding.setAcAsymmetricKeyAgreement(getAsymmetricKeyAgreement());
		else
			do
			binding.addAcExtension(getExtension());
			while(Math.random() < ADD_SEED);

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcResumeSession getResumeSession()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcResumeSession binding = new AcResumeSession();
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcPreviousSession getPreviousSession()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcPreviousSession binding = new AcPreviousSession();
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcZeroKnowledge getZeroKnowledge()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcZeroKnowledge binding = new AcZeroKnowledge();
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcSharedSecretDynamicPlaintext getSharedSecretDynamicPlaintext()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcSharedSecretDynamicPlaintext binding = new AcSharedSecretDynamicPlaintext();
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcSharedSecretChallengeResponse getSharedSecretChallengeResponse()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcSharedSecretChallengeResponse binding = new AcSharedSecretChallengeResponse();
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcDigSig getDigSig()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcDigSig binding = new AcDigSig();
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcIPAddress getIPAddress()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcIPAddress binding = new AcIPAddress();
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcAsymmetricDecryption getAsymmetricDecryption()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcAsymmetricDecryption binding = new AcAsymmetricDecryption();
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcAsymmetricKeyAgreement getAsymmetricKeyAgreement()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcAsymmetricKeyAgreement binding = new AcAsymmetricKeyAgreement();
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcAuthenticatorTransportProtocol getAuthenticatorTransportProtocol()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcAuthenticatorTransportProtocol binding = new AcAuthenticatorTransportProtocol();
		double random = Math.random();
		if(random < 1 / 8)
			binding.setAcHTTP(getHTTP());
		else if(random < 2 / 8)
			binding.setAcSSL(getSSL());
		else if(random < 3 / 8)
			binding.setAcMobileNetworkEndToEndEncryption(getMobileNetworkEndToEndEncryption());
		else if(random < 4 / 8)
			binding.setAcMobileNetworkNoEncryption(getMobileNetworkNoEncryption());
		else if(random < 5 / 8)
			binding.setAcMobileNetworkRadioEncryption(getMobileNetworkRadioEncryption());
		else if(random < 6 / 8)
			binding.setAcWTLS(getWTLS());
		else if(random < 7 / 8)
			binding.setAcIPSec(getIPSec());
		else
			do
			binding.addAcExtension(getExtension());
			while(Math.random() < ADD_SEED);

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcMobileNetworkEndToEndEncryption getMobileNetworkEndToEndEncryption()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcMobileNetworkEndToEndEncryption binding = new AcMobileNetworkEndToEndEncryption();
		while(Math.random() < ADD_SEED)
		{
			binding.addAcExtension(getExtension());
		}

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcMobileNetworkNoEncryption getMobileNetworkNoEncryption()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcMobileNetworkNoEncryption binding = new AcMobileNetworkNoEncryption();
		while(Math.random() < ADD_SEED)
		{
			binding.addAcExtension(getExtension());
		}

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcMobileNetworkRadioEncryption getMobileNetworkRadioEncryption()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcMobileNetworkRadioEncryption binding = new AcMobileNetworkRadioEncryption();
		while(Math.random() < ADD_SEED)
		{
			binding.addAcExtension(getExtension());
		}

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcHTTP getHTTP()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcHTTP binding = new AcHTTP();
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcIPSec getIPSec()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcIPSec binding = new AcIPSec();
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcWTLS getWTLS()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcWTLS binding = new AcWTLS();
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcSSL getSSL()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcSSL binding = new AcSSL();
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcOperationalProtection getOperationalProtection()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcOperationalProtection binding = new AcOperationalProtection();
		binding.setAcSecurityAudit(getSecurityAudit());
		binding.setAcDeactivationCallCenter(getDeactivationCallCenter());
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcSecurityAudit getSecurityAudit()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcSecurityAudit binding = new AcSecurityAudit();
		binding.setAcSwitchAudit(getSwitchAudit());
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcSwitchAudit getSwitchAudit()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcSwitchAudit binding = new AcSwitchAudit();
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcDeactivationCallCenter getDeactivationCallCenter()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcDeactivationCallCenter binding = new AcDeactivationCallCenter();
		while(Math.random() < ADD_SEED)
			binding.addAcExtension(getExtension());

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcGoverningAgreements getGoverningAgreements()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcGoverningAgreements binding = new AcGoverningAgreements();
		do
		binding.addAcGoverningAgreementRef(getGoverningAgreementRef());
		while(Math.random() < ADD_SEED);


		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcGoverningAgreementRef getGoverningAgreementRef()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcGoverningAgreementRef binding = new AcGoverningAgreementRef();
		binding.setAcGoverningAgreementRefAttr(new AcGoverningAgreementRef.AcGoverningAgreementRefAttr(getRandomString()));

		if(verifiable)
		{
			verify(binding);
			setVerifiable(true);
		}
		return binding;
	}

	public static AcExtension getExtension()
	{
		boolean verifiable = isVerifiable();
		if(verifiable)
			setVerifiable(false);

		AcExtension binding = new AcExtension();
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
