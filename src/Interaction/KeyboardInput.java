package Interaction;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import view.GameCanvas;

@SuppressWarnings("serial")
public class KeyboardInput extends AbstractAction{

	private String action;
	private GameCanvas canvas;
	
	public KeyboardInput(String action, GameCanvas canvas){
		this.action = action;
		this.canvas = canvas;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(action.equals("=")){
			int pRangeX = (canvas.getWidth()/2) - canvas.getTranslateX();
			int pRangeY = (canvas.getHeight()/2) - canvas.getTranslateY();
			int deltaTranslateX = pRangeX/canvas.getZoom() * 20;
			int deltaTranslateY = pRangeY/canvas.getZoom() * 20;
			canvas.zoomIn(deltaTranslateX, deltaTranslateY);
		}
		else if(action.equals("Minus")){
			int pRangeX = (canvas.getWidth()/2) - canvas.getTranslateX();
			int pRangeY = (canvas.getHeight()/2) - canvas.getTranslateY();
			int deltaTranslateX = pRangeX/canvas.getZoom() * 20;
			int deltaTranslateY = pRangeY/canvas.getZoom() * 20;
			canvas.zoomOut(deltaTranslateX, deltaTranslateY);
		}
		else if(action.equals("c")){
			canvas.clearSelectedTiles();
		}
		else if(action.equals("space")){
			canvas.setTranslateX(((int) (canvas.getWidth() / 2)) - ((canvas.getFloorSize()+1) * canvas.getZoom()/2));
			canvas.setTranslateY(canvas.getHeight()/2);
			canvas.repaint();
		}
	}
}
