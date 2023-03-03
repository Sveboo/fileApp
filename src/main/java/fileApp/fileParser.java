package fileApp;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Character.toLowerCase;

public class fileParser {
    private final String inputFileName;

    private final String outputFilename;

    private final Map<Character, Integer> dict;

    private String exceptMes;


    fileParser(String input, String output){
        inputFileName = input;
        outputFilename = output;
        dict = new HashMap<>();
        exceptMes = "";
    }

    public String getErrorMessage(){
        return exceptMes;
    }

    public String getOutputFilename() {
        return outputFilename;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public boolean getData() {
        File path = new File(inputFileName);

        try (BufferedReader reader
                     = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                for (int i = 0; i < line.length(); ++i) {
                    char symbol = line.charAt(i);
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