package state.blocks;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class Wall implements Block {

	BufferedImage image;
	String name;
	Point position;
	
	public Wall(BufferedImage image, String name, Point position) {
		super();
		this.image = image;
		this.name = name;
		this.position = position;
	}
	
	@Override
	public BufferedImage getImage() {
		return image;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Point getPosition() {
		return position;
	}

}
