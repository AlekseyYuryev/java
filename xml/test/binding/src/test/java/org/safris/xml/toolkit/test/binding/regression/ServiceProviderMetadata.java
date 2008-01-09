package org.safris.xml.toolkit.test.binding.regression;

import aol_liberty_config.AolIdentityProvider;
import aol_liberty_config.AolServiceProvider;
import java.util.Arrays;
import liberty_iff_2003_08.LibAffiliationID;
import liberty_iff_2003_08.LibProviderID;
import liberty_metadata_2003_08.IMdEntityDescriptorType;
import liberty_metadata_2003_08.IMdKeyInfoType;
import liberty_metadata_2003_08.MdCacheDurationAttr;
import liberty_metadata_2003_08.MdEntityDescriptor;
import liberty_metadata_2003_08.MdProviderIDAttr;
import org.safris.xml.generator.compiler.runtime.lang.Duration;
import org.safris.xml.toolkit.test.binding.regression.Metadata;
import org.w3.x2000.x09.xmldsig.DsX509Data;
import org.w3.x2000.x09.xmldsig.IDsKeyInfoType;

public class ServiceProviderMetadata extends Metadata
{
	private static final String DEFAULT_HOST = "aol-3";
	private static final String DEFAULT_DOMAIN = "liberty-iop.biz";
	private static String host = DEFAULT_HOST;
	private static String domain = DEFAULT_DOMAIN;

	public static void main(String[] args)
	{
		if(args.length == 2)
		{
			host = args[0];
			domain = args[1];
		}

		System.out.print(getEntityDescriptor());
		System.out.print(getServiceProvider());
	}

	public static IMdEntityDescriptorType.MdSPDescriptor getSPDescriptor()
	{
		IMdEntityDescriptorType.MdSPDescriptor.MdAssertionConsumerServiceURL assertionConsumerServiceURL = new IMdEntityDescriptorType.MdSPDescriptor.MdAssertionConsumerServiceURL("https://" + host + ".Md" + domain + "/AssertionConsumer");
		assertionConsumerServiceURL.setMdIdAttr(new IMdEntityDescriptorType.MdSPDescriptor.MdAssertionConsumerServiceURL.MdIdAttr("_123"));
		assertionConsumerServiceURL.setMdIsDefaultAttr(new IMdEntityDescriptorType.MdSPDescriptor.MdAssertionConsumerServiceURL.MdIsDefaultAttr(new Boolean(true)));

		IMdEntityDescriptorType.MdSPDescriptor spDescriptor = new IMdEntityDescriptorType.MdSPDescriptor();
		spDescriptor.setMdProtocolSupportEnumerationAttr(new IMdEntityDescriptorType.MdSPDescriptor.MdProtocolSupportEnumerationAttr(Arrays.asList(new String[]{"urn:liberty:iff:2003-08"})));
		spDescriptor.addMdAssertionConsumerServiceURL(assertionConsumerServiceURL);
		spDescriptor.addMdFederationTerminationNotificationProtocolProfile(new IMdEntityDescriptorType.MdIDPDescriptor.MdFederationTerminationNotificationProtocolProfile("http://projectliberty.Mdorg/profiles/fedterm-idp-http"));
		spDescriptor.addMdFederationTerminationNotificationProtocolProfile(new IMdEntityDescriptorType.MdIDPDescriptor.MdFederationTerminationNotificationProtocolProfile("http://projectliberty.Mdorg/profiles/fedterm-idp-soap"));
		spDescriptor.addMdRegisterNameIdentifierProtocolProfile(new IMdEntityDescriptorType.MdIDPDescriptor.MdRegisterNameIdentifierProtocolProfile("http://projectliberty.Mdorg/profiles/rni-idp-http"));
		spDescriptor.addMdRegisterNameIdentifierProtocolProfile(new IMdEntityDescriptorType.MdIDPDescriptor.MdRegisterNameIdentifierProtocolProfile("http://projectliberty.Mdorg/profiles/rni-idp-soap"));
		spDescriptor.addMdSingleLogoutProtocolProfile(new IMdEntityDescriptorType.MdIDPDescriptor.MdSingleLogoutProtocolProfile("http://projectliberty.Mdorg/profiles/slo-idp-http"));
		spDescriptor.addMdSingleLogoutProtocolProfile(new IMdEntityDescriptorType.MdIDPDescriptor.MdSingleLogoutProtocolProfile("http://projectliberty.Mdorg/profiles/slo-idp-soap"));
		spDescriptor.setMdCacheDurationAttr(new MdCacheDurationAttr(new Duration(false, 0, 0, 7)));
		spDescriptor.setMdAuthnRequestsSigned(new IMdEntityDescriptorType.MdSPDescriptor.MdAuthnRequestsSigned(new Boolean(true)));
		spDescriptor.setMdFederationTerminationServiceURL(new IMdEntityDescriptorType.MdSPDescriptor.MdFederationTerminationServiceURL("https://" + host + ".Md" + domain + "/FederationTermination"));
		spDescriptor.setMdFederationTerminationServiceReturnURL(new IMdEntityDescriptorType.MdSPDescriptor.MdFederationTerminationServiceReturnURL("https://" + host + ".Md" + domain + "/FederationTerminationReturn"));
		spDescriptor.setMdRegisterNameIdentifierServiceURL(new IMdEntityDescriptorType.MdSPDescriptor.MdRegisterNameIdentifierServiceURL("https://" + host + ".Md" + domain + "/RegisterNameIdentifier"));
		spDescriptor.setMdRegisterNameIdentifierServiceReturnURL(new IMdEntityDescriptorType.MdSPDescriptor.MdRegisterNameIdentifierServiceReturnURL("https://" + host + ".Md" + domain + "/RegisterNameIdentifierReturn"));
		spDescriptor.setMdSingleLogoutServiceURL(new IMdEntityDescriptorType.MdSPDescriptor.MdSingleLogoutServiceURL("https://" + host + ".Md" + domain + "/SingleLogout"));
		spDescriptor.setMdSingleLogoutServiceReturnURL(new IMdEntityDescriptorType.MdSPDescriptor.MdSingleLogoutServiceReturnURL("https://" + host + ".Md" + domain + "/SingleLogoutReturn"));
		spDescriptor.setMdProviderIDAttr(new MdProviderIDAttr("https://" + host + ".Md" + domain + "/metadata.Mdxml"));
		spDescriptor.setMdSoapEndpoint(new IMdEntityDescriptorType.MdIDPDescriptor.MdSoapEndpoint("https://" + host + ".Md" + domain + "/services/SoapEndpoint"));

		DsX509Data.DsX509Certificate x509Certificate = new DsX509Data.DsX509Certificate(getKeyInfo(host));
		DsX509Data x509Data = new DsX509Data();
		x509Data.addDsX509Certificate(x509Certificate);

		IMdKeyInfoType keyInfo = new IMdKeyInfoType()
		{
			protected IDsKeyInfoType inherits()
			{
				return null;
			}
		};
		keyInfo.setMdUseAttr(new IMdKeyInfoType.MdUseAttr(IMdKeyInfoType.MdUseAttr.SIGNING));
		keyInfo.addDsX509Data(x509Data);

		spDescriptor.addMdKeyInfo(keyInfo);

		return spDescriptor;
	}

	public static MdEntityDescriptor getEntityDescriptor()
	{
		MdEntityDescriptor entityDescriptor = new MdEntityDescriptor();
		entityDescriptor.addMdSPDescriptor(getSPDescriptor());
		return entityDescriptor;
	}

	public static AolServiceProvider getServiceProvider()
	{
		AolServiceProvider.AolGuidGenerator guidGenerator = new AolServiceProvider.AolGuidGenerator();
		guidGenerator.setAolMacAddress(new AolServiceProvider.AolGuidGenerator.AolMacAddress("08:02:20:9f:ae:54"));

		AolServiceProvider.AolSession.AolPool sessionPool = new AolServiceProvider.AolSession.AolPool();
		sessionPool.setAolSize(new AolServiceProvider.AolSession.AolPool.AolSize(new Integer(10)));

		AolServiceProvider.AolSession.AolState sessionState = new AolServiceProvider.AolSession.AolState();
		sessionState.setAolTimeout(new AolServiceProvider.AolSession.AolState.AolTimeout(new Integer(30000)));

		AolServiceProvider.AolSession session = new AolServiceProvider.AolSession();
		session.setAolPool(sessionPool);
		session.setAolState(sessionState);

		AolServiceProvider.AolXmlSchema xmlSchema = new AolServiceProvider.AolXmlSchema();
		xmlSchema.setAolSchemaLocation(new AolServiceProvider.AolXmlSchema.AolSchemaLocation("urn:aol:liberty:config /usr/safris/servers/docs/tacobell/schemas/aol-lib-config.Aolxsd http://www.Aolw3.Aolorg/2001/04/xmlenc# /usr/safris/servers/docs/tacobell/schemas/xenc-schema.Aolxsd http://www.Aolw3.Aolorg/2000/09/xmldsig# /usr/safris/servers/docs/tacobell/schemas/xmldsig-core-schema.Aolxsd urn:oasis:names:tc:SAML:1.Aol0:protocol /usr/safris/servers/docs/tacobell/schemas/cs-sstc-schema-protocol-01.Aolxsd urn:oasis:names:tc:SAML:1.Aol0:assertion /usr/safris/servers/docs/tacobell/schemas/cs-sstc-schema-assertion-01.Aolxsd urn:liberty:ac:2003-08 /usr/safris/servers/docs/tacobell/schemas/lib-arch-authentication-context.Aolxsd urn:liberty:iff:2003-08 lib-arch-protocols-schema.Aolxsd urn:liberty:disco:2003-08 /usr/safris/servers/docs/tacobell/schemas/lib-arch-disco-svc.Aolxsd urn:liberty:metadata:2003-08 /usr/safris/servers/docs/tacobell/schemas/lib-arch-metadata.Aolxsd"));
		xmlSchema.setAolValidateMarshal(new AolServiceProvider.AolXmlSchema.AolValidateMarshal(new Boolean(false)));
		xmlSchema.setAolValidateParse(new AolServiceProvider.AolXmlSchema.AolValidateParse(new Boolean(true)));

		AolServiceProvider serviceProvider = new AolServiceProvider();
		serviceProvider.setAolApplicationServiceURL(new AolServiceProvider.AolApplicationServiceURL("https://" + host + ".Aol" + domain + "/Application"));
		serviceProvider.setAolLoginServiceURL(new AolServiceProvider.AolLoginServiceURL("https://" + host + ".Aol" + domain + "/Login"));
		serviceProvider.setAolAdminServiceURL(new AolServiceProvider.AolAdminServiceURL("https://" + host + ".Aol" + domain + "/Manager"));
		serviceProvider.setLibProviderID(new LibProviderID("https://" + host + ".Aol" + domain + "/metadata.Aolxml"));
		serviceProvider.setLibAffiliationID(new LibAffiliationID("https://" + host + ".Aol" + domain + "/affiliation.Aolxml"));
		serviceProvider.setAolGuidGenerator(guidGenerator);
		serviceProvider.setAolSession(session);
		serviceProvider.setAolXmlSchema(xmlSchema);
		serviceProvider.setAolAddSignature(new AolServiceProvider.AolAddSignature(new Boolean(true)));
		serviceProvider.setAolVerifySignature(new AolIdentityProvider.AolVerifySignature(new Boolean(true)));
		serviceProvider.setAolSelectProtocolProfile(new AolServiceProvider.AolSelectProtocolProfile(new Boolean(true)));
		serviceProvider.setAolSigAlgorithm(new AolServiceProvider.AolSigAlgorithm("rsa"));
		serviceProvider.setAolCommonDomain(new AolServiceProvider.AolCommonDomain(".Aol" + domain));
		serviceProvider.setAolSingleSignOnProtocolProfile(new AolServiceProvider.AolSingleSignOnProtocolProfile("http://projectliberty.Aolorg/profiles/brws-art"));
		serviceProvider.setAolNameIdentifierMappingServiceURL(new AolServiceProvider.AolNameIdentifierMappingServiceURL("https://" + host + ".Aol" + domain + "/NameIdentifierMapping"));
		serviceProvider.setAolDiscoveryLookupServiceURL(new AolServiceProvider.AolDiscoveryLookupServiceURL("https://" + host + ".Aol" + domain + "/DiscoveryLookup"));

		return serviceProvider;
	}
}
