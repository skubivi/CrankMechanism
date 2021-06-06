package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.ArrayList;

public class CrankMachine implements Serializable {
    private double w;
    private double r;
    private double l;
    private Circle roller;
    private Circle roller2;
    private Rectangle piston;
    private Line rod;
    private Line crank;
    ArrayList<Line> surfaces = new ArrayList<>();

    public double getW() {
        return w;
    }

    public double getR() {
        return r;
    }

    public double getL() {
        return l;
    }

    CrankMachine(double w, double r, double l, double width, double height) {
        double rectSize = Math.min(width - r * 2 - l, 150);
        double space = width - (rectSize + r * 2 + l);
        this.w = w;
        this.r = r;
        this.l = l;
        //Создание вала
        roller = new Circle(r);
        roller2 = new Circle(r - 5, Color.WHITE);
        roller.setCenterY(height / 2);
        roller.setCenterX(space / 2 + r);
        roller2.setCenterY(height / 2);
        roller2.setCenterX(space / 2 + r);
        //Создание поршня
        piston = new Rectangle(rectSize, rectSize);
        piston.setX(space / 2 + r * 2 + l);
        piston.setY((height - rectSize) / 2);
        //Создание шатуна и кривошипа
        rod = new Line(space / 2 + r * 2, height / 2, space / 2 + r * 2 + l, height / 2);
        crank = new Line(space / 2 + r, height / 2, space / 2 + r * 2, height / 2);

        double surfaceLength = 2 * r + rectSize;
        double surfaceStartX = roller.getCenterX() - r + l;
        double surfaceStartY = piston.getY() + piston.getHeight();
        double surfaceEndX = surfaceStartX + surfaceLength;
        double surfaceEndY = surfaceStartY;

        surfaces.add(new Line(surfaceStartX, surfaceStartY, surfaceEndX, surfaceEndY));
        for (double i = surfaceStartX; i < surfaceEndX; i += 5) {
            surfaces.add(new Line(i, surfaceStartY, Math.min(i + 10, surfaceEndX), Math.min(i + 10, surfaceEndX) - i + surfaceStartY));
        }
    }

    public ArrayList<Line> getSurfaces() {
        return surfaces;
    }

    public Circle getRoller() {
        return roller;
    }

    public Circle getRoller2() {
        return roller2;
    }

    public Rectangle getPiston() {
        return piston;
    }

    public Line getRod() {
        return rod;
    }

    public Line getCrank() {
        return crank;
    }

    public void move(double angle) {
        double x = r * Math.cos(angle) + Math.sqrt(l * l - Math.pow(r * Math.sin(angle), 2));
        piston.setX(roller.getCenterX() + x);
        crank.setEndX(roller.getCenterX() + Math.cos(angle) * r);
        crank.setEndY(roller.getCenterY() + Math.sin(angle) * r);
        rod.setEndX(roller.getCenterX() + x);
        rod.setStartX(crank.getEndX());
        rod.setStartY(crank.getEndY());
    }
}
