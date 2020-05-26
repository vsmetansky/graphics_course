package app;

import app.components.Axe;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Base extends JPanel implements ActionListener {
    private static int maxWidth;
    private static int maxHeight;

    private double angle;
    private double tx, ty;
    private double dx = 2, dy = 2;
    private double txMax = 100, tyMax = 100;
    private Timer timer;

    public Base() {
        timer = new Timer(1, this);
        timer.start();
    }

    public static void main(String[] args) {
        JFrame frame = frameInit("Damn axe again", 1366, 768);

        frame.add(new Base());

        frame.setVisible(true);

        setDimensions(frame);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        renderingInit(g2d);

        g2d.setBackground(new Color(171, 11, 0));
        g2d.clearRect(0, 0, maxWidth, maxHeight);

        g2d.setStroke(new BasicStroke(5, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_BEVEL));
        g2d.drawRect(10, 10, maxWidth - 20, maxHeight - 20);

        Axe a = new Axe(maxWidth / 2, maxHeight / 2, (int) (maxWidth * 0.375), (int) (maxHeight * 0.45));
        
        a.animate(g2d, angle, tx, ty);
        a.materialize(g2d);
    }

    public void actionPerformed(ActionEvent e) {
        angle = angle - 0.01;
        if (tx > -txMax && ty == 0) {
            tx-= dx;
        } else if (tx == -txMax && ty == 0) {
            ty -= dy;
        } else if (tx > -txMax && ty == tyMax) {
            tx -= dx;
        } else if (ty > -tyMax && tx == -txMax) {
            ty -= dy;
        } else if (tx < txMax && ty == -tyMax) {
            tx += dx;
        } else if (ty < tyMax && tx == txMax) {
            ty += dy;
        }
        repaint();
    }

    private static void renderingInit(Graphics2D g2d) {
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
    }

    private static JFrame frameInit(String title, int width, int height) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        return frame;
    }

    private static void setDimensions(JFrame frame) {
        Dimension size = frame.getSize();
        Insets insets = frame.getInsets();
        maxWidth = size.width - insets.left - insets.right;
        maxHeight = size.height - insets.top - insets.bottom;
    }
}
