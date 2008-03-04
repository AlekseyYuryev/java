package org.safris.xml.tutorial.binding.invoice;

import java.io.File;
import java.io.FileInputStream;
import org.safris.commons.xml.binding.Decimal;
import org.safris.commons.xml.dom.DOMStyle;
import org.safris.commons.xml.dom.DOMs;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.xml.sax.InputSource;

public class InvoiceUpdater
{
	private static PvInvoice addItem(File invoiceFile, PvInvoice.PvBilledItems.PvItem item) throws Exception
	{
		final PvInvoice invoice = (PvInvoice)Bindings.parse(new InputSource(new FileInputStream(invoiceFile)));
		invoice.getPvBilledItems().addPvItem(item);
		return invoice;
	}

	public static void main(String[] args) throws Exception
	{
		if(args.length == 0)
			usage();

		final File file = new File(args[0]);

		final PvInvoice.PvBilledItems.PvItem item = new PvInvoice.PvBilledItems.PvItem();
		item.setPvDescription(new PvInvoice.PvBilledItems.PvItem.PvDescription(args[1]));
		item.setPvCode(new PvInvoice.PvBilledItems.PvItem.PvCode(Integer.parseInt(args[2])));
		item.setPvQuantity(new PvInvoice.PvBilledItems.PvItem.PvQuantity(Integer.parseInt(args[3])));
		item.setPvPrice(new PvInvoice.PvBilledItems.PvItem.PvPrice(new Decimal(Float.parseFloat(args[4]))));

		final PvInvoice invoice = addItem(file, item);
		DOMs.domToString(invoice.marshal(), DOMStyle.INDENT);
	}

	private static void usage()
	{
		System.err.println("Usage: InvoiceUpdater <invoice.xml>");
		System.exit(1);
	}
}
