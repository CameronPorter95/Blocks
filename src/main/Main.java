package main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;

import view.Frame;
import view.GameCanvas;
import view.KeyboardInput;
import view.MouseControl;

public class Main {
	
	private GameCanvas canvas;
	private Frame frame;
	
	private static JLabel functionality = new JLabel();
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
	private static final String ZOOM_IN = "zoom in";
	private static final String ZOOM_OUT = "zoom out";
	
	private Main(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.frame = new Frame();
		this.canvas = new GameCanvas(screenSize);
		MouseControl mouseControl = new MouseControl(canvas);
		this.canvas.addMouseListener(mouseControl);
        this.canvas.addMouseWheelListener(mouseControl);
        this.canvas.addMouseMotionListener(mouseControl); 
        this.frame.setContentPane(canvas);
        keyBindings();
	}

	/**
	 * Binds the given keyboard inputs to actions so pressing the key calls
	 * a new move action.
	 */
	private void keyBindings() {
		functionality.getInputMap(IFW).put(KeyStroke.getKeyStroke("EQUALS"), ZOOM_IN);
		functionality.getInputMap(IFW).put(KeyStroke.getKeyStroke("MINUS"), ZOOM_OUT);
		
		functionality.getActionMap().put(ZOOM_IN, new KeyboardInput("=", this.canvas));
		functionality.getActionMap().put(ZOOM_OUT, new KeyboardInput("Minus", this.canvas));
		
		this.frame.add(functionality);
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
