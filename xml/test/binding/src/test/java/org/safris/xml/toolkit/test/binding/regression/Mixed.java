package org.safris.xml.toolkit.test.binding.regression;

import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.schema.binding.test.unit.mixed.Root;

public class Mixed
{
	public static void main(String[] args)
	{
		Root.Zero zero = new Root.Zero();

		Root.OneEx oneEx = new Root.OneEx(createString());
		oneEx.setTEXT(createString());

		Root.OneRe oneRe = new Root.OneRe();			// FIXME: This should NOT be mixed

		Root.Two two = new Root.Two("mixed");
		two.setTEXT("mixed");

		Root.Three three = new Root.Three(createString());
		three.setTEXT(createString());

		Root.Four four = new Root.Four(createString());
		four.setTEXT(createString());

		Root.Five five = new Root.Five();

		Root.Six six = new Root.Six(createString());
		six.setTEXT(createString());

		Root.Seven seven = new Root.Seven();

		Root.Eight eight = new Root.Eight("mixed");
		eight.setTEXT("mixed");

		Root.Ten ten = new Root.Ten();

		Root.ElevenEx elevenEx = new Root.ElevenEx(createString());
		elevenEx.setTEXT(createString());

		Root.ElevenRe elevenRe = new Root.ElevenRe();	// FIXME: This should NOT be mixed

		Root.Twelve twelve = new Root.Twelve("mixed");
		twelve.setTEXT("mixed");

		Root.Thirteen thirteen = new Root.Thirteen(createString());
		thirteen.setTEXT(createString());

		Root.Fourteen fourteen = new Root.Fourteen(createString());
		fourteen.setTEXT(createString());

		Root.Fifteen fifteen = new Root.Fifteen();

		Root.Sixteen sixteen = new Root.Sixteen(createString());
		sixteen.setTEXT(createString());

		Root.Seventeen seventeen = new Root.Seventeen();

		Root.Eighteen eighteen = new Root.Eighteen("mixed");
		eighteen.setTEXT("mixed");

		Root.Twenty twenty = new Root.Twenty();

		Root.TwentyOne twentyOne = new Root.TwentyOne();

		Root.TwentyTwo twentyTwo = new Root.TwentyTwo("mixed");
		twentyTwo.setTEXT("mixed");

		Root.TwentyThree twentyThree = new Root.TwentyThree(createString());
		twentyThree.setTEXT(createString());

		Root.TwentyFour twentyFour = new Root.TwentyFour(createString());
		twentyFour.setTEXT(createString());

		Root.TwentyFive twentyFive = new Root.TwentyFive();

		Root.TwentySix twentySix = new Root.TwentySix(createString());
		twentySix.setTEXT(createString());

		Root.TwentySeven twentySeven = new Root.TwentySeven();

		Root.TwentyEight twentyEight = new Root.TwentyEight("mixed");
		twentyEight.setTEXT("mixed");

		Root.Thirty thirty = new Root.Thirty();

		Root.ThirtyOne thirtyOne = new Root.ThirtyOne();

		Root.ThirtyTwo thirtyTwo = new Root.ThirtyTwo("mixed");
		thirtyTwo.setTEXT("mixed");

		Root.ThirtyThree thirtyThree = new Root.ThirtyThree(createString());
		thirtyThree.setTEXT(createString());

		Root.ThirtyFour thirtyFour = new Root.ThirtyFour(createString());
		thirtyFour.setTEXT(createString());

		Root.ThirtyFive thirtyFive = new Root.ThirtyFive();

		Root.ThirtySix thirtySix = new Root.ThirtySix(createString());
		thirtySix.setTEXT(createString());

		Root.ThirtySeven thirtySeven = new Root.ThirtySeven();

		Root.ThirtyEight thirtyEight = new Root.ThirtyEight("mixed");
		thirtyEight.setTEXT("mixed");

		Root root = new Root();
		root.setZero(zero);
		root.setOneEx(oneEx);
		root.setOneRe(oneRe);
		root.setTwo(two);
		root.setThree(three);
		root.setFour(four);
		root.setFive(five);
		root.setSix(six);
		root.setSeven(seven);
		root.setEight(eight);
		root.setTen(ten);
		root.setElevenEx(elevenEx);
		root.setElevenRe(elevenRe);
		root.setTwelve(twelve);
		root.setThirteen(thirteen);
		root.setFourteen(fourteen);
		root.setFifteen(fifteen);
		root.setSixteen(sixteen);
		root.setSeventeen(seventeen);
		root.setEighteen(eighteen);
		root.setTwenty(twenty);
		root.setTwentyOne(twentyOne);
		root.setTwentyTwo(twentyTwo);
		root.setTwentyThree(twentyThree);
		root.setTwentyFour(twentyFour);
		root.setTwentyFive(twentyFive);
		root.setTwentySix(twentySix);
		root.setTwentySeven(twentySeven);
		root.setTwentyEight(twentyEight);
		root.setThirty(thirty);
		root.setThirtyOne(thirtyOne);
		root.setThirtyTwo(thirtyTwo);
		root.setThirtyThree(thirtyThree);
		root.setThirtyFour(thirtyFour);
		root.setThirtyFive(thirtyFive);
		root.setThirtySix(thirtySix);
		root.setThirtySeven(thirtySeven);
		root.setThirtyEight(thirtyEight);

		try
		{
			System.out.println(Bindings.domToString(root.marshal()));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private static String createString()
	{
		return "";
	}
}
