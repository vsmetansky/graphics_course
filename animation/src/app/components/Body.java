package app.components;

import java.awt.*;

public interface Body {
    public void materialize(Graphics2D g2d);
    public void animate(Graphics2D g2d, double angle, double x, double y);
}
