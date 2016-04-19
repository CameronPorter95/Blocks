package main;

import view.Frame;
import view.GameCanvas;
import view.MouseListener;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Frame frame = new Frame();
		GameCanvas canvas = new GameCanvas();
		MouseListener mouseListener = new MouseListener(canvas);
		//Register for mouse-wheel events on the canvas.
        canvas.addMouseWheelListener(mouseListener);
        frame.setContentPane(canvas);
	}

}
