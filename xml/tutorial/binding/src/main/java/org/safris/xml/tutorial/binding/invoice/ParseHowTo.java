package org.safris.xml.tutorial.binding.invoice;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import org.safris.commons.xml.binding.Date;
import org.safris.commons.xml.binding.Decimal;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.xml.sax.InputSource;

public class ParseHowTo
{
	public static void main(String[] args) throws Exception
	{
		File file = new File("src/main/resources/xml/invoice.xml");
		if(!file.exists())
			throw new Error("File " + file.getAbsolutePath() + " does not exist.");

		if(!file.canRead())
			throw new Error("File " + file.getAbsolutePath() + " is not readable.");

		pv_invoice invoice = (pv_invoice)Bindings.parse(new InputSource(new FileInputStream(file)));

		Integer number = invoice.get_number().get(0).getText();
		System.out.print("This invoice # " + number + " ");

		Date date = invoice.get_date().get(0).getText();
		System.out.println("is established on " + date + " ");

		List<$pv_addressType._name> billingNames = invoice.get_billingAddress().get(0).get_name();
		String billingName = billingNames.get(0).getText();
		System.out.print("from " + billingName + ", ");

		List<$pv_addressType._address> billingAddresses = invoice.get_billingAddress().get(0).get_address();
		String billingAddress = billingAddresses.get(0).getText();
		System.out.print(billingAddress + ", ");

		List<$pv_addressType._city> billingCityies = invoice.get_billingAddress().get(0).get_city();
		String billingCity = billingCityies.get(0).getText();
		System.out.print(billingCity + ", ");

		List<$pv_addressType._postalCode> billingPostalCodes = invoice.get_billingAddress().get(0).get_postalCode();
		Integer billingPostalCode = billingPostalCodes.get(0).getText();
		System.out.print(billingPostalCode + ", ");

		List<$pv_addressType._country> billingCountries = invoice.get_billingAddress().get(0).get_country();
		String billingCountry = billingCountries.get(0).getText();
		System.out.println(billingCountry + ".");

		List<$pv_addressType._name> shippingNames = invoice.get_shippingAddress().get(0).get_name();
		String shippingName = shippingNames.get(0).getText();
		System.out.print("Shipping address is: " + shippingName + ", ");

		List<$pv_addressType._address> shippingAddresses = invoice.get_shippingAddress().get(0).get_address();
		String shippingAddress = shippingAddresses.get(0).getText();
		System.out.print(shippingAddress + ", ");

		List<$pv_addressType._city> shippingCities = invoice.get_shippingAddress().get(0).get_city();
		String shippingCity = shippingCities.get(0).getText();
		System.out.print(shippingCity + ", ");

		List<$pv_addressType._postalCode> shippingPostalCodes = invoice.get_shippingAddress().get(0).get_postalCode();
		Integer shippingPostalCode = shippingPostalCodes.get(0).getText();
		System.out.print(shippingPostalCode + ", ");

		List<$pv_addressType._country> shippingCountries = invoice.get_shippingAddress().get(0).get_country();
		String shippingCountry = shippingCountries.get(0).getText();
		System.out.println(shippingCountry + ".");

		System.out.println("The following items are included in this invoice:");
		for($pv_itemType item : (List<$pv_itemType>)invoice.get_billedItems().get(0).get_item())
		{
			List<$pv_itemType._quantity> quantities = item.get_quantity();
			Integer quantity = quantities.get(0).getText();
			System.out.print(quantity + " ");

			List<$pv_itemType._description> descriptions = item.get_description();
			String description = descriptions.get(0).getText();
			System.out.print(description + " ");

			List<$pv_itemType._code> codes = item.get_code();
			Integer code = codes.get(0).getText();
			System.out.print("(#" + code + ") ");

			List<$pv_positiveDecimal> prices = item.get_price();
			Decimal price = prices.get(0).getText();
			System.out.println("$" + price + " each.");
		}
	}
}
