package vues;
import controller.Controller;
import controller.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

public class Menu extends Vue implements VueInteractive {
    private Controller controller;
    private File file;
    public Parent getTop() {
        return mainVbox;
    }
    @FXML
    private VBox mainVbox;
    @FXML
    private TextField fichier;
    private final ResourceBundle bundle = ResourceBundle.getBundle("strings");
    public static Menu creerVue(Controller controller, Stage stage) {
        ResourceBundle bundle = ResourceBundle.getBundle("strings");
        FXMLLoader fxmlLoader = new FXMLLoader(Menu.class.getResource("menu.fxml"), bundle);
        try {fxmlLoader.load();
        } catch (IOException e) {System.out.println("Probleme de construction de vue");}
        Menu vue = fxmlLoader.getController();
        vue.setControleur(controller);
        vue.setStage(stage);
        stage.setTitle(bundle.getString("titleMenu"));
        vue.setScene(new Scene(vue.getTop()));
        return vue;
    }
    public void browse(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        file = fileChooser.showOpenDialog(getStage());
        if (file == null) {fichier.setText(bundle.getString("erreurFichierNull"));
        }else {
            fichier.setText(file.toString());}
    }
    public void execute(ActionEvent actionEvent) {
            if (file == null) {
                fichier.setText(bundle.getString("erreurFichierNull"));
        } else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fileSort.fxml"),bundle);
                Parent root1 = null;
                try {
                    root1 = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FXMLController fxmlController = fxmlLoader.getController();
                Stage stage = new Stage();
                stage.setTitle(bundle.getString("titleWindow"));
                fxmlController.setStage(stage);
                fxmlController.initialiserVue(file);
                if (root1 != null) {
                    stage.setScene(new Scene(root1));
                }
                stage.show();
            }
    }
    @Override
    public void setControleur(Controller controller) {
        this.controller = controller;
    }
}