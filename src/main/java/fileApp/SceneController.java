package fileApp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.nio.file.*;

public class SceneController {
    @FXML
    private TextField nameInputFile;
    @FXML
    private TextField nameOutputFile;

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

    void sendError(String exceptMes, String source){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Problem with open \"" + source + "\"");
        alert.setContentText(exceptMes.replace(source, "").replaceAll("[()]", ""));

        alert.showAndWait();
    }

    private void setNormalStyle(){
        invalidDetails.setText("");
        String normalStyle = "-fx-border-color: #A9A9A9;";
        nameOutputFile.setStyle(normalStyle);
        nameInputFile.setStyle(normalStyle);
        invalidDetails.setStyle(null);
    }

    private void setErrorStyle(){
        String errorMessage = "-fx-text-fill: RED;";
        String errorStyle = "-fx-border-color: RED;";

        invalidDetails.setStyle(errorMessage);
        invalidDetails.setText("Enter the valid path");

        if (!isPathValid(nameInputFile)) {
            nameInputFile.setStyle(errorStyle);
        }

        if (!isPathValid(nameOutputFile)) {
            nameOutputFile.setStyle(errorStyle);
        }
    }

    @FXML
    void onButtonClick() {
        setNormalStyle();

        if (!isPathValid(nameInputFile) || !isPathValid(nameOutputFile)) {
            setErrorStyle();
        } else {
            fileParser parser = new fileParser(nameInputFile.getText(), nameOutputFile.getText());
            if(parser.getData()){
                if(parser.fillData()){
                    String successMessage = "-fx-text-fill: GREEN;";
                    invalidDetails.setStyle(successMessage);
                    invalidDetails.setText("Success!");
            }else{sendError(parser.getErrorMessage(), parser.getInputFileName());}

            } else{sendError(parser.getErrorMessage(), parser.getOutputFilename());}
        }
    }
}