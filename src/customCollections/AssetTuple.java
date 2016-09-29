package customCollections;

import java.awt.image.BufferedImage;

public class AssetTuple {
	
	private String name;
	private DoublePoint position;
	private BufferedImage image;
	private BufferedImage scaled = null;

	public AssetTuple(String name, DoublePoint position, BufferedImage image) {
		super();
		this.name = name;
		this.position = position;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DoublePoint getPosition() {
		return position;
	}

	public void setPosition(DoublePoint position) {
		this.position = position;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public BufferedImage getScaled() {
		return scaled;
	}

	public void setScaled(BufferedImage scaled) {
		this.scaled = scaled;
	}
}
