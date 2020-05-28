package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.j3d.*;
import javax.swing.Timer;
import javax.vecmath.*;

public class RocketAnimation implements ActionListener, KeyListener {
    private TransformGroup rocket;
    private Transform3D transform3D = new Transform3D();

    private float x = 0;
    private float y = 0;

    private boolean w = false;
    private boolean s = false;
    private boolean a = false;
    private boolean d = false;

    public RocketAnimation(TransformGroup rocket) {
        this.rocket = rocket;
        this.rocket.getTransform(this.transform3D);

        Timer timer = new Timer(20, this);
        timer.start();
    }

    private void Move() {
        if (w) {
            y += 0.02f;
        }
        if (s) {
            y -= 0.02f;
        }
        if (a) {
            x -= 0.02f;
        }
        if (d) {
            x += 0.02f;
        }
        transform3D.setTranslation(new Vector3f(x, y, 0));


        rocket.setTransform(transform3D);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Move();
    }

    @Override
    public void keyPressed(KeyEvent ev) {
        switch (ev.getKeyChar()) {
            case 'w':
                w = true;
                break;
            case 's':
                s = true;
                break;
            case 'a':
                a = true;
                break;
            case 'd':
                d = true;
                break;

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent ev) {
        switch (ev.getKeyChar()) {
            case 'w':
                w = false;
                break;
            case 's':
                s = false;
                break;
            case 'a':
                a = false;
                break;
            case 'd':
                d = false;
                break;
        }
    }
}
