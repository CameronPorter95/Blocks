package view;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Frame extends JFrame implements ComponentListener{
	
	SideBar sideBar;
	
	public Frame(){
		this.setMinimumSize(new Dimension(800, 600));
		setupFrame();
	}
	
	private void setupFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//todo: this.setIconImage(new ImageIcon(imgURL).getImage());
		//this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//setUndecorated(true);
		setVisible(true);
		this.addComponentListener(this);
		setFocusTraversalKeysEnabled(false);
	}
	
	public SideBar addSideBar(){
		sideBar = new SideBar(this.getSize());
		this.add(sideBar);
		return sideBar;
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		if(this.sideBar != null){
			if(this.sideBar.getExtended() == true){
				sideBar.setSize(this.getWidth()/15, (int) (this.getHeight()/1.25));
				sideBar.setLocation(0, (int) ((this.getContentPane().getHeight() - sideBar.getHeight())/2));
			}
			else{
				sideBar.setSize(this.getWidth()/15, (int) (this.getHeight()/1.25));
				sideBar.setLocation(-sideBar.getWidth(), 0);
			}
		}
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
