package org.safris.xml.tutorial.binding.invoice;

import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.generator.compiler.runtime.BindingsOption;
import org.safris.xml.generator.compiler.runtime.lang.Date;
import org.safris.xml.generator.compiler.runtime.lang.Decimal;

public class MarshalHowTo
{
	public static void main(String[] args) throws Exception
	{
		PvInvoice invoice = new PvInvoice();
		invoice.setPvDate(new PvInvoice.PvDate(new Date(2003, 1, 7)));

		invoice.setPvNumber(new PvInvoice.PvNumber(1458));

		PvInvoice.PvBillingAddress billingAddress = new PvInvoice.PvBillingAddress();
		billingAddress.setPvName(new PvInvoice.PvBillingAddress.PvName("Ian Barking"));
		billingAddress.setPvAddress(new PvInvoice.PvBillingAddress.PvAddress("123 Kennel Street"));
		billingAddress.setPvCity(new PvInvoice.PvBillingAddress.PvCity("Dachshund City"));
		billingAddress.setPvPostalCode(new PvInvoice.PvBillingAddress.PvPostalCode(98765));
		billingAddress.setPvCountry(new PvInvoice.PvBillingAddress.PvCountry("US"));

		invoice.setPvBillingAddress(billingAddress);

		PvInvoice.PvShippingAddress shippingAddress = new PvInvoice.PvShippingAddress();
		shippingAddress.setPvName(new PvInvoice.PvBillingAddress.PvName("Retail Dept."));
		shippingAddress.setPvAddress(new PvInvoice.PvBillingAddress.PvAddress("888 Dogbowl Street"));
		shippingAddress.setPvCity(new PvInvoice.PvBillingAddress.PvCity("Pet City"));
		shippingAddress.setPvPostalCode(new PvInvoice.PvBillingAddress.PvPostalCode(98765));
		shippingAddress.setPvCountry(new PvInvoice.PvBillingAddress.PvCountry("US"));

		invoice.setPvShippingAddress(shippingAddress);

		PvInvoice.PvBilledItems billedItems = new PvInvoice.PvBilledItems();

		PvInvoice.PvBilledItems.PvItem item = new PvInvoice.PvBilledItems.PvItem();
		item.setPvDescription(new PvInvoice.PvBilledItems.PvItem.PvDescription("Studded Collar"));
		item.setPvCode(new PvInvoice.PvBilledItems.PvItem.PvCode(45342));
		item.setPvQuantity(new PvInvoice.PvBilledItems.PvItem.PvQuantity(10));
		item.setPvPrice(new PvInvoice.PvBilledItems.PvItem.PvPrice(new Decimal(11.95)));

		billedItems.addPvItem(item);

		item = new PvInvoice.PvBilledItems.PvItem();
		item.setPvDescription(new PvInvoice.PvBilledItems.PvItem.PvDescription("K9 Pet Coat"));
		item.setPvCode(new PvInvoice.PvBilledItems.PvItem.PvCode(25233));
		item.setPvQuantity(new PvInvoice.PvBilledItems.PvItem.PvQuantity(5));
		item.setPvPrice(new PvInvoice.PvBilledItems.PvItem.PvPrice(new Decimal(25.01)));

		billedItems.addPvItem(item);

		invoice.setPvBilledItems(billedItems);

		System.out.println(Bindings.domToString(invoice.marshal(), BindingsOption.INDENT));
	}
}
