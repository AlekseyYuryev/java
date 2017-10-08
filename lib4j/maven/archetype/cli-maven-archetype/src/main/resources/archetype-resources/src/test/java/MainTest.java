package ${package};

import org.junit.Test;
import org.safris.commons.cli.OptionsException;

public class MainTest {
  @Test
  public void testOptions() throws OptionsException {
    Main.main(new String[] {"--colors", "red,blue,orange", "--silent", "file1.txt", "file1.txt", "file1.txt"});
    Main.main(new String[0]);
  }
}