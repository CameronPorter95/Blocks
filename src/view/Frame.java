package view;

import java.awt.Dimension;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Frame extends JFrame {
	
	public Frame(Dimension screenSize){
		this.setMinimumSize(screenSize);
		setupFrame();
	}
	
	private void setupFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//todo: this.setIconImage(new ImageIcon(imgURL).getImage());
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		pack();
		setVisible(true);
	}
}
