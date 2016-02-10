package fmd_desktop_clint.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;

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

import fmd_desktop_clint.socet.Connection;
import fmd_desktop_clint.util.Constants;

public class ConnectToServer extends JFrame {

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
					logout();
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
					String url = Constants.HOST_NAME + "/fmd/";
					java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
				} catch (MalformedURLException e) {

					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

	}

	private void placeComponents(JPanel panel) {
		panel.setLayout(null);
		JButton ConnectButton = new JButton("Connect");
		ConnectButton.setBounds(100, 120, 600, 100);
		panel.add(ConnectButton);

		final JPanel statusPanel = new JPanel();

		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(getWidth(), 16));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		final JLabel statusLabel = new JLabel("you are now connected");
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel);
		statusLabel.setVisible(false);

		ConnectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean flag = true;
				while (flag) {
					Connection con = new Connection();
					if (con.signIn()) {
						flag = false;
						statusLabel.setVisible(true);
					} else {
						statusLabel.setText("Please Wait .......");
						statusLabel.setVisible(true);
					}

					try {
						Thread.sleep(60000 * 1);
					} catch (InterruptedException ec) {
						System.out.println("Interrupted at ");
					}
				}
			}
		});

	}

	public static void logout() throws IOException {
		File addDeviceFile = new File(Constants.FILE_PATH);
		BufferedReader brTest = new BufferedReader(new FileReader(addDeviceFile));
		String[] arr = brTest.readLine().split(" , ");

		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Constants.FILE_PATH, false)))) {
			out.println(arr[0] + " , " + 0 + " , " + arr[2]);
		} catch (IOException e) {
		}

	}
}
