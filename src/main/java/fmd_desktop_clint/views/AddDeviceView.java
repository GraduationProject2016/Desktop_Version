package fmd_desktop_clint.views;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.json.JSONException;

import fmd_desktop_clint.util.CommonUtil;
import fmd_desktop_clint.util.Constants;
import fmd_desktop_clint.util.SuperUtil;
import fmd_desktop_clint.util.WSInvokes;

@SuppressWarnings("serial")
public class AddDeviceView extends JFrame {

	private static String hostname;

	public void preInit() throws IOException, JSONException {
		if (new File(Constants.FILE_PATH).exists()) {
			String[] arr = CommonUtil.readConfigFile();
			if (arr.length > 0) {
				if (arr[0].equals("1")) {
					boolean deletedDevice = WSInvokes.isDeletedDevice(getMacAddress());
					if (deletedDevice)
						CommonUtil.DeleteDevice();
				}
			}
		}
	}

	public AddDeviceView() throws IOException, JSONException {
		super("Find My Device | Add  Device");

		preInit();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setResizable(false);
		setBounds(250, 115, 800, 550);
		JPanel panel = new JPanel();
		add(panel);

		URL url = getClass().getResource("/resources/logo.png");
		ImageIcon icon = new ImageIcon(url);
		setIconImage(icon.getImage());

		try {
			if (CommonUtil.isAddedDevice()) {
				placeMessage(panel);
			} else {
				placeComponents(panel);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		setVisible(true);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		// Define and add two drop down menu to the menubar
		JMenu web = new JMenu("Web");
		JMenu helpMenu = new JMenu("Help");
		JMenu aboutMenu = new JMenu("about");
		JMenu dataMenu = new JMenu("Data");
		JMenu serverMenu = new JMenu("Server");
		JMenu logoutMenu = new JMenu("logout");

		menuBar.add(web);
		menuBar.add(helpMenu);
		menuBar.add(aboutMenu);
		menuBar.add(dataMenu);
		menuBar.add(serverMenu);
		menuBar.add(logoutMenu);

		// Create and add simple menu item to one of the drop down menu
		JMenuItem openAction = new JMenuItem("Open");
		JMenuItem exitAction = new JMenuItem("Exit");
		JMenuItem logoutAction = new JMenuItem("logout");
		JMenuItem logsAction = new JMenuItem("show logs");
		JMenuItem connectAction = new JMenuItem("connect");

		web.add(openAction);
		web.addSeparator();
		web.add(exitAction);
		logoutMenu.add(logoutAction);
		dataMenu.add(logsAction);
		serverMenu.add(connectAction);

		exitAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		connectAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				new ConnectToServerView().setVisible(true);
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
					new LoginView();
					dispose();
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

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (CommonUtil.isAddedDevice()) {
						CommonUtil.doWork();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void placeMessage(JPanel panel) {
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

		JLabel message = new JLabel("This Device is subscribed Successfully");
		message.setBounds(270, 150, 900, 100);
		message.setFont(new Font("Serif", Font.PLAIN, 30));
		message.setOpaque(true);
		message.setForeground(Color.red);
		panel.add(message);

		JLabel instructions = new JLabel("If You Want to subscribe your current device location ? ");
		instructions.setBounds(90, 300, 600, 40);
		instructions.setFont(new Font("Serif", Font.PLAIN, 25));
		instructions.setOpaque(true);
		instructions.setBackground(Color.LIGHT_GRAY);
		instructions.setForeground(Color.BLACK);
		panel.add(instructions);

		JLabel instructions1 = new JLabel("(1) Click on the below button.");
		instructions1.setBounds(90, 330, 600, 40);
		instructions1.setFont(new Font("Serif", Font.PLAIN, 20));
		instructions1.setOpaque(true);
		instructions1.setBackground(Color.LIGHT_GRAY);
		instructions1.setForeground(Color.BLACK);
		panel.add(instructions1);

		JLabel instructions2 = new JLabel("(2) It will open your browser.");
		instructions2.setBounds(90, 360, 600, 40);
		instructions2.setFont(new Font("Serif", Font.PLAIN, 20));
		instructions2.setOpaque(true);
		instructions2.setBackground(Color.LIGHT_GRAY);
		instructions2.setForeground(Color.BLACK);
		panel.add(instructions2);

		JLabel instructions3 = new JLabel("(3) Click allow.");
		instructions3.setBounds(90, 390, 600, 40);
		instructions3.setFont(new Font("Serif", Font.PLAIN, 20));
		instructions3.setOpaque(true);
		instructions3.setBackground(Color.LIGHT_GRAY);
		instructions3.setForeground(Color.BLACK);
		panel.add(instructions3);

		JButton instbtn = new JButton("Allowing GPS Location");
		instbtn.setBounds(90, 425, 600, 40);
		instbtn.setOpaque(true);
		instbtn.setForeground(Color.BLACK);
		panel.add(instbtn);
		instbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("http://" + CommonUtil.getHostName()
							+ ":8080/fmd/webserviceconnector.html?qu=" + CommonUtil.getDeviceID()));
				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});
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

		JLabel message = new JLabel("Register Your Device");
		message.setBounds(340, 150, 900, 100);
		message.setFont(new Font("Time New Roman", Font.ITALIC, 37));
		message.setForeground(Color.BLACK);
		panel.add(message);

		JLabel deviceNameLabel = new JLabel("Device Name");
		deviceNameLabel.setBounds(370, 250, 120, 25);
		deviceNameLabel.setFont(new Font("Time New Roman", Font.ITALIC, 18));
		panel.add(deviceNameLabel);

		final JTextField deviceNameInput = new JTextField(20);
		deviceNameInput.setBounds(500, 250, 160, 25);
		panel.add(deviceNameInput);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(370, 290, 120, 25);
		passwordLabel.setFont(new Font("Time New Roman", Font.ITALIC, 18));
		panel.add(passwordLabel);

		final JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(500, 290, 160, 25);
		panel.add(passwordText);

		JLabel RepasswordLabel = new JLabel("Re Password");
		RepasswordLabel.setBounds(370, 330, 120, 25);
		RepasswordLabel.setFont(new Font("Time New Roman", Font.ITALIC, 18));
		panel.add(RepasswordLabel);

		final JPasswordField RepasswordText = new JPasswordField(20);
		RepasswordText.setBounds(500, 330, 160, 25);
		panel.add(RepasswordText);

		JButton addDevice = new JButton("Register Device");
		addDevice.setBounds(500, 375, 160, 25);
		panel.add(addDevice);

		addDevice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (deviceNameInput.getText().trim().length() > 0) {
					String deviceName = deviceNameInput.getText().trim().toString();
					if (passwordText.getPassword().length > 0) {
						if (RepasswordText.getPassword().length > 0) {
							String pass = String.valueOf(passwordText.getPassword());
							String rePass = String.valueOf(RepasswordText.getPassword());
							if (pass.equals(rePass)) {
								String response = "false";
								try {
									response = WSInvokes.registerUserDevice(deviceName, pass);
								} catch (JSONException | IOException e1) {
									e1.printStackTrace();
								}

								if (response.equals("true")) {
									try {
										SuperUtil.markDeviceAsAdded();
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									dispose();
									try {
										new AddDeviceView().setVisible(true);
									} catch (IOException | JSONException e1) {
										e1.printStackTrace();
									}
								} else if (response.equals("null")) {
									SuperUtil.errorMsg("Please check internet connection.");
								} else if (response.equals("error_MacAddressNotNniqe")) {
									SuperUtil.errorMsg("This device is already added by another user.");
								} else if (response.equals("false")) {
									SuperUtil.errorMsg("There is an error please try again.");
								}

							} else {
								SuperUtil.errorMsg("password and re-password are not equal");
							}
						} else {
							SuperUtil.errorMsg("you should Re-Enter RePassword");
						}
					} else {
						SuperUtil.errorMsg("you should Enter Password");
					}
				} else {
					SuperUtil.errorMsg("you should Enter Devcie Name");
				}

			}
		});

	}

	public static String getMacAddress() {
		InetAddress ip = null;
		StringBuilder sb = new StringBuilder();
		try {
			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();
			for (int i = 0; i < mac.length; i++)
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));

		} catch (UnknownHostException | SocketException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
