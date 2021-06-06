package sample;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileWriter;

public class Main extends Application {
    private static double w;
    private static double r;
    private static double l;

    final private static int WIDTH_SIM = 800;
    final private static int HEIGHT_SIM = 600;

    final private static int WIDTH_GRAPH = 700;
    final private static int HEIGHT_GRAPH = 600;

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
        //Создание области для графика
        Group graphG = new Group();

        //Создание кривошипно-шатунного механизма
        CrankMachine cM = new CrankMachine(w, r, l, WIDTH_SIM, HEIGHT_SIM);
        //Кнопка сохранения
        Button save = new Button("Сохранить");
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    double n = w / 2 / Math.PI * 60;
                    FileWriter fW = new FileWriter("w" + n + "_r" + r + "_l" + l);
                    fW.write(cM.getW() + " " + cM.getR() + " " + cM.getL());
                    fW.flush();
                    fW.close();
                } catch (Exception e) {
                }
            }
        });
        //Создание графика
        Graph graph = new Graph(r, l);

        //Настройка области симуляции
        simulation.maxWidth(WIDTH_SIM);
        simulation.maxHeight(HEIGHT_SIM);
        simulation.setLayoutX(0);
        simulation.setLayoutY(0);
        simulation.getChildren().addAll(cM.getPiston(), cM.getRoller(), cM.getRoller2(), cM.getCrank(), cM.getRod());
        for (int i = 0; i < cM.getSurfaces().size(); i++) {
            simulation.getChildren().add(cM.getSurfaces().get(i));
        }
        simulation.getChildren().add(save);

        //Настройка области графика
        graphG.maxWidth(WIDTH_GRAPH);
        graphG.maxHeight(HEIGHT_GRAPH);
        graphG.setLayoutX(WIDTH_SIM);
        graphG.setLayoutY(0);
        graphG.getChildren().addAll(graph.getChart());

        //Добавление всех областей на главный экран
        root.getChildren().addAll(simulation, graphG);
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

        //Создание нового окна
        Stage stage = new Stage();
        stage.setTitle("Crank Mechanism Simulation");
        stage.setScene(new Scene(root, 1500, 600));
        stage.show();

        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public static void createSimulation(double wT, double rT, double lT, ActionEvent event) {
        try {
            w = wT;
            r = rT;
            l = lT;
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
