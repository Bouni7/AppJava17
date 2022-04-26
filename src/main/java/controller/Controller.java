package controller;
import javafx.stage.Stage;
import vues.Menu;

public class Controller {
    private Menu menu;
    public Controller(Stage stage) {
        menu = Menu.creerVue(this,stage);}
    private void showMenu() {
      menu.show();
    }
    public void run() {
        showMenu();
    }
    public void gotoMenu() {
        showMenu();
    }
}
