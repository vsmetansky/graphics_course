package app;

import app.components.Axe;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Base extends JPanel {
    private static int maxWidth;
    private static int maxHeight;

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        renderingInit(g2d);

        g2d.setBackground(Color.RED);
        g2d.clearRect(0, 0, maxWidth, maxHeight);

        Axe a = new Axe(maxWidth / 2, maxHeight / 2, (int) (maxWidth * 0.75), (int) (maxHeight * 0.9));
        a.materialize(g2d);
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

    public static void main(String[] args) {
        JFrame frame = frameInit("Damn axe again", 1366, 768);

        frame.add(new Base());

        frame.setVisible(true);

        setDimensions(frame);
    }
}
