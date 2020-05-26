package app.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

public class Axe implements Body {
    protected Body[] parts;

    public Axe(int x, int y, int width, int height) {
        parts = new Body[]{
                new Leg((int) (x - width / 2.5), y, (int) (width * 0.1), height / 4),
                new Blade(x, y, height),
                new Handle(x, y, (int) (width * 0.9), height / 30)};
    }

    public void materialize(Graphics2D g2d) {
        for (Body p : parts) p.materialize(g2d);
    }

    public void animate(Graphics2D g2d, double angle, double x, double y) {
        for (Body p : parts) p.animate(g2d, angle, x, y);
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

    public void animate(Graphics2D g2d, double angle, double x, double y) {
        g2d.translate(x, y);
        g2d.rotate(angle, base.getBounds2D().getCenterX(),
                base.getBounds2D().getCenterY());
    }
}

class Blade implements Body {
    private Area[] base;

    Blade(int x, int y, int size) {
        double bigRadius = size / 2.2;
        double smallRadius = size / 2.3;

        double[] bigCoords = {(x + bigRadius) * 0.85, y - bigRadius};
        double[] smallCoords = {(x + bigRadius) * 0.85 + (bigRadius - smallRadius), y - smallRadius};
        Ellipse2D biggerProto = new Ellipse2D.Double(bigCoords[0], bigCoords[1], 2 * bigRadius, 2 * bigRadius);
        Ellipse2D smallerProto = new Ellipse2D.Double(smallCoords[0], smallCoords[1], 2 * smallRadius, 2 * smallRadius);

        base = new Area[]{
                trimBladeProto(biggerProto, bigRadius),
                trimBladeProto(smallerProto, bigRadius)
        };
    }

    public void materialize(Graphics2D g2d) {
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fill(base[0]);
        Rectangle2D baseRect = base[1].getBounds2D();
        g2d.setPaint(new GradientPaint(
                (float) baseRect.getCenterX(), (float) baseRect.getMinY(), new Color(217, 217, 217),
                (float) baseRect.getCenterX(), (float) baseRect.getCenterY(), new Color(120, 120, 120),
                true));
        g2d.fill(base[1]);
    }

    private Area trimBladeProto(Ellipse2D proto, double radius) {
        Double subRadius = radius * 0.75;
        Area left = new Area(new Ellipse2D.Double((proto.getCenterX() - radius - subRadius),
                proto.getCenterY() - subRadius, 2 * subRadius, 2 * subRadius));
        Area right = new Area(new Ellipse2D.Double(proto.getCenterX() - (2 * subRadius - radius) + subRadius,
                proto.getCenterY() - subRadius, 2 * subRadius, 2 * subRadius));

        Area blade = new Area(proto);
        blade.subtract(left);
        blade.subtract(right);

        return blade;
    }

    public void animate(Graphics2D g2d, double angle, double x, double y) {
//        for (Area b: base) {
//            g2d.rotate(angle, b.getBounds2D().getCenterX(),
//                    b.getBounds2D().getCenterY());
//        }
    }
}

class Leg implements Body {
    private Shape[] base;

    public Leg(int x, int y, int width, int height) {
        int xNorm = x - width / 2;
        int yNorm = y - height / 2;

        double points[][] = {
                {xNorm, yNorm},
                {xNorm + width / 2, yNorm + width / 2},
                {xNorm + width, yNorm}
        };

        GeneralPath triangle = new GeneralPath();
        triangle.moveTo(points[0][0], points[0][1]);
        for (int k = 1; k < points.length; k++)
            triangle.lineTo(points[k][0], points[k][1]);
        triangle.closePath();

        base = new Shape[]{
                new Rectangle2D.Double(xNorm, yNorm, width, height),
                triangle
        };

    }

    public void materialize(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fill(base[0]);
        g2d.setPaint(Color.DARK_GRAY);
        g2d.fill(base[1]);
    }

    public void animate(Graphics2D g2d, double angle, double x, double y) {
//        for (Shape b: base) {
//            g2d.rotate(angle, b.getBounds2D().getCenterX(),
//                    b.getBounds2D().getCenterY());
//        }
    }
}