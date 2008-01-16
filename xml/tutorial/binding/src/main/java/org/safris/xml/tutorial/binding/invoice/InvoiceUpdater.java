package org.safris.xml.tutorial.binding.invoice;

import java.io.File;
import java.io.FileInputStream;
import org.safris.xml.generator.compiler.runtime.BindingConfig;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.generator.compiler.runtime.lang.Decimal;
import org.xml.sax.InputSource;

public class InvoiceUpdater
{
	static
	{
		BindingConfig bindingConfig = new BindingConfig();
		bindingConfig.setIndent(true);
		Bindings.bootstrapConfig(bindingConfig);
	}

	private static String addItem(File invoiceFile, PvInvoice.PvBilledItems.PvItem item) throws Exception
	{
		PvInvoice invoice = Bindings.<PvInvoice>parse(new InputSource(new FileInputStream(invoiceFile)));
		invoice.getPvBilledItems().addPvItem(item);
		return invoice.toString();
	}

	public static void main(String[] args) throws Exception
	{
		File file = new File(args[0]);

		PvInvoice.PvBilledItems.PvItem item = new PvInvoice.PvBilledItems.PvItem();
		item.setPvDescription(new PvInvoice.PvBilledItems.PvItem.PvDescription(args[1]));
		item.setPvCode(new PvInvoice.PvBilledItems.PvItem.PvCode(Integer.parseInt(args[2])));
		item.setPvQuantity(new PvInvoice.PvBilledItems.PvItem.PvQuantity(Integer.parseInt(args[3])));
		item.setPvPrice(new PvInvoice.PvBilledItems.PvItem.PvPrice(new Decimal(Float.parseFloat(args[4]))));

		addItem(file, item);
	}
}
