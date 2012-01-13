/*  Copyright Safris Software 2006
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.safris.xml.toolkit.test.binding;

import org.junit.Test;
import org.safris.xml.schema.binding.test.unit.everything.$ev_complexTypie;
import org.safris.xml.schema.binding.test.unit.everything.$ev_complexTypie_original;
import org.safris.xml.schema.binding.test.unit.everything.$ev_simpleTypie;
import org.safris.xml.schema.binding.test.unit.everything.$ev_simpleTypie_original;

public class RedefineTest {
  public static void main(String[] args) {
    new RedefineTest().testComplexTypie();
    new RedefineTest().testSimpleTypie();
  }

  @Test
  public void testComplexTypie() {
    $ev_complexTypie_original original = new $ev_complexTypie_original()
    {
      protected $ev_complexTypie_original inherits() {
        return null;
      }
    };

    original.setattr_test_attribute_nested1$(null);
    original.setattr_test_attribute_type$(null);
    original.addty_test_type_NCName(null);
    original.addty_test_type_QName(null);
    original.addAny(null);                  // in <redefine/> of groupie
    original.addAny$(null);                 // in <redefine/> of attrGroupie

    $ev_complexTypie redefined = new $ev_complexTypie()
    {
      protected $ev_complexTypie inherits() {
        return null;
      }
    };

    redefined.setattr_test_attribute_nested1$(null);
    redefined.setattr_test_attribute_type$(null);
    redefined.addty_test_type_NCName(null);
    redefined.addty_test_type_QName(null);
    redefined.addAny(null);                 // in <redefine/> of groupie
    redefined.addAny$(null);                // in <redefine/> of attrGroupie
    redefined.set_redefineTest$(null);      // in <redefine/> of complexTypie
  }

  @Test
  public void testSimpleTypie() {
    $ev_simpleTypie_original original = new $ev_simpleTypie_original()
    {
      protected $ev_simpleTypie_original inherits() {
        return null;
      }
    };

    original.setText($ev_simpleTypie_original._1);
    original.setText($ev_simpleTypie_original._2);
    original.setText($ev_simpleTypie_original._3);

    $ev_simpleTypie redefined = new $ev_simpleTypie()
    {
      protected $ev_simpleTypie inherits() {
        return null;
      }
    };

    redefined.setText($ev_simpleTypie._1);
//      redefined.setText($ev_simpleTypie._2);  // removed in <redefine/> of simpleTypie
    redefined.setText($ev_simpleTypie._3);
  }
}
