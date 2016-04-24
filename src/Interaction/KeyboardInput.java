package Interaction;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import view.Frame;
import view.GameCanvas;
import view.SideBar;

@SuppressWarnings("serial")
public class KeyboardInput extends AbstractAction{

	private String action;
	private GameCanvas canvas;
	private Frame frame;
	private SideBar sideBar;
	
	public KeyboardInput(String action, GameCanvas canvas){
		this.action = action;
		this.canvas = canvas;
	}
	
	public KeyboardInput(String action, Frame frame, SideBar sideBar){
		this.action = action;
		this.frame = frame;
		this.sideBar = sideBar;
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
		else if(action.equals("tab")){
			if(sideBar.getExtended() == false){
				sideBar.slidePanelInFromLeft(sideBar, (int) ((frame.getHeight() - sideBar.getHeight())/2) - 20);
				sideBar.setExtended(true);
			}
			else{
				sideBar.setExtended(false);
			}
		}
	}
}
