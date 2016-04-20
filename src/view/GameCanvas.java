package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameCanvas extends JPanel {
	
	private int zoom = 200;
	private int floorSize = 6; //Floor is a 69x69 grid.
	private int translateX, translateY = 0;
	private ArrayList<Point> selectedTiles = new ArrayList<Point>();
	private HashMap<String, BufferedImage> scaledImages = new HashMap<String, BufferedImage>();
	private HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();

	public GameCanvas(Dimension screenSize){
		this.setBackground(Color.BLACK);
		setSize(screenSize);
		this.translateX = ((int) (this.getSize().getWidth() / 2)) - ((floorSize+1) * zoom/2);
		this.translateY = this.getHeight()/2;
		readImages();
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
		    	if(selectedTiles.contains(point)){
			    	g.drawImage(scaledImages.get("checkeredFloor"), p.x, p.y, getParent());
		    	}
		    	else{
			    	g.drawImage(scaledImages.get("floor"), p.x, p.y, getParent());
		    	}
		    }
		}
	}
	
	private Point twoDToIso(Point point){
		Point tempPt = new Point(0,0);
		tempPt.x = (point.x * (int) zoom / 2) + (point.y * (int) zoom / 2) + this.translateX;
		tempPt.y = (point.y * (int) zoom / 4) - (point.x * (int) zoom / 4) + this.translateY;
		return tempPt;
	}
	
	private ArrayList<String> addToFilenames(){
		ArrayList<String> filenames = new ArrayList<String>();
		
		filenames.add("floor");
		filenames.add("checkeredFloor");
		
		return filenames;
	}
	
	private void readImages(){
		ArrayList<String> filenames = addToFilenames();
		
		for(String s : filenames){
			try {
				BufferedImage image = ImageIO.read(getClass().getResource("assets/" + "floor" + ".png"));
				images.put(s, image);
				scaledImages.put(s, getScaledImage(image, zoom, zoom/2)) ;
			} catch (IOException e) {
				e.printStackTrace();
			}
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
	
	public void zoomIn(int deltaTranslateX, int deltaTranslateY){
		if(zoom <= 280){
			this.zoom = this.zoom + 20;
			//this.translateX = this.translateX - (20*((floorSize+1)/2));
			this.translateX = this.translateX - deltaTranslateX;
			this.translateY = this.translateY - deltaTranslateY;
			for (Iterator<Entry<String, BufferedImage>> iterator = this.images.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, BufferedImage> entry = iterator.next();
				scaledImages.put(entry.getKey(), getScaledImage(entry.getValue(), zoom, zoom/2));
			}
			repaint();
		}
	}
	
	public void zoomOut(int deltaTranslateX, int deltaTranslateY){
		if(zoom >= 80){
			this.zoom = this.zoom - 20;
			this.translateX = this.translateX + deltaTranslateX;
			this.translateY = this.translateY + deltaTranslateY;
			for (Iterator<Entry<String, BufferedImage>> iterator = this.images.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, BufferedImage> entry = iterator.next();
				scaledImages.put(entry.getKey(), getScaledImage(entry.getValue(), zoom, zoom/2));
			}
			repaint();
		}
	}
	
	public void moveWorld(double x, double y){
		this.translateX = this.translateX + (int) x;
		this.translateY = this.translateY + (int) y;
		repaint();
	}
	
	public void selectTile(int xCoord, int yCoord){
		Point p = new Point(xCoord, yCoord);
		this.selectedTiles.add(p);
	}
	
	/*----------------------------Getters & Setters------------------------------*/
	
	public int getTranslateX(){
		return this.translateX;
	}
	
	public int getTranslateY(){
		return this.translateY;
	}
	
	public int getFloorSize(){
		return floorSize;
	}
	
	public int getZoom(){
		return this.zoom;
	}
}
