package org.safris.xml.toolkit.test.binding.regression;

import org.safris.xml.schema.binding.test.unit.everything.IEvComplexTypie;
import org.safris.xml.schema.binding.test.unit.everything.IEvComplexTypieOriginal;
import org.safris.xml.schema.binding.test.unit.everything.IEvSimpleTypie;
import org.safris.xml.schema.binding.test.unit.everything.IEvSimpleTypieOriginal;

public class Redefine
{
	public static void main(String[] args)
	{
		new Redefine().testComplexTypie();
		new Redefine().testSimpleTypie();
	}

	public void testComplexTypie()
	{
		IEvComplexTypieOriginal original = new IEvComplexTypieOriginal()
		{
			protected IEvComplexTypieOriginal inherits()
			{
				return null;
			}
		};

		original.setAttrTestAttributeNested1Attr(null);
		original.setAttrTestAttributeTypeAttr(null);
		original.setTyTestTypeNCName(null);
		original.setTyTestTypeQName(null);
		original.setANY(null);						// in <redefine/> of groupie
		original.addANYATTR(null);					// in <redefine/> of attrGroupie

		IEvComplexTypie redefined = new IEvComplexTypie()
		{
			protected IEvComplexTypie inherits()
			{
				return null;
			}
		};

		redefined.setAttrTestAttributeNested1Attr(null);
		redefined.setAttrTestAttributeTypeAttr(null);
		redefined.setTyTestTypeNCName(null);
		redefined.setTyTestTypeQName(null);
		redefined.setANY(null);						// in <redefine/> of groupie
		redefined.addANYATTR(null);					// in <redefine/> of attrGroupie
		redefined.setEvRedefineTestAttr(null);		// in <redefine/> of complexTypie
	}

	public void testSimpleTypie()
	{
		IEvSimpleTypieOriginal original = new IEvSimpleTypieOriginal()
		{
			protected IEvSimpleTypieOriginal inherits()
			{
				return null;
			}
		};

		original.setTEXT(IEvSimpleTypieOriginal._1);
		original.setTEXT(IEvSimpleTypieOriginal._2);
		original.setTEXT(IEvSimpleTypieOriginal._3);

		IEvSimpleTypie redefined = new IEvSimpleTypie()
		{
			protected IEvSimpleTypie inherits()
			{
				return null;
			}
		};

		redefined.setTEXT(IEvSimpleTypie._1);
//		redefined.setTEXT(IEvSimpleTypie._2);	// removed in <redefine/> of simpleTypie
		redefined.setTEXT(IEvSimpleTypie._3);
	}
}
