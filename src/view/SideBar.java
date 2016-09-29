package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class SideBar extends JPanel{
	
	private GameCanvas canvas;
	private BufferedImage selectedBlockImage = null;
	private String selectedBlockName = null;
	private HashMap<String, BufferedImage> blocks = new HashMap<String, BufferedImage>();	//Unscaled blocks to be drawn on the panel.
	private HashMap<String, BufferedImage> scaledBlocks = new HashMap<String, BufferedImage>();	//Scaled blocks to be drawn on the panel.
	private HashMap<Point, String> drawnBlocks = new HashMap<Point, String>();	//Names of blocks with positions to be drawn on the panel.
	private HashMap<String, BufferedImage> UIElements = new HashMap<String, BufferedImage>();
	private HashMap<Point, BufferedImage> UIElementsScaled = new HashMap<Point, BufferedImage>();
	private Timer timer;
	private int yPos = this.getY() + 10;
	private int location;
	private boolean extended = false;

	public SideBar(Dimension frameSize, GameCanvas canvas){
		this.canvas = canvas;
		this.location = (int) (-frameSize.getWidth()/15);
		this.setBackground(Color.BLACK);
		this.setSize((int) frameSize.getWidth()/15, (int) (frameSize.getHeight()/1.25));
		this.setLocation(-this.getWidth(), 0);
		addToImages();
		scaleImages();
		setUIElementValues();
		addToDrawnImages();
	}
		
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        addComponentsToPane(g);
	}
	
	private void addComponentsToPane(Graphics g) {
		for (Iterator<Entry<Point, String>> iterator = drawnBlocks.entrySet().iterator(); iterator.hasNext();) {
			Entry<Point, String> entry = iterator.next();
			Point position = entry.getKey();
			g.drawImage((Image) scaledBlocks.get(entry.getValue()), (int) position.getX(), (int) position.getY(), getParent());
		}
		for (Iterator<Entry<Point, BufferedImage>> uiIterator = UIElementsScaled.entrySet().iterator(); uiIterator.hasNext();) {
			Entry<Point, BufferedImage> entry = uiIterator.next();
			Point position = entry.getKey();
			g.drawImage(entry.getValue(), (int) position.getX(), (int) position.getY(), getParent());
		}
	}
	
	public void addToDrawnImages(){
		int xPos = this.getWidth()/6;
		for(String s : scaledBlocks.keySet()){
			drawnBlocks.put(new Point(xPos, yPos), s);
			yPos = yPos + scaledBlocks.get(s).getHeight() + 20;
		}
	}
	
	private ArrayList<String> addToUIFilenames(){
		ArrayList<String> filenames = new ArrayList<String>();
		filenames.add("PanelScroll");
		
		return filenames;
	}
	
	private void addToImages(){
		for (Iterator<Entry<String, BufferedImage>> iterator = canvas.getUnselectedImages().entrySet().iterator(); iterator.hasNext();) {
			Entry<String, BufferedImage> entry = iterator.next();
			String name = entry.getKey();
			BufferedImage image = entry.getValue();
			double scalingValue = (double) image.getHeight()/(double) image.getWidth();
			if(scalingValue != 0.5){	//If image is a block and not a tile.
				this.blocks.put(name, image);
			}
		}
		
		ArrayList<String> filenames = addToUIFilenames();
		for(String s : filenames){
			BufferedImage image = null;
			try {
				image = ImageIO.read(getClass().getResource("assets/UI/" + (String) s + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			UIElements.put(s, image);
		}
	}
	
	public void scaleImages(){
		for (Iterator<Entry<String, BufferedImage>> iterator = blocks.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, BufferedImage> entry = iterator.next();
			BufferedImage image = entry.getValue();
			double scalingValue = (double) image.getHeight()/(double) image.getWidth();
			scaledBlocks.put(entry.getKey(), getScaledImage(image, this.getWidth()/2, (int) ((this.getWidth()/2)*scalingValue)));
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
	
	public synchronized void slidePanel(JPanel panel, int y, boolean extend) {
		timer = new Timer(5, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if(extend){
					if (location < 0){
						location = location + 1;
						panel.setLocation(location, y);
						panel.repaint();
					} else {
						timer.stop();
					}
				}
				else{
					if (location > -panel.getWidth()){
						location = location - 1;
						panel.setLocation(location, y);
						panel.repaint();
					} else {
						timer.stop();
					}
				}
				panel.setLocation(location, y);
				panel.repaint();

			}
		});
		timer.start();
	}
	
	public void selectBlock(String name, String oldName, BufferedImage image, Point point){
		if(name.contains("selected")){
			this.selectedBlockImage = image;
			this.selectedBlockName = name;
		}
		else{
			this.selectedBlockImage = null;
			this.selectedBlockName = null;
		}
		blocks.remove(oldName);
		blocks.put(name, image);
		scaledBlocks.remove(oldName);
		double scalingValue = (double) image.getHeight()/(double) image.getWidth();
		scaledBlocks.put(name, getScaledImage(image, this.getWidth()/2, (int) ((this.getWidth()/2)*scalingValue)));
		drawnBlocks.put(point, name);
		repaint();
	}
	
	public BufferedImage getSelectedBlockImage(){
		return selectedBlockImage;
	}
	
	public String getSelectedBlockName(){
		return selectedBlockName;
	}
	
	public boolean getExtended(){
		return extended;
	}
	
	public HashMap<Point, String> getDrawnImages(){
		return this.drawnBlocks;
	}
	
	public HashMap<String, BufferedImage> getScaledImages(){
		return this.scaledBlocks;
	}
	
	public HashMap<Point, BufferedImage> getScaledUIElements(){
		return this.UIElementsScaled;
	}
	
	public void setExtended(boolean extended){
		this.extended = extended;
	}
	
	public void setYPos(int yPos){
		this.yPos = yPos;
	}
	
	//----------------------Helper Methods--------------------------------
	
	void setUIElementValues(){
		for(String name : UIElements.keySet()){
			Point pos = null;
			switch(name){
			case "PanelScroll":		
				pos = new Point((int) (this.getWidth()/1.1), 0);
				break;
			}
			
			if(pos != null){
				BufferedImage image = UIElements.get(name);
				double scalingValue = (double) image.getHeight()/(double) image.getWidth();
				UIElementsScaled.put(pos, getScaledImage(image, this.getWidth()/2, (int) ((this.getWidth()/2)*scalingValue)));
			}
			else{
				throw new NullPointerException("Position of UI Element is null");
			}
		}
	}
}
