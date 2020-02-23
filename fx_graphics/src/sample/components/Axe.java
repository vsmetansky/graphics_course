package sample.components;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public class Axe implements Body {
    protected Body[] parts;

    public Axe(int x, int y, int width, int height) {
        parts = new Body[]{
                new Blade(x, y, height),
                new Handle(x, y, (int) (width * 0.9), height / 30)};
    }

    public void materialize(Group root) {
        for (Body p : parts) p.materialize(root);
    }
}

class Handle implements Body {
    private Rectangle base;

    Handle(int x, int y, int width, int height) {
        int xNorm = x - width / 2;
        int yNorm = y - height / 2;
        base = new Rectangle(xNorm, yNorm, width, height);
        base.setFill(Color.BLACK);
    }

    public void materialize(Group root) {
        root.getChildren().add(base);
    }
}

class Blade implements Body {
    private Shape[] base;

    Blade(int x, int y, int size) {
        Circle biggerProto = new Circle((int) (x * 1.5), y, size / 2);
        Circle smallerProto = new Circle((int) (x * 1.5), y, size / 2.1);
        double radius = biggerProto.getRadius();
        base = new Shape[]{
                trimBladeProto(biggerProto, radius, Color.LIGHTGREY),
                trimBladeProto(smallerProto, radius, Color.GREY)
        };
    }

    public void materialize(Group root) {
        root.getChildren().addAll(base);
    }

    private Shape trimBladeProto(Circle proto, double radius, Color c) {
        Circle left = new Circle(proto.getCenterX() - radius, proto.getCenterY(), radius * 0.75);
        Circle right = new Circle(proto.getCenterX() + radius, proto.getCenterY(), radius * 0.75);
        Shape blade = Shape.subtract(proto, Shape.union(left, right));
        blade.setFill(c);
        return blade;
    }
}