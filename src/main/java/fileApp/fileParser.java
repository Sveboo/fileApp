package fileApp;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Character.toLowerCase;

/**
 * Reads text from input-file, count the number of letters(A-Z, a - z) and writes to output-file
 */
public class fileParser {
    /**
     * name input-file
     */
    private final String inputFileName;
    /**
     * name output-file
     */
    private final String outputFilename;
    /**
     * dictionary for writing letters and its number
     */
    private final Map<Character, Integer> dict;
    /**
     * error message
     */
    private String exceptMes;

    /**
     * Constructor with two parameters
     * @param input - name input-file
     * @param output - name output-file
     */
    fileParser(String input, String output){
        inputFileName = input;
        outputFilename = output;
        dict = new HashMap<>();
        exceptMes = "";
    }

    /**
     * Getter for error message
     * @return exceptMes
     */
    public String getErrorMessage(){
        return exceptMes;
    }

    /**
     * Getter for input-file
     * @return name input-file
     */
    public String getOutputFilename() {
        return outputFilename;
    }

    /**
     * Getter for output-file
     * @return name output-file
     */
    public String getInputFileName() {
        return inputFileName;
    }

    /**
     * Gets letters from input-file and counts it's number to dictionary(Character - Integer) of letters
     * @return true if success else false
     */
    public boolean getData() {
        File path = new File(inputFileName);

        try (BufferedReader reader
                     = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                    char[] arrChar = line.toCharArray();
                    for (char symbol : arrChar) {
                        if (toLowerCase(symbol) >= 'a' && toLowerCase(symbol) <= 'z') {
                            dict.put(symbol, dict.getOrDefault(symbol, 0) + 1);
                    }
                }
            }

            reader.close();
            return true;
        } catch (IOException e) {
            exceptMes = e.getMessage();
            return false;
        }
    }

    /**
     * Writes information from dictionary to output-file
     * @return true if success else false
     */
    public boolean fillData() {
        try (Writer writer = new BufferedWriter(new FileWriter(outputFilename, StandardCharsets.UTF_8))) {

            for (var element : dict.entrySet()) {
                String str_el = element.getKey() + " " + element.getValue() + "\n";
                writer.write(str_el, 0, str_el.length());
            }

            writer.flush();
            return true;

        } catch (IOException e) {
            exceptMes = e.getMessage();
            return false;
        }
    }
}
