package fileApp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import static java.lang.Character.toLowerCase;

public class SceneController {
    @FXML
    private TextField nameInputFile;
    @FXML
    private TextField nameOutputFile;

    @FXML
    private Button mainButton;

    @FXML
    private Label invalidDetails;

    public SceneController() {
    }

    public boolean isPathValid(TextField input) {
        try {
            Path path = Paths.get(input.getText());
            return !Files.isDirectory(path);
        } catch (InvalidPathException ex) {
            return false;
        }
    }

    void sendError(Exception e, String source){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Problem with open \"" + source + "\"");
        alert.setContentText(e.getMessage().replace(source, "").replaceAll("[()]", ""));

        alert.showAndWait();
    }

    private boolean getData(Map<Character, Integer> dict) {
        File path = new File(nameInputFile.getText());

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
            sendError(e, nameInputFile.getText());
            return false;
        }
    }

    private boolean fillData(Map<Character, Integer> dict) {
        try (Writer writer = new BufferedWriter(new FileWriter(nameOutputFile.getText(), StandardCharsets.UTF_8))) {

            for (var element : dict.entrySet()) {
                String str_el = element.getKey() + " " + element.getValue() + "\n";
                writer.write(str_el, 0, str_el.length());
            }

            writer.flush();
            return true;

        } catch (IOException e) {
            sendError(e, nameOutputFile.getText());
            return false;
        }
    }

    @FXML
    void onButtonClick() {
        invalidDetails.setText("");
        String normalStyle = "-fx-border-color: #A9A9A9;";
        nameOutputFile.setStyle(normalStyle);
        nameInputFile.setStyle(normalStyle);
        invalidDetails.setStyle(null);

        if (!isPathValid(nameInputFile) || !isPathValid(nameOutputFile)) {
            String errorMessage = "-fx-text-fill: RED;";
            invalidDetails.setStyle(errorMessage);
            invalidDetails.setText("Enter the valid path");
            String errorStyle = "-fx-border-color: RED;";
            if (!isPathValid(nameInputFile)) {
                nameInputFile.setStyle(errorStyle);
            }

            if (!isPathValid(nameOutputFile)) {
                nameOutputFile.setStyle(errorStyle);
            }
        } else {
            Map<Character, Integer> dict = new HashMap<>();

            if(getData(dict) && fillData(dict)){
                String successMessage = "-fx-text-fill: GREEN;";
                invalidDetails.setStyle(successMessage);
                invalidDetails.setText("Success!");
            }
        }
    }
}