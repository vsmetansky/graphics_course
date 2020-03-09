package app.components;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;


public class Axe implements Body {
    protected Body[] parts;

    public Axe(int x, int y, int width, int height) {
        parts = new Body[]{
                new Blade(x, y, height),
                new Handle(x, y, (int) (width * 0.9), height / 30)};
    }

    public void materialize(Graphics2D g2d) {
        for (Body p : parts) p.materialize(g2d);
    }
}

class Handle implements Body {
    private Rectangle2D base;

    Handle(int x, int y, int width, int height) {
        int xNorm = x - width / 2;
        int yNorm = y - height / 2;
        base = new Rectangle2D.Double(xNorm, yNorm, width, height);
    }

    public void materialize(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fill(base);
    }
}

class Blade implements Body {
    private Ellipse2D[] base;

    Blade(int x, int y, int size) {
        Ellipse2D biggerProto = new Ellipse2D.Double((int) (x - x), y, size / 1.5, size / 1.5);
        Ellipse2D smallerProto = new Ellipse2D.Double((int) (x - x), y, size / 1.6, size / 1.6);
        double radius = size / 2;
        base = new Ellipse2D[]{
                biggerProto,
                smallerProto
        };
    }

    public void materialize(Graphics2D g2d) {
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fill(base[0]);
        g2d.setColor(Color.GRAY);
        g2d.fill(base[1]);
    }

    private Area trimBladeProto(Ellipse2D proto, double radius) {
        Double subRadius = radius * 0.75;
        Area left = new Area(new Ellipse2D.Double(proto.getCenterX() - radius, proto.getCenterY(), subRadius, subRadius));
        Area right = new Area(new Ellipse2D.Double(proto.getCenterX() + radius, proto.getCenterY(), subRadius, subRadius));
        left.add(right);

        Area blade = new Area(proto);
        blade.subtract(left);

        return blade;
    }
}