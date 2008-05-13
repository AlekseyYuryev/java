package org.safris.xml.toolkit.test.binding.regression;

import org.junit.Test;
import org.safris.xml.schema.binding.test.unit.everything.$ev_complexTypie;
import org.safris.xml.schema.binding.test.unit.everything.$ev_complexTypie_original;
import org.safris.xml.schema.binding.test.unit.everything.$ev_simpleTypie;
import org.safris.xml.schema.binding.test.unit.everything.$ev_simpleTypie_original;

public class RedefineTest
{
	public static void main(String[] args)
	{
		new RedefineTest().testComplexTypie();
		new RedefineTest().testSimpleTypie();
	}

	@Test
	public void testComplexTypie()
	{
		$ev_complexTypie_original original = new $ev_complexTypie_original()
		{
			protected $ev_complexTypie_original inherits()
			{
				return null;
			}
		};

		original.addattr_test_attribute_nested1$(null);
		original.addattr_test_attribute_type$(null);
		original.addty_test_type_NCName(null);
		original.addty_test_type_QName(null);
		original.addAny(null);						// in <redefine/> of groupie
		original.addAny$(null);					// in <redefine/> of attrGroupie

		$ev_complexTypie redefined = new $ev_complexTypie()
		{
			protected $ev_complexTypie inherits()
			{
				return null;
			}
		};

		redefined.addattr_test_attribute_nested1$(null);
		redefined.addattr_test_attribute_type$(null);
		redefined.addty_test_type_NCName(null);
		redefined.addty_test_type_QName(null);
		redefined.addAny(null);						// in <redefine/> of groupie
		redefined.addAny$(null);					// in <redefine/> of attrGroupie
		redefined.add_redefineTest$(null);		// in <redefine/> of complexTypie
	}

	@Test
	public void testSimpleTypie()
	{
		$ev_simpleTypie_original original = new $ev_simpleTypie_original()
		{
			protected $ev_simpleTypie_original inherits()
			{
				return null;
			}
		};

		original.setText($ev_simpleTypie_original._1);
		original.setText($ev_simpleTypie_original._2);
		original.setText($ev_simpleTypie_original._3);

		$ev_simpleTypie redefined = new $ev_simpleTypie()
		{
			protected $ev_simpleTypie inherits()
			{
				return null;
			}
		};

		redefined.setText($ev_simpleTypie._1);
//		redefined.setText($ev_simpleTypie._2);	// removed in <redefine/> of simpleTypie
		redefined.setText($ev_simpleTypie._3);
	}
}
