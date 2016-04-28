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
		String direction = null;
		Block block = null;
		if(name.contains("east")){
			name = name.substring(0, name.length()-4);
			direction = "east";
		}
		else if(name.contains("north")){
			name = name.substring(0, name.length()-5);
			direction = "north";
		}
		else if(name.contains("south")){
			name = name.substring(0, name.length()-5);
			direction = "south";
		}
		else if(name.contains("west")){
			name = name.substring(0, name.length()-4);
			direction = "west";
		}
		
		switch(name){
			case "wall":		block = new Wall(image, name + direction, position);
					 			break;
			case "skinnywall":	block = new Wall(image, name + direction, position);
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
