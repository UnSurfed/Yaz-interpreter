// Angelino dela Cruz
// A6: Yaz Interpreter
// Period 3
// 3/29/2021

/*
This assignment is the Yaz Interpreter. Basically you need to 
read and scan files that are inputed by/from the user while also printing
the results of the translated file onto a txt file and onto the console.
The assignment uses past functions/code from previous assignments like
scanners, while loops, for loops, if else statements. And new elements
such as using the scanner to read/scan files. There is also a good amount of 
String manipulation containing substrings and string.replace, the file type 
is also introduced which can be changed through strings or manually
putting the fileName. PrintStream is also used, taking in a file it can 
act similarly like the console but you can print anything onto a file 
using the PrintStream.
*/

import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) throws FileNotFoundException {
    
    introductionToInterpreter();
     char input = getInput();
    while (input != 'q') {
      File inputFile = getFile();
      if (input == 'v') {
        viewFile(inputFile);
      } else if (input == 'i') {
        interpretFile(inputFile);
      }
      input = getInput(); // user can choose which char to pick
    }
  }

  public static void introductionToInterpreter() {
    System.out.println("Welcome to YazInterpreter!");
    System.out.println("You may interpret a YazLang program and output");
    System.out.println("the results to a file or view a previously");
    System.out.println("interpreted YazLang program.");
    System.out.println();
  }

  public static char getInput() {
    Scanner scn = new Scanner(System.in);
    System.out.print("(I)nterpret .yzy program, (V)iew .yzy output, (Q)uit? ");
    char answer = scn.next().charAt(0); // trims the answer to the first char (i,v or q)
    return answer;
  }

  public static File getOutputFile() {
    Scanner scn = new Scanner(System.in);
    System.out.print("Output file name: ");
    String fileName = scn.next(); // can ask for fileName
    File f = new File(fileName);
    System.out.println("YazLang interpreted and output to a file!");
    System.out.println();
    return f;
  }

  public static File getFile() {
    Scanner scn = new Scanner(System.in);
    System.out.print("Input file name: ");
    String fileName = scn.next();
    File f = new File(fileName); // can ask for fileName as long as it is in
                                 // the directory
    while (!f.exists()) { // if the file isn't found in directory or does not exist
      System.out.print("File not found. Try again: ");
      fileName = scn.next();
      f = new File(fileName);
    }
    return f;
  }

  public static void viewFile(File inputFile) throws FileNotFoundException {
    Scanner fileScn = new Scanner(inputFile);
    while (fileScn.hasNextLine()) {
      System.out.println(fileScn.nextLine());
    }
    fileScn.close();
  }

  public static void interpretFile(File inputFile) throws FileNotFoundException {
    Scanner fileScn = new Scanner(inputFile); // scanner for file
    File outputFile = getOutputFile(); // ask for output file for printstream
    PrintStream print = new PrintStream(outputFile);
    while (fileScn.hasNextLine()) { // while there is a next line
      String line = fileScn.nextLine(); // scan next line
      Scanner lineScan = new Scanner(line); // scanner that scans tokens in a single line
      String condition = lineScan.next(); // string which scans (convert,repeat,range)
      String lineResult = ""; // left empty every time it loops back

      // below is a if else statement that checks each condition
      if (condition.equals("CONVERT")) {

        lineResult = convert(lineScan);

      } else if (condition.equals("RANGE")) {

        lineResult = range(lineScan);

      } else {

        lineResult = repeat(lineScan);

      }

      print.println(lineResult);

    }
    print.close();
  }

  // the following code below are the functions for the conditions
  // "String output" is usually left empty for the start of each loop.

  // this is the convert function
  public static String convert(Scanner lineScan) {
    String output = "";
    int amountOfDegrees = lineScan.nextInt(); // first number in line
    String cOrF = lineScan.next(); // scans for C/c or F/f

    if (cOrF.equals("f") || cOrF.equals("F")) { // converts farenheit to celcius
      double degrees = (amountOfDegrees - 32) / 1.8;
      int integerDegrees = (int) degrees;
      output += integerDegrees + "C";
    } else { // does the opposite of previous statement
      double degrees = 1.8 * amountOfDegrees + 32;
      int integerDegrees = (int) degrees;
      output += integerDegrees + "F";
    }
    
    return output;
  }

  // this is the range function
  public static String range(Scanner lineScan) {
    String sequenceOfNumbers = "";

    int startingNumber = lineScan.nextInt();
    int secondNumber = lineScan.nextInt();
    int numberToBeIncrementedBy = lineScan.nextInt();

    for (int i = startingNumber; i < secondNumber; i += numberToBeIncrementedBy) { // for loop, starts when
      // scanner gets the first number, max is the second number scanned,
      // and first number is incremented by the third number
      sequenceOfNumbers += (i + " ");
    }

    return sequenceOfNumbers;
  }

  // this is the repeat function
  public static String repeat(Scanner lineScan) {
    String outputStrings = "";
    while (lineScan.hasNext()) {
      String repeatName = lineScan.next(); // scans the strings in the file
      repeatName = repeatName.substring(1, repeatName.length() - 1); // takes out the outside quotation marks but
      // keeps the ones inside
      repeatName = repeatName.replace("_", " "); // replaces "_" with a space
      int numberOfTimes = lineScan.nextInt(); // scans the integer for how many times that string is repeated/printed
      for (int i = 0; i < numberOfTimes; i++) { // for loop that repeats the string
        outputStrings += repeatName;
      }
    }
    return outputStrings;
  }
}