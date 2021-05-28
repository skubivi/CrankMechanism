package sample;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Graph {
    private double r;
    private double l;
    private NumberAxis x;
    private NumberAxis y;
    private LineChart<Number, Number> chart;
    private CrankMachine cM;
    private XYChart.Series<Number, Number> series1;
    private XYChart.Series<Number, Number> series2;
    private XYChart.Series<Number, Number> series3;

    Graph(double r, double l) {
        this.r = r;
        this.l = l;
        x = new NumberAxis(-360, 360, 45);
        y = new NumberAxis();
        chart = new LineChart<Number, Number>(x, y);
        chart.setTitle("Положение, скорость и ускорение поршня относительно угла поворота");
        x.setLabel("Угол поворота");
        y.setLabel("Значение");
        series1 = new XYChart.Series<>();
        series1.setName("Положение поршня");
        series2 = new XYChart.Series<>();
        series2.setName("Скорость поршня");
        series3 = new XYChart.Series<>();
        series3.setName("Ускорение поршня");
        for(int i = -360; i < 360; i++){
            double angle = (double)i / 360 * 2 * Math.PI;
            series1.getData().add(new XYChart.Data<>(i, calcPos(angle)));
            series2.getData().add(new XYChart.Data<>(i, calcVel(angle)));
            series3.getData().add(new XYChart.Data<>(i, calcAc(angle)));
        }
        chart.getData().addAll(series1, series2, series3);
    }

    private double calcPos(double angle) {
        return (r * Math.cos(angle) + Math.sqrt(l * l - Math.pow(r * Math.sin(angle), 2)));
    }

    private double calcVel(double angle) {
        double v = -r * Math.sin(angle);
        double numerator = r * r * Math.sin(angle) * Math.cos(angle);
        double denominator = Math.sqrt(l * l - r * r * Math.pow(Math.sin(angle), 2));
        v += numerator / denominator;
        return v;
    }

    private double calcAc(double angle) {
        double a = -r * Math.cos(angle);
        double numerator1 = r * r * (Math.pow(Math.cos(angle), 2) - Math.pow(Math.sin(angle), 2));
        double denominator1 = Math.sqrt(l * l - r * r * Math.pow(Math.sin(angle), 2));
        double numerator2 = Math.pow(r, 4) * Math.pow(Math.sin(angle), 2) * Math.pow(Math.cos(angle), 2);
        double denominator2 = Math.pow(Math.sqrt(l * l - r * r * Math.pow(Math.sin(angle), 2)), 3);
        a += numerator1 / denominator1 + numerator2 / denominator2;
        return a;
    }

    public LineChart<Number, Number> getChart() {
        return chart;
    }
}
