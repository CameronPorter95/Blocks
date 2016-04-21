package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import view.Frame;
import view.GameCanvas;
import view.KeyboardInput;
import view.MouseInput;

public class Main {
	
	private GameCanvas canvas;
	private Frame frame;
	
	private static JLabel functionality = new JLabel();
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
	private static final String ZOOM_IN = "zoom in";
	private static final String ZOOM_OUT = "zoom out";
	private static final String CLEAR_SELECTION = "clear selection";
	
	private Main(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.frame = new Frame();
		this.canvas = new GameCanvas(screenSize);
		MouseInput mouseInput = new MouseInput(canvas);
		this.canvas.addMouseListener(mouseInput);
        this.canvas.addMouseWheelListener(mouseInput);
        this.canvas.addMouseMotionListener(mouseInput);
        this.canvas.setDoubleBuffered(true);
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
		functionality.getInputMap(IFW).put(KeyStroke.getKeyStroke("C"), CLEAR_SELECTION);
		
		functionality.getActionMap().put(ZOOM_IN, new KeyboardInput("=", this.canvas));
		functionality.getActionMap().put(ZOOM_OUT, new KeyboardInput("Minus", this.canvas));
		functionality.getActionMap().put(CLEAR_SELECTION, new KeyboardInput("c", this.canvas));
		
		this.frame.add(functionality);
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
