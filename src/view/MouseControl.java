package view;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.SwingUtilities;

public class MouseControl implements MouseWheelListener, MouseMotionListener, MouseListener{

	private GameCanvas canvas;
	private Point mouseLoc = null;
	
	public MouseControl(GameCanvas canvas){
		this.canvas = canvas;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int mPosX = (int) e.getPoint().getX();
		int mPosY = (int) e.getPoint().getY();
		int pRangeX = mPosX - canvas.getTranslateX();
		int pRangeY = mPosY - canvas.getTranslateY();
		int deltaTranslateX = pRangeX/canvas.getZoom() * 20;
		int deltaTranslateY = pRangeY/canvas.getZoom() * 20;
		int notches = e.getWheelRotation();
	    if (notches < 0) {
	    	canvas.zoomIn(deltaTranslateX, deltaTranslateY);
	    } else {
	    	canvas.zoomOut(deltaTranslateX, deltaTranslateY);
	    }
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			double dragAmountX = 0;
			double dragAmountY = 0;
			if(mouseLoc != null){
				dragAmountX = e.getPoint().getX() - mouseLoc.getX();
				dragAmountY = e.getPoint().getY() - mouseLoc.getY();
			}
			mouseLoc = e.getPoint();
			canvas.moveWorld(dragAmountX, dragAmountY);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseLoc = null;
	}
	
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			double diffY = e.getPoint().getY() - canvas.getTranslateY();
			double diffX = e.getPoint().getX() - (canvas.getTranslateX() + (((canvas.getFloorSize()+1) * canvas.getZoom())/2));
			
			int totalDiffX = (int) Math.ceil((diffX/canvas.getZoom()) - (diffY/(canvas.getZoom()/2)));
			int totalDiffY = (int) Math.ceil((diffX/canvas.getZoom()) + (diffY/(canvas.getZoom()/2)));
			
			double xCoord = (canvas.getFloorSize()/2) + totalDiffX;
			double yCoord = (canvas.getFloorSize()/2) + totalDiffY;
			
			System.out.println(xCoord + ", " + yCoord);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
