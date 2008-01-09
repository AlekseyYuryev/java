package org.safris.xml.toolkit.test.binding.regression;

import aol_liberty_config.AolIdentityProvider;
import java.util.Arrays;
import javax.xml.namespace.QName;
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

public class IdentityProviderMetadata extends Metadata
{
	private static String DEFAULT_HOST = "aol-1";
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

		System.out.print(getEntityDescriptor());
		System.out.print(getIdentityProvider());
	}

	public static IMdEntityDescriptorType.MdIDPDescriptor getIDPDescriptor()
	{
		IMdEntityDescriptorType.MdIDPDescriptor.MdNameIdentifierMappingBinding nameIdentifierMappingBinding = new IMdEntityDescriptorType.MdIDPDescriptor.MdNameIdentifierMappingBinding();
		nameIdentifierMappingBinding.setSamlAuthorityKindAttr(new IMdEntityDescriptorType.MdIDPDescriptor.MdNameIdentifierMappingBinding.SamlAuthorityKindAttr(new QName("urn:oasis:names:tc:Saml:1.0:protocol", "AttributeQuery")));
		nameIdentifierMappingBinding.setSamlLocationAttr(new IMdEntityDescriptorType.MdIDPDescriptor.MdNameIdentifierMappingBinding.SamlLocationAttr(("https://" + host + "." + domain + "/services/SoapEndpoint")));
		nameIdentifierMappingBinding.setSamlBindingAttr(new IMdEntityDescriptorType.MdIDPDescriptor.MdNameIdentifierMappingBinding.SamlBindingAttr("urn:oasis:names:tc:Saml:1.0:assertion"));

		IMdEntityDescriptorType.MdIDPDescriptor idpDescriptor = new IMdEntityDescriptorType.MdIDPDescriptor();
		idpDescriptor.setMdProtocolSupportEnumerationAttr(new IMdEntityDescriptorType.MdIDPDescriptor.MdProtocolSupportEnumerationAttr(Arrays.asList(new String[]{"urn:liberty:iff:2003-08"})));
		idpDescriptor.addMdSingleSignOnProtocolProfile(new IMdEntityDescriptorType.MdIDPDescriptor.MdSingleSignOnProtocolProfile("http://projectliberty.org/profiles/brws-art"));
		idpDescriptor.addMdSingleSignOnProtocolProfile(new IMdEntityDescriptorType.MdIDPDescriptor.MdSingleSignOnProtocolProfile("http://projectliberty.org/profiles/brws-post"));
		idpDescriptor.setMdSingleSignOnServiceURL(new IMdEntityDescriptorType.MdIDPDescriptor.MdSingleSignOnServiceURL("https://" + host + "." + domain + "/SingleSignOn"));
		idpDescriptor.setMdCacheDurationAttr(new MdCacheDurationAttr(new Duration(false, 0, 0, 7)));
		idpDescriptor.addMdFederationTerminationNotificationProtocolProfile(new IMdEntityDescriptorType.MdIDPDescriptor.MdFederationTerminationNotificationProtocolProfile("http://projectliberty.org/profiles/fedterm-sp-http"));
		idpDescriptor.addMdFederationTerminationNotificationProtocolProfile(new IMdEntityDescriptorType.MdIDPDescriptor.MdFederationTerminationNotificationProtocolProfile("http://projectliberty.org/profiles/fedterm-sp-soap"));
		idpDescriptor.addMdRegisterNameIdentifierProtocolProfile(new IMdEntityDescriptorType.MdIDPDescriptor.MdRegisterNameIdentifierProtocolProfile("http://projectliberty.org/profiles/rni-sp-http"));
		idpDescriptor.addMdRegisterNameIdentifierProtocolProfile(new IMdEntityDescriptorType.MdIDPDescriptor.MdRegisterNameIdentifierProtocolProfile("http://projectliberty.org/profiles/rni-sp-soap"));
		idpDescriptor.addMdSingleLogoutProtocolProfile(new IMdEntityDescriptorType.MdIDPDescriptor.MdSingleLogoutProtocolProfile("http://projectliberty.org/profiles/slo-sp-http"));
		idpDescriptor.addMdSingleLogoutProtocolProfile(new IMdEntityDescriptorType.MdIDPDescriptor.MdSingleLogoutProtocolProfile("http://projectliberty.org/profiles/slo-sp-soap"));
		idpDescriptor.addMdNameIdentifierMappingBinding(nameIdentifierMappingBinding);
		idpDescriptor.setMdFederationTerminationServiceURL(new IMdEntityDescriptorType.MdSPDescriptor.MdFederationTerminationServiceURL("https://" + host + "." + domain + "/FederationTermination"));
		idpDescriptor.setMdFederationTerminationServiceReturnURL(new IMdEntityDescriptorType.MdSPDescriptor.MdFederationTerminationServiceReturnURL("https://" + host + "." + domain + "/FederationTerminationReturn"));
		idpDescriptor.setMdRegisterNameIdentifierServiceURL(new IMdEntityDescriptorType.MdSPDescriptor.MdRegisterNameIdentifierServiceURL("https://" + host + "." + domain + "/RegisterNameIdentifier"));
		idpDescriptor.setMdRegisterNameIdentifierServiceReturnURL(new IMdEntityDescriptorType.MdSPDescriptor.MdRegisterNameIdentifierServiceReturnURL("https://" + host + "." + domain + "/RegisterNameIdentifierReturn"));
		idpDescriptor.setMdSingleLogoutServiceURL(new IMdEntityDescriptorType.MdSPDescriptor.MdSingleLogoutServiceURL("https://" + host + "." + domain + "/SingleLogout"));
		idpDescriptor.setMdSingleLogoutServiceReturnURL(new IMdEntityDescriptorType.MdSPDescriptor.MdSingleLogoutServiceReturnURL("https://" + host + "." + domain + "/SingleLogoutReturn"));
		idpDescriptor.setMdProviderIDAttr(new MdProviderIDAttr("https://" + host + "." + domain + "/metadata.xml"));
		idpDescriptor.setMdSoapEndpoint(new IMdEntityDescriptorType.MdIDPDescriptor.MdSoapEndpoint("https://" + host + "." + domain + "/services/SoapEndpoint"));

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

		idpDescriptor.addMdKeyInfo(keyInfo);
		return idpDescriptor;
	}

	public static MdEntityDescriptor getEntityDescriptor()
	{
		MdEntityDescriptor entityDescriptor = new MdEntityDescriptor();
		entityDescriptor.addMdIDPDescriptor(getIDPDescriptor());
		return entityDescriptor;
	}

	public static AolIdentityProvider getIdentityProvider()
	{
		AolIdentityProvider.AolGuidGenerator guidGenerator = new AolIdentityProvider.AolGuidGenerator();
		guidGenerator.setAolMacAddress(new AolIdentityProvider.AolGuidGenerator.AolMacAddress("10:11:11:2f:ee:24"));

		AolIdentityProvider.AolJdbc jdbc = new AolIdentityProvider.AolJdbc();
		jdbc.setAolConfigPath(new AolIdentityProvider.AolJdbc.AolConfigPath("/usr/local/tomcat4/" + host + "/server/webapps/liberty/WEB-INF/etc/connection-pool.xml"));

		AolIdentityProvider.AolSession.AolPool sessionPool = new AolIdentityProvider.AolSession.AolPool();
		sessionPool.setAolSize(new AolIdentityProvider.AolSession.AolPool.AolSize(new Integer(10)));

		AolIdentityProvider.AolSession.AolState sessionState = new AolIdentityProvider.AolSession.AolState();
		sessionState.setAolTimeout(new AolIdentityProvider.AolSession.AolState.AolTimeout(new Integer(30000)));

		AolIdentityProvider.AolSession session = new AolIdentityProvider.AolSession();
		session.setAolPool(sessionPool);
		session.setAolState(sessionState);

		AolIdentityProvider.AolXmlSchema xmlSchema = new AolIdentityProvider.AolXmlSchema();
		xmlSchema.setAolSchemaLocation(new AolIdentityProvider.AolXmlSchema.AolSchemaLocation("urn:aol:liberty:config /usr/safris/servers/docs/foodidp/schemas/aol-lib-config.xsd http://www.w3.org/2001/04/xmlenc# /usr/safris/servers/docs/foodidp/schemas/xenc-schema.xsd http://www.w3.org/2000/09/xmldsig# /usr/safris/servers/docs/foodidp/schemas/xmldsig-core-schema.xsd urn:oasis:names:tc:Saml:1.0:protocol /usr/safris/servers/docs/foodidp/schemas/cs-sstc-schema-protocol-01.xsd urn:oasis:names:tc:Saml:1.0:assertion /usr/safris/servers/docs/foodidp/schemas/cs-sstc-schema-assertion-01.xsd urn:liberty:ac:2003-08 /usr/safris/servers/docs/foodidp/schemas/lib-arch-authentication-context.xsd urn:liberty:iff:2003-08 lib-arch-protocols-schema.xsd urn:liberty:disco:2003-08 /usr/safris/servers/docs/foodidp/schemas/lib-arch-disco-svc.xsd urn:liberty:metadata:2003-08 /usr/safris/servers/docs/foodidp/schemas/lib-arch-metadata.xsd"));
		xmlSchema.setAolValidateMarshal(new AolIdentityProvider.AolXmlSchema.AolValidateMarshal(new Boolean(false)));
		xmlSchema.setAolValidateParse(new AolIdentityProvider.AolXmlSchema.AolValidateParse(new Boolean(true)));

		AolIdentityProvider identityProvider = new AolIdentityProvider();
		identityProvider.setLibProviderID(new LibProviderID("https://" + host + "." + domain + "/metadata.xml"));
		identityProvider.setAolApplicationServiceURL(new AolIdentityProvider.AolApplicationServiceURL("https://" + host + "." + domain + "/Application"));
		identityProvider.setAolLoginServiceURL(new AolIdentityProvider.AolLoginServiceURL("https://" + host + "." + domain + "/Login"));
		identityProvider.setAolAdminServiceURL(new AolIdentityProvider.AolAdminServiceURL("https://" + host + "." + domain + "/Manager"));
		identityProvider.setAolGuidGenerator(guidGenerator);
		identityProvider.setAolJdbc(jdbc);
		identityProvider.setAolSession(session);
		identityProvider.setAolXmlSchema(xmlSchema);
		identityProvider.setAolAddSignature(new AolIdentityProvider.AolAddSignature(new Boolean(true)));
		identityProvider.setAolVerifySignature(new AolIdentityProvider.AolVerifySignature(new Boolean(true)));
		identityProvider.setAolSelectProtocolProfile(new AolIdentityProvider.AolSelectProtocolProfile(new Boolean(true)));
		identityProvider.setAolSigAlgorithm(new AolIdentityProvider.AolSigAlgorithm("rsa"));
		identityProvider.setAolCommonDomain(new AolIdentityProvider.AolCommonDomain("." + domain));
		identityProvider.setAolRegistrationServiceURL(new AolIdentityProvider.AolRegistrationServiceURL("https://" + host + "." + domain + "/Registration"));
		identityProvider.setAolDiscoveryProviderID(new AolIdentityProvider.AolDiscoveryProviderID("https://aol-2." + domain + "/metadata.xml"));

		return identityProvider;
	}
}
