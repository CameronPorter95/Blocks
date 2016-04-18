package view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class GameCanvas extends Canvas {
	
	private int zoom = 200;
	private BufferedImage floorTile = null;

	public GameCanvas(){
		setSize(new Dimension(1920, 1080));
		this.setBackground(Color.BLACK);
		readImage();
		repaint();
	}
	
	public void paint(Graphics graphics){
		Graphics2D g = (Graphics2D) graphics;
		g.setColor(Color.BLACK);
		drawFloor(g);
	}
	
	private void drawFloor(Graphics g){
		for (int i = 10; i >= 0; i--){
		    for (int j = 0; j < 10; j++){
		    	Point point = new Point(i, j);
		    	Point p = twoDToIso(point);
		    	g.drawImage(floorTile, p.x, p.y, getParent());
		    }
		}
	}
	
	private Point twoDToIso(Point point){
		Point tempPt = new Point(0,0);
		tempPt.x = (point.x * (int) zoom / 2) + (point.y * (int) zoom / 2);
		tempPt.y = (point.y * (int) zoom / 4) - (point.x * (int) zoom / 4);
		return tempPt;
	}
	
	private void readImage(){
		try {
			floorTile = ImageIO.read(getClass().getResource("assets/floor.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
