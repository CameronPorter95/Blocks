package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class SideBar extends JPanel{
	
	private Timer timer;
	int width = 0;

	public SideBar(Dimension frameSize){
		this.setBackground(Color.BLACK);
		this.setLocation(0,(int) frameSize.getHeight()/15);
		slidePanelInFromLeft(this, (int) frameSize.getWidth()/15, (int) (frameSize.getHeight()/1.25));
		//addComponentsToPane(frame.getContentPane());
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
	}
	
	synchronized void slidePanelInFromLeft(JPanel panel, int targetWidth, int y) {
		timer = new Timer(5, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (width < targetWidth){
					width = width + 1;
					panel.setSize(new Dimension(width,y));
					panel.repaint();
				} else {
					timer.stop();
				}

				panel.setSize(new Dimension(width,y));
				panel.repaint();

			}
		});
		timer.start();
	}
}
	
//	private void addComponentsToPane(Container canvas) {
//        if (RIGHT_TO_LEFT) {
//            canvas.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//        }
//        
//        canvas.setLayout(new GridBagLayout());
//        GridBagConstraints c = new GridBagConstraints();
//	}
