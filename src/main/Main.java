package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import view.Frame;
import view.GameCanvas;
import view.MouseListener;

public class Main {

	public static void main(String[] args) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Frame frame = new Frame(screenSize);
		GameCanvas canvas = new GameCanvas(screenSize);
		MouseListener mouseListener = new MouseListener(canvas);
        canvas.addMouseWheelListener(mouseListener);
        canvas.addMouseMotionListener(mouseListener);
        frame.setContentPane(canvas);
	}

}
