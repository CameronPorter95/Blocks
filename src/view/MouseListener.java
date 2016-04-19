package view;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseListener implements MouseWheelListener{

	GameCanvas canvas;
	
	public MouseListener(GameCanvas canvas){
		this.canvas = canvas;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
	    if (notches < 0) {
	    	canvas.zoomIn();
	    } else {
	    	canvas.zoomOut();
	    }
	}
}
