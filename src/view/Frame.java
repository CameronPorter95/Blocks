package view;

import java.awt.Dimension;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Frame extends JFrame {
	
	SideBar sideBar;
	
	public Frame(){
		this.setMinimumSize(new Dimension(800, 600));
		setupFrame();
	}
	
	private void setupFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//todo: this.setIconImage(new ImageIcon(imgURL).getImage());
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//setUndecorated(true);
		pack();
		setVisible(true);
	}
	
	public void addSideBar(){
		sideBar = new SideBar(this.getSize());
		this.add(sideBar);
	}
}
