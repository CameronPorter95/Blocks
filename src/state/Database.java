package state;

import java.awt.Point;
import java.awt.image.BufferedImage;

import state.blocks.Block;
import state.blocks.Wall;

public class Database {
	
	Block[][] blocks;
	int floorX, floorY;
	
	public Database(){
		floorX = 100;
		floorY = 100;
		blocks = new Block[floorX][floorY];
	}
	
	public void placeBlock(BufferedImage image, String name, Point position){
		name = name.substring(8);
		Block block;
		switch(name){
			case "wall": block = new Wall(image, name, position);
						 break;
			default: 	 block = null;
						 break;
        
		}
		if(block != null){
			blocks[(int) block.getPosition().getX()][(int) block.getPosition().getY()] = block;
		}
	}
	
	public Block[][] getBlocks(){
		return blocks;
	}
	
	public int getFloorX(){
		return floorX;
	}
	
	public int getFloorY(){
		return floorY;
	}
}
