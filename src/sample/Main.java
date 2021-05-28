package sample;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    private static double w;
    private static double r;
    private static double l;

    final private static int WIDTH_SIM = 800;
    final private static int HEIGHT_SIM = 750;

    final private static int WIDTH_GRAPH = 400;
    final private static int HEIGHT_GRAPH = 250;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("Crank Mechanism");
        primaryStage.setScene(new Scene(root, 400, 500));
        primaryStage.show();
    }

    public static void startingSimulation(ActionEvent event) {
        //Создание корневого узла
        Group root = new Group();
        //Создание области для анимации
        Group simulation = new Group();
        //Создание области для первого графика
        Group graph1 = new Group();
        //Создание области для второго графика
        Group graph2 = new Group();
        //Создание области для третьего графика
        Group graph3 = new Group();

        //Создание кривошипно-шатунный механизма
        CrankMachine cM = new CrankMachine(w, r, l, WIDTH_SIM, HEIGHT_SIM);

        //Настройка области симуляции
        simulation.maxWidth(WIDTH_SIM);
        simulation.maxHeight(HEIGHT_SIM);
        simulation.setLayoutX(0);
        simulation.setLayoutY(0);
        simulation.getChildren().addAll(cM.getPiston(), cM.getRoller(), cM.getRoller2(), cM.getCrank(), cM.getRod());

        //Настройка области первого графика
        graph1.maxWidth(WIDTH_GRAPH);
        graph1.maxHeight(HEIGHT_GRAPH);
        graph1.setLayoutX(WIDTH_SIM);
        graph1.setLayoutY(0);
        //Настройка области второго графика
        graph1.maxWidth(WIDTH_GRAPH);
        graph1.maxHeight(HEIGHT_GRAPH);
        graph1.setLayoutX(WIDTH_SIM);
        graph1.setLayoutY(HEIGHT_GRAPH);
        //Настройка области третьего графика
        graph1.maxWidth(WIDTH_GRAPH);
        graph1.maxHeight(HEIGHT_GRAPH);
        graph1.setLayoutX(WIDTH_SIM);
        graph1.setLayoutY(HEIGHT_GRAPH * 2);

        //Добавление всех областей на главный экран
        root.getChildren().addAll(simulation, graph1, graph2, graph3);
        //Анимация механизма
        Animation anim = new Transition() {
            {
                setCycleDuration(Duration.seconds(2 * Math.PI / w));
                setCycleCount(INDEFINITE);
                setInterpolator(Interpolator.LINEAR);
            }

            @Override
            protected void interpolate(double v) {
                double angle = v * 2 * Math.PI;
                cM.move(angle);
            }
        };
        anim.play();

        Stage stage = new Stage();
        stage.setTitle("Crank Mechanism Simulation");
        stage.setScene(new Scene(root, 1200, 750));
        stage.show();


        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public static void createSimulation(TextField wT, TextField rT, TextField lT, ActionEvent event) {
        try {
            double n = Double.parseDouble(wT.getCharacters().toString());
            w = 2 * Math.PI * n / 60;
            r = Double.parseDouble(rT.getCharacters().toString());
            l = Double.parseDouble(lT.getCharacters().toString());
            if (l < 2 * r) throw new NumberFormatException();

            startingSimulation(event);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Введите коректные данные");

            alert.showAndWait();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
