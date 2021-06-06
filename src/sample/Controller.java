package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
        double n = Double.parseDouble(wT.getCharacters().toString());
        double w = 2 * Math.PI * n / 60;
        double r = Double.parseDouble(rT.getCharacters().toString());
        double l = Double.parseDouble(lT.getCharacters().toString());
        Main.createSimulation(w, r, l, event);
    }

    @FXML
    private void load(ActionEvent event) {
        try {
            Load.start(event);
        } catch (Exception e) {

        }
    }
}
