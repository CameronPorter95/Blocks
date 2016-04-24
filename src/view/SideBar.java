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
	int hiddenLocation;

	public SideBar(Dimension panelSize, int yOffset){
		this.hiddenLocation = (int) (-panelSize.getWidth()/15);
		this.setBackground(Color.BLACK);
		this.setSize((int) panelSize.getWidth()/15, (int) (panelSize.getHeight()/1.25));
		slidePanelInFromLeft(this, (int) ((panelSize.getHeight() - this.getHeight())/2) + yOffset);
		//addComponentsToPane(frame.getContentPane());
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
	}
	
	synchronized void slidePanelInFromLeft(JPanel panel, int y) {
		timer = new Timer(5, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (hiddenLocation < 0){
					hiddenLocation = hiddenLocation + 1;
					panel.setLocation(hiddenLocation, y);
					panel.repaint();
				} else {
					timer.stop();
				}

				panel.setLocation(hiddenLocation, y);
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
