package fmd_desktop_clint.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import fmd_desktop_clint.socet.Connection;

public class ConnectToServer {

	public ConnectToServer() {
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setSize(800, 550);
		frame.setBounds(250, 115, 800, 550);

		// create the status bar panel and shove it down the bottom of the frame
//		JPanel statusPanel = new JPanel();

		JPanel panel = new JPanel();
		frame.add(panel);

		placeComponents(panel, frame);

		frame.setVisible(true);
	}

	private void placeComponents(JPanel Panel,final JFrame frame) {
		JButton ConnectButton = new JButton("Connect");
		ConnectButton.setBounds(250, 250, 80, 25);
		Panel.add(ConnectButton);
		
		final JPanel statusPanel = new JPanel();
		
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		frame.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 16));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		final JLabel statusLabel = new JLabel("you are now connected");
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel);
		statusLabel.setVisible(false);;
		
		
		ConnectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Connection con = new Connection();
				con.signIn();
				statusLabel.setVisible(true);;
				

			}
		});

	}


}
