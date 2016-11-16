package ${package};

import java.util.Arrays;

import org.safris.commons.cli.Options;
import org.safris.commons.cli.OptionsException;
import org.safris.commons.lang.Resources;

public class Main {
  public static void main(final String[] args) throws OptionsException {
    final Options options = Options.parse(Resources.getResource("cli.xml").getURL(), Main.class, args);
    System.out.println("Colors: " + Arrays.toString(options.getOptions("colors")));
    System.out.println("Silent: " + options.getOption("silent"));
    System.out.println("Verbose: " + options.getOption("V"));
    System.out.println("Arguments: " + Arrays.toString(options.getArguments()));
  }
}