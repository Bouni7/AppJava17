package pnt;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MonApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Controller controller = new Controller(stage);
        controller.run();
    }
    public static void main(String[] args) {
        launch();
    }
}
