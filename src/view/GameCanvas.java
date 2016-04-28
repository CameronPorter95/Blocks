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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import state.Database;
import state.blocks.Block;

@SuppressWarnings("serial")
public class GameCanvas extends JPanel {
	
	private Database database;
	private int zoom = 140;
	private int floorX;
	private int floorY;
	private int translateX, translateY = 0;
	private HashSet<Point> selectedTiles = new HashSet<Point>();
	private HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();	//All the images.
	private HashMap<String, BufferedImage> unselectedImages = new HashMap<String, BufferedImage>();	//Only the unselectedImages.
	private HashMap<String, BufferedImage> scaledImages = new HashMap<String, BufferedImage>();	//All the images, scaled for the board.

	public GameCanvas(Dimension frameSize, Database database){
		this.setBackground(Color.BLACK);
		setSize(frameSize);
		this.database = database;
		this.floorX = database.getFloorX();
		this.floorY = database.getFloorY();
		this.translateX = ((int) (this.getSize().getWidth() / 2)) - ((floorX+1) * zoom/2);
		this.translateY = this.getHeight()/2;
		readImages(false);
		readImages(true);
		repaint();
	}
	
	@Override
    public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
        super.paintComponent(g);
        drawFloor(g);
        drawBlocks(g);
	}
	
	/*----------------------------Rendering Methods-------------------------------------*/
	
	private void drawFloor(Graphics2D g){
		for (int i = floorX-1; i >= 0; i--){
		    for (int j = 0; j < floorY; j++){
		    	Point point = new Point(i, j);
		    	Point p = twoDToIso(point);
		    	if(p.getX() < this.getWidth() && p.getX() > -this.zoom && p.getY() < this.getHeight() && p.getY() > -this.zoom/2){
		    		if(selectedTiles.contains(point)){
				    	g.drawImage(scaledImages.get("marblefloor"), p.x, p.y, getParent());
			    	}
			    	else{
				    	g.drawImage(scaledImages.get("floor"), p.x, p.y, getParent());
			    	}
		    	}
		    }
		}
	}
	
	private void drawBlocks(Graphics2D g){
		for (int i = floorX-1; i >= 0; i--){
		    for (int j = 0; j <= floorY-1; j++){
		    	if(database.getBlocks()[i][j] != null){
		    		Point point = new Point(i, j);
			    	Point p = twoDToIso(point);
			    	Block block = database.getBlocks()[i][j];
			    	BufferedImage image = scaledImages.get(block.getName());
			    	g.drawImage(image, p.x, p.y - image.getHeight()+(zoom/2), getParent());
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
	
	private ArrayList<String> addToFilenames(boolean selected){
		ArrayList<String> filenames = new ArrayList<String>();
		
		if(selected == false){
			filenames.add("floor");
			filenames.add("checkeredfloor");
			filenames.add("marblefloor");
			filenames.add("wall");
			filenames.add("skinnywall");
		}
		else{
			filenames.add("selectedwall");
			filenames.add("selectedskinnywall");
		}
		
		return filenames;
	}
	
	private void readImages(boolean selected){
		ArrayList<String> filenames = addToFilenames(selected);
		
		for(String s : filenames){
			for(int i = 0; i < 4; i++){	//four directions on the board.
				String name = s;
				if(!s.contains("floor")){
					switch(i){
						case 0: name = name.concat("east");
						break;
						case 1: name = name.concat("north");
						break;
						case 2: name = name.concat("south");
						break;
						case 3: name = name.concat("west");
						break;
					}
				}
				else{
					i = 3;
				}
				try {
					BufferedImage image = null;
					if(selected == false){
						image = ImageIO.read(getClass().getResource("assets/deselected/" + (String) name + ".png"));
						images.put(name, image);
						unselectedImages.put(name, image);
						double width = (image.getWidth()/64f)*(zoom/(double) image.getWidth())*image.getWidth();
						double height = (image.getHeight()/32f)*((zoom /2f) / (double) image.getHeight())*image.getHeight();
						scaledImages.put(name, getScaledImage(image, (int) width, (int) height));
					}
					else{
						image = ImageIO.read(getClass().getResource("assets/selected/" + (String) name + ".png"));
						images.put(name, image);
						double width = (image.getWidth()/64f)*(zoom/(double) image.getWidth())*image.getWidth();
						double height = (image.getHeight()/32f)*((zoom /2f) / (double) image.getHeight())*image.getHeight();
						scaledImages.put(name, getScaledImage(image, (int) width, (int) height));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
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
			this.translateX = this.translateX - deltaTranslateX;
			this.translateY = this.translateY - deltaTranslateY;
			for (Iterator<Entry<String, BufferedImage>> iterator = images.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, BufferedImage> entry = iterator.next();
				BufferedImage image = entry.getValue();
				double width = (image.getWidth()/64f)*(zoom/(double) image.getWidth())*image.getWidth();
				double height = (image.getHeight()/32f)*((zoom /2f) / (double) image.getHeight())*image.getHeight();
				scaledImages.put(entry.getKey(), getScaledImage(image, (int) width, (int) height));
			}
			repaint();
		}
	}
	
	public void zoomOut(int deltaTranslateX, int deltaTranslateY){
		if(zoom >= 80){
			this.zoom = this.zoom - 20;
			this.translateX = this.translateX + deltaTranslateX;
			this.translateY = this.translateY + deltaTranslateY;
			for (Iterator<Entry<String, BufferedImage>> iterator = images.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, BufferedImage> entry = iterator.next();
				BufferedImage image = entry.getValue();
				double width = (image.getWidth()/64f)*(zoom/(double) image.getWidth())*image.getWidth();
				double height = (image.getHeight()/32f)*((zoom /2f) / (double) image.getHeight())*image.getHeight();
				scaledImages.put(entry.getKey(), getScaledImage(image, (int) width, (int) height));
			}
			repaint();
		}
	}
	
	public void moveWorld(double x, double y){
//		if(x < 0 && this.translateX <= this.zoom * (floorSize * -0.99)){
//			return;
//		}
//		System.out.println(this.translateX);
//		if(y < 0 && this.translateY <= (this.zoom/2) * (floorSize * -0.45)){
//			return;
//		}
//		if(x > 0 && this.translateX >= this.zoom * (floorSize * 0.25)){
//			return;
//		}
		this.translateX = this.translateX + (int) x;
		this.translateY = this.translateY + (int) y;
		repaint();
	}
	
	public void selectTile(int xCoord, int yCoord){
		Point p = new Point(xCoord, yCoord);
		this.selectedTiles.add(p);
		repaint();
	}
	
	public void deselectTile(int xCoord, int yCoord){
		Point p = new Point(xCoord, yCoord);
		this.selectedTiles.remove(p);
		repaint();
	}
	
	public void clearSelectedTiles(){
		this.selectedTiles.clear();
		repaint();
	}
	
	/*----------------------------Getters & Setters------------------------------*/
	
	public int getTranslateX(){
		return this.translateX;
	}
	
	public int getTranslateY(){
		return this.translateY;
	}
	
	public int getFloorX(){
		return floorX;
	}
	
	public int getFloorY(){
		return floorY;
	}
	
	public int getZoom(){
		return this.zoom;
	}
	
	public HashMap<String, BufferedImage> getImages(){
		return this.images;
	}
	
	public HashMap<String, BufferedImage> getUnselectedImages(){
		return this.unselectedImages;
	}
	
	public void setTranslateX(int x){
		this.translateX = x;
	}
	
	public void setTranslateY(int y){
		this.translateY = y;
	}
}
