package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class CrankMachine {
    private double w;
    private double r;
    private double l;
    private double x;
    private double y;
    private Circle roller;
    private Circle roller2;
    private Rectangle piston;
    private Line rod;
    private Line crank;
    CrankMachine(double w, double r, double l, double x, double y){
        double rectSize = Math.min(x - r * 2 - l, 150);
        double space = x - (rectSize + r * 2 + l);
        this.w = w;
        this.r = r;
        this.l = l;
        this.x = x;
        this.y = y;
        //Создание вала
        roller = new Circle(r);
        roller2 = new Circle(r - 5, Color.WHITE);
        roller.setCenterY(y / 2);
        roller.setCenterX(space / 2 + r);
        roller2.setCenterY(y / 2);
        roller2.setCenterX(space / 2 + r);
        //Создание поршня
        piston = new Rectangle(rectSize, rectSize);
        piston.setX(space / 2 + r * 2 + l);
        piston.setY((y - rectSize) / 2);
        //Создание шатуна и кривошипа
        rod = new Line(space / 2 + r * 2, y / 2, space / 2 + r * 2 + l, y / 2);
        crank = new Line(space / 2 + r, y / 2, space / 2 + r * 2, y / 2);
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

    public void move(double angle){
        double x = r * Math.cos(angle) + Math.sqrt(l * l - Math.pow(r * Math.sin(angle),2));
        piston.setX(roller.getCenterX() + x);
        crank.setEndX(roller.getCenterX() + Math.cos(angle) * r);
        crank.setEndY(roller.getCenterY() + Math.sin(angle) * r);
        rod.setEndX(roller.getCenterX() + x);
        rod.setStartX(crank.getEndX());
        rod.setStartY(crank.getEndY());
    }

}
