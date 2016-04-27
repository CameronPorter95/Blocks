package state.blocks;

import java.awt.Point;
import java.awt.image.BufferedImage;

public interface Block {
	
	public BufferedImage getImage();
	
	public  String getName();
	
	public Point getPosition();
	
}