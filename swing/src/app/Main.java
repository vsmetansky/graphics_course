package app;

import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends JFrame implements ActionListener, KeyListener {
    Swing s;
    boolean rotate = false;

    public Main() {
        super("Swing");

        s = new Swing();

        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

        add(canvas3D);
        canvas3D.addKeyListener(this);

        Timer t = new Timer(50, this);
        t.start();

        BranchGroup scene = s.createSceneGraph();
        SimpleUniverse u = new SimpleUniverse(canvas3D);
        u.getViewingPlatform().setNominalViewingTransform();
        u.addBranchGraph(scene);

        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (rotate) {
            s.rotate();
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            rotate = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            rotate = false;
        }
    }
}
