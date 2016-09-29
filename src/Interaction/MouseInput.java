package Interaction;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.swing.SwingUtilities;
import state.Database;
import view.GameCanvas;
import view.SideBar;
import customCollections.DoublePoint;

public class MouseInput implements MouseWheelListener, MouseMotionListener, MouseListener {

	private GameCanvas canvas;
	private SideBar sideBar;
	private Database database;
	private boolean onSideBar = false;
	private Point mouseLoc = null;
	
	public MouseInput(GameCanvas canvas, SideBar sideBar, Database database){
		this.canvas = canvas;
		this.sideBar = sideBar;
		this.database = database;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(onSideBar == true){
			return;
		}
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
		Point p = e.getPoint();
		if(onSideBar == true){
			return;
		}
		if(!SwingUtilities.isRightMouseButton(e)) {
			mouseLoc = null;
		}
		if (SwingUtilities.isRightMouseButton(e)) {
			double dragAmountX = 0;
			double dragAmountY = 0;
			if(mouseLoc != null){
				dragAmountX = p.getX() - mouseLoc.getX();
				dragAmountY = p.getY() - mouseLoc.getY();
			}
			mouseLoc = e.getPoint();
			canvas.moveWorld(dragAmountX, dragAmountY);
		}
		if (SwingUtilities.isLeftMouseButton(e) && e.isShiftDown()) {
			selectTile(p, true, false);
		}
		else if (SwingUtilities.isLeftMouseButton(e)) {
			if(sideBar.getSelectedBlockName() == null){
				selectTile(p, false, false);
			}
			else{
				Point pos = selectTile(p, false, true);
				if(pos.getX() > 99 || pos.getY() > 99 || pos.getX() < 0 || pos.getY() < 0){
					return;
				}
				database.placeBlock(sideBar.getSelectedBlockImage(), sideBar.getSelectedBlockName(), pos);
				canvas.repaint();
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseLoc = null;
		if(e.getPoint().getX() == 0){
			new Thread() {
		        public void run() {
		        	while(MouseInfo.getPointerInfo().getLocation().getX() == 0){
						moveWorld(1, 0);
					}
		        }
			}.start();
		}
		else if(e.getPoint().getX() == canvas.getWidth()-1){
			new Thread() {
		        public void run() {
		        	while(MouseInfo.getPointerInfo().getLocation().getX() == canvas.getWidth()-1){
						moveWorld(-1, 0);
					}
		        }
			}.start();
		}
		if(e.getPoint().getY() == 0){
			new Thread() {
		        public void run() {
		        	while(MouseInfo.getPointerInfo().getLocation().getY() == 0){
						moveWorld(0, 1);
					}
		        }
			}.start();
		}
		else if(e.getPoint().getY() == canvas.getHeight()-1){
			new Thread() {
		        public void run() {
		        	while(MouseInfo.getPointerInfo().getLocation().getY() == canvas.getHeight()-1){
						moveWorld(0, -1);
					}
		        }
			}.start();
		}
	}
	
	public void mousePressed(MouseEvent e) {
		Point p = e.getPoint();
		if(onSideBar == true){
			for (Iterator<Entry<DoublePoint, String>> iterator = sideBar.getDrawnImages().entrySet().iterator(); iterator.hasNext();) {
				Entry<DoublePoint, String> entry = iterator.next();
				DoublePoint point = entry.getKey();
				String name = entry.getValue();
				BufferedImage image = sideBar.getScaledImages().get(name);
				if(p.getX() < point.getX() + image.getWidth() && p.getX() > point.getX()
					&& p.getY() < point.getY() + image.getHeight() && p.getY() > point.getY()){
					if(name.contains("selected")){
						String newName = name.substring(8);
						sideBar.selectBlock(newName, name, canvas.getImages().get(newName), point);
					}
					else{
						if(sideBar.getSelectedBlockName() != null){
							for(Entry<DoublePoint, String> entry2 : sideBar.getDrawnImages().entrySet()){
								DoublePoint point2 = entry2.getKey();
								String name2 = entry2.getValue();
								if(name2.equals(sideBar.getSelectedBlockName())){
									String newName = name2.substring(8);
									sideBar.selectBlock(newName, name2, canvas.getImages().get(newName), point2);
								}
							}
						}
						String newName = "selected" + name;
						sideBar.selectBlock(newName, name, canvas.getImages().get(newName), point);
					}
				}
			}
			return;
		}
		if (SwingUtilities.isLeftMouseButton(e) && e.isShiftDown()) {
			selectTile(p, true, false);
		}
		else if (SwingUtilities.isLeftMouseButton(e)) {
			if(sideBar.getSelectedBlockName() == null){
				selectTile(p, false, false);
			}
			else{
				Point pos = selectTile(p, false, true);
				if(pos.getX() > database.getFloorX()-1 || pos.getY() > database.getFloorY()-1 || pos.getX() < 0 || pos.getY() < 0){
					return;
				}
				database.placeBlock(sideBar.getSelectedBlockImage(), sideBar.getSelectedBlockName(), pos);
				canvas.repaint();
			}
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
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getComponent() == sideBar){
			onSideBar = true;
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getComponent() == sideBar){
			onSideBar = false;
		}
	}
	
	private Point selectTile(Point p, boolean deselect, boolean placeBlock){
		double diffY = p.getY() - canvas.getTranslateY();
		double diffX = p.getX() - (canvas.getTranslateX() + (((canvas.getFloorX()+1) * canvas.getZoom())/2));
		
		int totalDiffX = (int) Math.ceil((diffX/canvas.getZoom()) - (diffY/(canvas.getZoom()/2)));
		int totalDiffY = (int) Math.ceil((diffX/canvas.getZoom()) + (diffY/(canvas.getZoom()/2)));
		
		int xCoord = (canvas.getFloorX()/2) + totalDiffX;
		int yCoord = (canvas.getFloorY()/(2*canvas.getFloorY()/canvas.getFloorX())) + totalDiffY - 1;
		
		if(placeBlock){
			Point tile = new Point(xCoord, yCoord);
			return tile;
		}
		else{
			if(deselect){
				canvas.deselectTile(xCoord, yCoord);
				return null;
			}
			canvas.selectTile(xCoord, yCoord);
			return null;
		}
	}
	
	private void moveWorld(int x, int y) {
		canvas.moveWorld(x, y);
	    try {
	        Thread.sleep(10);
	    } 
	    catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	}
}
