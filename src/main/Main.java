package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import Interaction.KeyboardInput;
import Interaction.MouseInput;
import view.Frame;
import view.GameCanvas;

public class Main {
	
	private Frame frame;
	private GameCanvas canvas;
	
	private static JLabel functionality = new JLabel();
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
	private static final String ZOOM_IN = "zoom in";
	private static final String ZOOM_OUT = "zoom out";
	private static final String CLEAR_SELECTION = "clear selection";
	private static final String SPACE = "space";
	
	private Main(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame = new Frame();
		canvas = new GameCanvas(screenSize);
		MouseInput mouseInput = new MouseInput(canvas);
		canvas.addMouseListener(mouseInput);
        canvas.addMouseWheelListener(mouseInput);
        canvas.addMouseMotionListener(mouseInput);
        canvas.setDoubleBuffered(true);
        frame.setContentPane(canvas); 
        frame.addSideBar();
        frame.setLayout(null);
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
		functionality.getInputMap(IFW).put(KeyStroke.getKeyStroke("SPACE"), SPACE);
		
		functionality.getActionMap().put(ZOOM_IN, new KeyboardInput("=", this.canvas));
		functionality.getActionMap().put(ZOOM_OUT, new KeyboardInput("Minus", this.canvas));
		functionality.getActionMap().put(CLEAR_SELECTION, new KeyboardInput("c", this.canvas));
		functionality.getActionMap().put(SPACE, new KeyboardInput("space", this.canvas));
		
		this.frame.add(functionality);
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
