package fmd_desktop_clint.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import org.json.JSONException;

import fmd_desktop_clint.socet.Connection;
import fmd_desktop_clint.util.CommonUtil;
import fmd_desktop_clint.util.Constants;

@SuppressWarnings("serial")
public class ConnectToServer extends JFrame {

	private static String hostname;

	public ConnectToServer() {
		super("Find My Device | Connect To Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setResizable(false);
		setBounds(250, 115, 800, 550);
		JPanel panel = new JPanel();
		add(panel);

		URL url = getClass().getResource("/resources/logo.png");
		ImageIcon icon = new ImageIcon(url);
		setIconImage(icon.getImage());

		placeComponents(panel);

		setVisible(true);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		// Define and add two drop down menu to the menubar
		JMenu web = new JMenu("Web");
		JMenu helpMenu = new JMenu("Help");
		JMenu aboutMenu = new JMenu("about");
		JMenu dataMenu = new JMenu("Data");
		JMenu logoutMenu = new JMenu("logout");

		menuBar.add(web);
		menuBar.add(helpMenu);
		menuBar.add(aboutMenu);
		menuBar.add(dataMenu);
		menuBar.add(logoutMenu);

		// Create and add simple menu item to one of the drop down menu
		JMenuItem openAction = new JMenuItem("Open");
		JMenuItem exitAction = new JMenuItem("Exit");
		JMenuItem logoutAction = new JMenuItem("logout");
		JMenuItem logsAction = new JMenuItem("show logs");

		web.add(openAction);
		web.addSeparator();
		web.add(exitAction);
		logoutMenu.add(logoutAction);
		dataMenu.add(logsAction);

		exitAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		logsAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if ((new File(Constants.LOG_FILE)).exists()) {

						Process p = Runtime.getRuntime()
								.exec("rundll32 url.dll,FileProtocolHandler " + Constants.LOG_FILE);
						p.waitFor();

					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		logoutAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					SuperUtil.logout();
					dispose();
					new login();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		openAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String url = "http://" + hostname + ":8080/fmd/";
					java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
				} catch (MalformedURLException e) {

					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		try {
			hostname = CommonUtil.getHostName();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void placeComponents(JPanel panel) {
		panel.setLayout(null);

		BufferedImage myPicture, iconProfile;
		try {
			iconProfile = ImageIO.read(new File(getClass().getResource("/resources/userpic.jpg").getPath()));
			JLabel iconProfileLab = new JLabel(new ImageIcon(iconProfile));
			iconProfileLab.setBounds(100, 80, 150, 150);
			Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
			iconProfileLab.setBorder(border);
			iconProfileLab.setOpaque(false);
			panel.add(iconProfileLab);

			myPicture = ImageIO.read(new File(getClass().getResource("/resources/bkimage.jpg").getPath()));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			picLabel.setBounds(-100, 0, 1000, 150);
			panel.add(picLabel);
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		JButton ConnectButton = new JButton("CONNECT TO OUR SERVER");
		ConnectButton.setBounds(100, 250, 600, 80);
		ConnectButton.setFont(new Font("Serif", Font.PLAIN, 25));
		ConnectButton.setForeground(Color.BLACK);
		panel.add(ConnectButton);

		JButton back = new JButton("BACK TO HOME");
		back.setBounds(100, 360, 600, 80);
		back.setFont(new Font("Serif", Font.PLAIN, 25));
		back.setForeground(Color.BLACK);
		panel.add(back);

		final JPanel statusPanel = new JPanel();

		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(getWidth(), 16));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		final JLabel statusLabel = new JLabel("you are now connected");
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel);
		statusLabel.setVisible(true);

		ConnectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection con = new Connection();

				if (con.isConnected()) {
					statusLabel.setText("Connected Successfully .......");
				} else {
					statusLabel.setText("Please Wait .......");
					try {
						Thread.sleep(60000 * 4);
					} catch (InterruptedException e1) {
						System.out.println("Interrupted at " + new Date());
					}
					con = new Connection();
				}
			}
		});

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new AddDevice().setVisible(true);
					dispose();
				} catch (IOException | JSONException e1) {
					e1.printStackTrace();
				}

			}
		});
	}

}
