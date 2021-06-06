package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Load {
    public static void start(ActionEvent event) throws Exception {
        File dir = new File(System.getProperty("user.dir"));
        List<File> lst = new ArrayList<>();
        for (File file : dir.listFiles()) {
            if (file.isFile() && file.getName().charAt(0) == 'w')
                lst.add(file);
        }

        FlowPane root = new FlowPane();
        root.setOrientation(Orientation.VERTICAL);
        root.setAlignment(Pos.TOP_CENTER);
        root.setHgap(10);
        root.setVgap(20);

        List<RadioButton> rBL = new ArrayList<>();
        ToggleGroup tG = new ToggleGroup();
        for (int i = 0; i < lst.size(); i++) {
            FlowPane file = new FlowPane();
            file.setOrientation(Orientation.HORIZONTAL);
            file.setAlignment(Pos.CENTER);
            file.setVgap(20);
            file.setHgap(10);
            rBL.add(new RadioButton());
            rBL.get(i).setToggleGroup(tG);
            Label text = new Label(lst.get(i).getName());
            file.getChildren().addAll(text, rBL.get(i));
            root.getChildren().add(file);
        }

        Button load = new Button("Загрузить");
        load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int c = -1;
                for(int i = 0; i < rBL.size(); i++){
                    if (rBL.get(i).isSelected()) c = i;
                }
                if(c!=-1) {
                    try {
                        Scanner in = new Scanner(Paths.get(lst.get(c).getName()));
                        in.useDelimiter(" ");
                        double w = Double.parseDouble(in.next());
                        double r = Double.parseDouble(in.next());
                        double l = Double.parseDouble(in.next());
                        Main.createSimulation(w, r, l, actionEvent);
                    } catch (Exception e){
                        System.out.println(e);
                    }
                }
            }
        });
        root.getChildren().add(load);

        if(rBL.size()!=0) {
            Stage stage = new Stage();
            stage.setTitle("Crank Mechanism Load");
            stage.setScene(new Scene(root, 400, 500));
            stage.show();

            ((Node) (event.getSource())).getScene().getWindow().hide();
        }
    }
}
