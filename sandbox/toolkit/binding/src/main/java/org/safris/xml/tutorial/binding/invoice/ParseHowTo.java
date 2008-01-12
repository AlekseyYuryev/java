package org.safris.xml.tutorial.binding.invoice;

import java.io.File;
import java.io.FileInputStream;
import org.safris.xml.generator.compiler.runtime.BindingConfig;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.generator.compiler.runtime.lang.Date;
import org.safris.xml.generator.compiler.runtime.lang.Decimal;
import org.safris.xml.toolkit.tutorial.binding.beginner.invoice.IPvItemType;
import org.safris.xml.toolkit.tutorial.binding.beginner.invoice.PvInvoice;
import org.xml.sax.InputSource;

public class ParseHowTo
{
	public static void main(String[] args) throws Exception
	{
		File file = new File("src/test/resources/xml/invoice.xml");
		if(!file.exists())
			throw new Error("File " + file.getAbsolutePath() + " does not exist.");

		if(!file.canRead())
			throw new Error("File " + file.getAbsolutePath() + " is not readable.");

		BindingConfig bindingConfig = new BindingConfig();
		bindingConfig.setIndent(true);
		Bindings.bootstrapConfig(bindingConfig);

		PvInvoice invoice = Bindings.<PvInvoice>parse(new InputSource(new FileInputStream(file)));

		Integer number = invoice.getPvNumber().getTEXT();
		System.out.print("This invoice # " + number + " ");

		Date date = invoice.getPvDate().getTEXT();
		System.out.println("is established on " + date + " ");

		String billingName = invoice.getPvBillingAddress().getPvName().getTEXT();
		System.out.print("from " + billingName + ", ");

		String billingAddress = invoice.getPvBillingAddress().getPvAddress().getTEXT();
		System.out.print(billingAddress + ", ");

		String billingCity = invoice.getPvBillingAddress().getPvCity().getTEXT();
		System.out.print(billingCity + ", ");

		Integer billingPostalCode = invoice.getPvBillingAddress().getPvPostalCode().getTEXT();
		System.out.print(billingPostalCode + ", ");

		String billingCountry = invoice.getPvBillingAddress().getPvCountry().getTEXT();
		System.out.println(billingCountry + ".");

		String shippingName = invoice.getPvShippingAddress().getPvName().getTEXT();
		System.out.print("Shipping address is: " + shippingName + ", ");

		String shippingAddress = invoice.getPvShippingAddress().getPvAddress().getTEXT();
		System.out.print(shippingAddress + ", ");

		String shippingCity = invoice.getPvShippingAddress().getPvCity().getTEXT();
		System.out.print(shippingCity + ", ");

		Integer shippingPostalCode = invoice.getPvShippingAddress().getPvPostalCode().getTEXT();
		System.out.print(shippingPostalCode + ", ");

		String shippingCountry = invoice.getPvShippingAddress().getPvCountry().getTEXT();
		System.out.println(shippingCountry + ".");

		System.out.println("The following items are included in this invoice:");
		for(Object object : invoice.getPvBilledItems().getPvItem())
		{
			IPvItemType item = (IPvItemType)object;
			Integer quantity = item.getPvQuantity().getTEXT();
			System.out.print(quantity + " ");

			String description = item.getPvDescription().getTEXT();
			System.out.print(description + " ");

			Integer code = item.getPvCode().getTEXT();
			System.out.print("(#" + code + ") ");

			Decimal price = item.getPvPrice().getTEXT();
			System.out.println("$" + price + " each.");
		}
	}
}
