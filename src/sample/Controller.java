package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private TextField wT;
    @FXML
    private TextField rT;
    @FXML
    private TextField lT;

    @FXML
    private void click(ActionEvent event) {
        Main.createSimulation(wT, rT, lT, event);
    }
}
