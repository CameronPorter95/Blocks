package state;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Database {
	
	private HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	
	public Database(){
		
	}

	public HashMap<String, BufferedImage> getImages() {
		return images;
	}

	public void putImage(String s, BufferedImage image) {
		this.images.put(s, image);
	}
	
}
