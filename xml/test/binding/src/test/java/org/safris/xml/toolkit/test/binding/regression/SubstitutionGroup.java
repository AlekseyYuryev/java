package org.safris.xml.toolkit.test.binding.regression;

import org.safris.xml.generator.compiler.runtime.BindingConfig;
import org.safris.xml.generator.compiler.runtime.BindingException;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.schema.binding.test.unit.substitutiongroup.ISgTwentyType;
import org.safris.xml.schema.binding.test.unit.substitutiongroup.SgEvenTen;
import org.safris.xml.schema.binding.test.unit.substitutiongroup.SgEvenTwenty;
import org.safris.xml.schema.binding.test.unit.substitutiongroup.SgOddTen;
import org.safris.xml.schema.binding.test.unit.substitutiongroup.SgOddTwenty;
import org.safris.xml.schema.binding.test.unit.substitutiongroup.SgRoot;
import org.safris.xml.schema.binding.test.unit.substitutiongroup.SgTwentyImpl;

public class SubstitutionGroup
{
	static
	{
		BindingConfig bindingConfig = new BindingConfig();
		bindingConfig.setIndent(true);
		Bindings.bootstrapConfig(bindingConfig);
	}

	public static void main(String[] args) throws BindingException
	{
		SgRoot root = new SgRoot();

		ISgTwentyType twentyImpl = new SgTwentyImpl(13);
		root.addSgTwenty(twentyImpl);

		ISgTwentyType oddTwenty = new SgOddTwenty(SgOddTwenty._19);
		root.addSgTwenty(oddTwenty);

		ISgTwentyType evenTwenty = new SgEvenTwenty(SgEvenTwenty._18);
		root.addSgTwenty(evenTwenty);

		ISgTwentyType oddTen = new SgOddTen(SgOddTen._9);
		root.addSgTwenty(oddTen);

		ISgTwentyType evenTen = new SgEvenTen(SgEvenTen._8);
		root.addSgTwenty(evenTen);

		System.out.println(Bindings.domToString(root.marshal()));
	}
}
