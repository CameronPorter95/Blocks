package main;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import Interaction.KeyboardInput;
import Interaction.MouseInput;
import state.Database;
import view.Frame;
import view.GameCanvas;
import view.SideBar;

public class Main {
	
	private Frame frame;
	private GameCanvas canvas;
	private SideBar sideBar;
	
	private static JLabel functionality = new JLabel();
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
	private static final String ZOOM_IN = "zoom in";
	private static final String ZOOM_OUT = "zoom out";
	private static final String CLEAR_SELECTION = "clear selection";
	private static final String SPACE = "space";
	private static final String TAB = "tab";
	
	private Main(){
		Database database = new Database();
		frame = new Frame();
		canvas = new GameCanvas(frame.getSize(), database);
		sideBar = new SideBar(frame.getSize(), canvas);
		MouseInput mouseInput = new MouseInput(canvas);
		canvas.addMouseListener(mouseInput);
        canvas.addMouseWheelListener(mouseInput);
        canvas.addMouseMotionListener(mouseInput);
        canvas.setDoubleBuffered(true);
        frame.setContentPane(canvas);
		frame.setSideBar(sideBar);
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
		functionality.getInputMap(IFW).put(KeyStroke.getKeyStroke("TAB"), TAB);
		
		functionality.getActionMap().put(ZOOM_IN, new KeyboardInput("=", this.canvas));
		functionality.getActionMap().put(ZOOM_OUT, new KeyboardInput("Minus", this.canvas));
		functionality.getActionMap().put(CLEAR_SELECTION, new KeyboardInput("c", this.canvas));
		functionality.getActionMap().put(SPACE, new KeyboardInput("space", this.canvas));
		functionality.getActionMap().put(TAB, new KeyboardInput("tab", this.frame, this.sideBar));
		
		this.frame.add(functionality);
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
