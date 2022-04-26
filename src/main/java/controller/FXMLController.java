package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
public class FXMLController {
    @FXML
    private VBox mainVBox;
    @FXML
    private VBox vBox;
    private Stage stage;
    @FXML
    private TableView<List<StringProperty>> stringTable;
    @FXML
    private VBox vBoxScrollPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField recherche;
    private final ObservableList<List<StringProperty>> data = FXCollections.observableArrayList();
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void initialiserVue(File file) {
        String line = "";
        final String delimiter = ",";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file.toString()));
            String[] lineOne = reader.readLine().split(delimiter);
            int nbCol = lineOne.length;
            for (int i = 0; i < nbCol; i++) {
                int finalI = i;
                TableColumn<List<StringProperty>, String> column = new TableColumn<>(lineOne[i].toUpperCase());
                column.setCellValueFactory(d -> d.getValue().get(finalI));
                column.getStyleClass().add("col");
                stringTable.getColumns().add(column);
                CheckBox checkBox = new CheckBox(lineOne[i].toUpperCase());
                checkBox.setSelected(true);
                checkBox.getStyleClass().add("checkbox");
                VBox.setMargin(checkBox, new Insets(10, 0, 0, 20));
                checkBox.setOnAction(event -> actionOnCheckBox(checkBox, finalI));
                vBoxScrollPane.getChildren().add(checkBox);
            }
            scrollPane.setFitToWidth(true);  //Adapt the content to the width of ScrollPane
            scrollPane.setFitToHeight(true); // to the height
            while ((line = reader.readLine()) != null) {
                List<StringProperty> row = new ArrayList<>();
                Arrays.stream(line.split(delimiter)).map(t -> row.add(new SimpleStringProperty(t))).collect(Collectors.toList());
                data.add(row);
            }
            stringTable.setItems(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void actionOnCheckBox(CheckBox checkBox, int i) {
        stringTable.getColumns().get(i).setVisible(checkBox.isSelected());
    }
    public void close(ActionEvent actionEvent) {
        stage.close();
    }
    public void research(ActionEvent actionEvent) {
        StringProperty motAChercher= new SimpleStringProperty(recherche.getText());
        ObservableList<List<StringProperty>> dataBis = FXCollections.observableArrayList();
        if(motAChercher.toString().equals(new SimpleStringProperty("").toString())){
            stringTable.setItems(data);
        }else {
            for (List<StringProperty> row : data) {
                for (StringProperty mot : row) {
                    if ((mot.toString()).equals(motAChercher.toString())) {
                        dataBis.add(row);
                    }
                }
            }
            stringTable.setItems(dataBis);
        }
    }
}
