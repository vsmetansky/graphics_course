package cg.lab4;

import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends JFrame implements ActionListener, KeyListener {
	Swing swing;
	boolean rotate = false;

	public Main() {
		// Frame title
		super("Swing");

		swing = new Swing();

		Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

		add(canvas3D);
		canvas3D.addKeyListener(this);

		Timer timer = new Timer(50, this);
		timer.start();
		BranchGroup scene = swing.createSceneGraph();
		SimpleUniverse u = new SimpleUniverse(canvas3D);
		u.getViewingPlatform().setNominalViewingTransform();
		u.addBranchGraph(scene);

		setSize(600, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Main();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(rotate) {
			swing.rotate();
		}
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {

	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		if(keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
			rotate = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		if(keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
			rotate = false;
		}
	}
}
