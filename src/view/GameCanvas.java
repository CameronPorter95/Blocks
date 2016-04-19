package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameCanvas extends JPanel {
	
	private int zoom = 200;
	private int floorSize = 50; //Floor is a 50x50 grid.
	private BufferedImage floorTile = null;
	private BufferedImage scaledFloorTile;

	public GameCanvas(){
		this.setBackground(Color.BLACK);
		setSize(new Dimension(1920, 1080));
		readImage();
		repaint();
	}
	
	@Override
    public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
        super.paintComponent(g);
        drawFloor(g);
	}
	
	private void drawFloor(Graphics2D g){
		for (int i = floorSize; i >= 0; i--){
		    for (int j = 0; j < floorSize; j++){
		    	Point point = new Point(i, j);
		    	Point p = twoDToIso(point);
		    	g.drawImage(scaledFloorTile, p.x, p.y, getParent());
		    }
		}
	}
	
	public void zoomIn(){
		if(zoom <= 280){
			this.zoom = this.zoom + 20;
			scaledFloorTile =  getScaledImage(floorTile, zoom, zoom/2);
			repaint();
			System.out.println("Curent zoom is " + this.zoom);
		}
		else{
			System.out.println("Cannot zoom in any further\nCurrent Zoom is " + this.zoom);
		}
	}
	
	public void zoomOut(){
		if(zoom >= 40){
			this.zoom = this.zoom - 20;
			scaledFloorTile =  getScaledImage(floorTile, zoom, zoom/2);
			repaint();
			System.out.println("Curent zoom is " + this.zoom);
		}
		else{
			System.out.println("Cannot zoom out any further\nCurrent Zoom is " + this.zoom);
		}
	}
	
	private Point twoDToIso(Point point){
		Point tempPt = new Point(0,0);
		tempPt.x = (point.x * (int) zoom / 2) + (point.y * (int) zoom / 2 - ((this.zoom/2)*25));
		tempPt.y = (point.y * (int) zoom / 4) - (point.x * (int) zoom / 4) + 480;
		return tempPt;
	}
	
	private void readImage(){
		try {
			floorTile = ImageIO.read(getClass().getResource("assets/floor.png"));
			scaledFloorTile =  getScaledImage(floorTile, zoom, zoom/2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private BufferedImage getScaledImage(Image img, int w, int h){
	    BufferedImage resized = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
	    Graphics2D g2 = resized.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(img, 0, 0, w, h, null);
	    g2.dispose();
	    return resized;
	}
}
