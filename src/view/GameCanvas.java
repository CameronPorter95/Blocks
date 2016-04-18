package view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class GameCanvas extends Canvas {

	public GameCanvas(){
		setSize(new Dimension(1920, 1080));
		this.setBackground(Color.BLACK);
	}
}
