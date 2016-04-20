package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import view.Frame;
import view.GameCanvas;
import view.MouseControl;

public class Main {

	public static void main(String[] args) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Frame frame = new Frame();
		GameCanvas canvas = new GameCanvas(screenSize);
		MouseControl mouseControl = new MouseControl(canvas);
		canvas.addMouseListener(mouseControl);
        canvas.addMouseWheelListener(mouseControl);
        canvas.addMouseMotionListener(mouseControl);
        frame.setContentPane(canvas);
	}

}
