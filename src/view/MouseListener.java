package view;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseListener implements MouseWheelListener, MouseMotionListener{

	private GameCanvas canvas;
	private Point mouseLoc = null;
	
	public MouseListener(GameCanvas canvas){
		this.canvas = canvas;
	}
	
	//mPosX = (point.x * (int) zoom / 2) + (point.y * (int) zoom / 2) + this.translateX;
	//mPosY = (point.y * (int) zoom / 4) - (point.x * (int) zoom / 4) + this.translateY
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		double mPosX = e.getPoint().getX();
		double mPosY = e.getPoint().getY();
		//px * zoom/2 = mPosX - (point.y * (int) zoom / 2) - this.translateX
		//px = (mPosX - (point.y * (int) zoom / 2) - this.translateX) / (zoom/2)
		
		//px * zoom/4 = -(mPosY - (point.y * (int) zoom / 4) - this.translateY)
		//px = (-mPosY + (point.y * (int) zoom / 4) + this.translateY) / (zoom/4)
		
		//(-mPosY + (py * zoom / 4) + this.translateY) / (zoom/4) = (mPosX - (py * zoom / 2) - this.translateX) / (zoom/2)
		//(-mPosY + (py * zoom / 4) + this.translateY) * 2 = (mPosX - (py *zoom / 2) - this.translateX)
		// -2mPosY + 2(py * zoom/4) + 2this.translateY = mPosX - (py * zoom/2) - this.translateX
		//-2mPosY + 2this.translateY = mPosX - (py * zoom/2) - this.translateX - 2(py * zoom/4)
		//-2mPosY + 2this.translateY - mPosX + this.translateX = -(py * zoom/2) - 2(py * zoom/4)
		//-2mPosY + 2this.translateY - mPosX + this.translateX = (-py * -zoom/2) - (2py * zoom/2)
		//-2mPosY + 2this.translateY - mPosX + this.translateX = zoom/2(py) - zoom/2(2py)
		//(-2mPosY + 2this.translateY - mPosX + this.translateX) / zoom/2 = -py
		//((-2mPosY + 2this.translateY - mPosX + this.translateX) / -zoom/2) = py
		
		
		//(-m + (U * (z / 4)) + y) / (z/4) = (n - (U * (z / 2)) - x) / (z/2)
		double py = ((2 * mPosY) + mPosX - canvas.getTranslateX() - (2 * canvas.getTranslateY())) / canvas.getZoom();
		System.out.println(py);
		int notches = e.getWheelRotation();
	    if (notches < 0) {
	    	canvas.zoomIn();
	    } else {
	    	canvas.zoomOut();
	    }
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		double dragAmountX = 0;
		double dragAmountY = 0;
		if(mouseLoc != null){
			dragAmountX = e.getPoint().getX() - mouseLoc.getX();
			dragAmountY = e.getPoint().getY() - mouseLoc.getY();
		}
		mouseLoc = e.getPoint();
		canvas.moveWorld(dragAmountX, dragAmountY);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseLoc = null;
	}
}
