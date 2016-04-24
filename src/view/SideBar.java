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
	int location;
	boolean extended = false;

	public SideBar(Dimension panelSize){
		this.location = (int) (-panelSize.getWidth()/15);
		this.setBackground(Color.BLACK);
		this.setSize((int) panelSize.getWidth()/15, (int) (panelSize.getHeight()/1.25));
		this.setLocation(-this.getWidth(), 0);
		//addComponentsToPane(frame.getContentPane());
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
	}
	
	public synchronized void slidePanel(JPanel panel, int y, boolean extend) {
		timer = new Timer(5, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if(extend){
					if (location < 0){
						location = location + 1;
						panel.setLocation(location, y);
						panel.repaint();
					} else {
						timer.stop();
					}
				}
				else{
					if (location > -panel.getWidth()){
						location = location - 1;
						panel.setLocation(location, y);
						panel.repaint();
					} else {
						timer.stop();
					}
				}
				panel.setLocation(location, y);
				panel.repaint();

			}
		});
		timer.start();
	}
	
	public void setExtended(boolean extended){
		this.extended = extended;
	}
	
	public boolean getExtended(){
		return extended;
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
