package com.mattnworb.calculator.cli;

import com.mattnworb.calculator.Calculator;
import java.util.List;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public final class Main {
  private Main() {}

  public static void main(String[] args) {
    ArgumentParser parser =
        ArgumentParsers.newFor("calculator").build().defaultHelp(true).description("adds numbers");

    parser.addArgument("numbers").help("numbers to add").nargs("+").type(Integer.class);

    Namespace ns = null;
    try {
      ns = parser.parseArgs(args);
    } catch (ArgumentParserException e) {
      parser.handleError(e);
      System.exit(1);
      return;
    }

    List<Integer> numbers = ns.getList("numbers");

    Calculator calculator = new Calculator();
    int sum = calculator.add(numbers.stream().mapToInt(i -> i).toArray());

    // join the list on " + "
    String joinedNumbers = String.join(" + ", numbers.stream().map(Object::toString).toList());

    System.out.printf("%s = %d%n", joinedNumbers, sum);
  }
}
