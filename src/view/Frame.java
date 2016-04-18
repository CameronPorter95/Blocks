package view;

import java.awt.Dimension;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Frame extends JFrame {
	
	public Frame(){
		setupFrame();
	}
	
	private void setupFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//todo: this.setIconImage(new ImageIcon(imgURL).getImage());
		this.setMinimumSize(new Dimension(1920, 1080));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		pack();
		setVisible(true);
	}
}
