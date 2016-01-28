package fmd_desktop_clint.views;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class ConnectToServer  {
	
	public ConnectToServer(){
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setSize(800, 550);
		frame.setBounds(250, 115, 800, 550);

		// create the status bar panel and shove it down the bottom of the frame
		JPanel statusPanel = new JPanel();
		
		placeComponents(statusPanel);
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		frame.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 16));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		
		
		JLabel statusLabel = new JLabel("you are now connected");
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel);

		frame.setVisible(true);
	}
	private static void placeComponents(JPanel panel) {
		
	}

}
