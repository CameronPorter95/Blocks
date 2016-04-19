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
	private int floorSize = 69; //Floor is a 69x69 grid.
	private int translateX, translateY = 0;
	private BufferedImage floorTile = null;
	private BufferedImage scaledFloorTile;

	public GameCanvas(){
		this.setBackground(Color.BLACK);
		setSize(new Dimension(1920, 1080));
		this.translateX = ((int) (this.getSize().getWidth() / 2)) - ((floorSize+1) * zoom/2);
		this.translateY = this.getHeight()/2;
		readImage();
		repaint();
	}
	
	@Override
    public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
        super.paintComponent(g);
        drawFloor(g);
	}
	
	/*----------------------------Rendering Methods-------------------------------------*/
	
	private void drawFloor(Graphics2D g){
		for (int i = floorSize; i > 0; i--){
		    for (int j = 0; j < floorSize; j++){
		    	Point point = new Point(i, j);
		    	Point p = twoDToIso(point);
		    	g.drawImage(scaledFloorTile, p.x, p.y, getParent());
		    }
		}
	}
	
	private Point twoDToIso(Point point){
		Point tempPt = new Point(0,0);
		tempPt.x = (point.x * (int) zoom / 2) + (point.y * (int) zoom / 2) + this.translateX;
		tempPt.y = (point.y * (int) zoom / 4) - (point.x * (int) zoom / 4) + this.translateY;
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
	
	/*----------------------------User Interaction Methods------------------------------*/
	
	public void zoomIn(){
		if(zoom <= 280){
			this.zoom = this.zoom + 20;
			this.translateX = this.translateX - (20*((floorSize+1)/2));
			scaledFloorTile =  getScaledImage(floorTile, zoom, zoom/2);
			repaint();
		}
	}
	
	public void zoomOut(){
		if(zoom >= 80){
			this.zoom = this.zoom - 20;
			this.translateX = this.translateX + (20*((floorSize+1)/2));
			scaledFloorTile =  getScaledImage(floorTile, zoom, zoom/2);
			repaint();
		}
	}
	
	public void moveWorld(double x, double y){
		this.translateX = this.translateX + (int) x;
		this.translateY = this.translateY + (int) y;
		repaint();
	}
}
