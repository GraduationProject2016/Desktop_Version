package fmd_desktop_clint.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONException;
import org.json.JSONObject;

import fmd_desktop_clint.util.CommonUtil;
import fmd_desktop_clint.util.Constants;
import fmd_desktop_clint.util.WebServiceConnector;

public class AddDevice extends JFrame {

	private static String hostname;

	public AddDevice() {
		super("Find My Device | Add  Device");
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
				new ConnectToServer().setVisible(true);
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
					new login();
					dispose();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		openAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String url = hostname + "/fmd/";
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

	private void placeMessage(JPanel panel) {
		panel.setLayout(null);
		JLabel message = new JLabel("This Computer is already added Successfully");
		message.setBounds(70, 100, 750, 100);
		message.setFont(new Font("Serif", Font.PLAIN, 35));
		message.setOpaque(true);
		// message.setBackground(Color.GRAY);
		message.setForeground(Color.red);
		panel.add(message);
	}

	private void placeComponents(JPanel panel) {
		panel.setLayout(null);

		JLabel message = new JLabel("Register Your Device to use our service.");
		message.setBounds(150, 20, 750, 100);
		message.setFont(new Font("Time New Roman", Font.ITALIC, 30));
		message.setOpaque(true);
		message.setForeground(Color.BLACK);
		panel.add(message);

		JLabel deviceNameLabel = new JLabel("Device Name");
		deviceNameLabel.setBounds(250, 140, 80, 25);
		panel.add(deviceNameLabel);

		final JTextField deviceNameInput = new JTextField(20);
		deviceNameInput.setBounds(350, 140, 160, 25);
		panel.add(deviceNameInput);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(250, 180, 80, 25);
		panel.add(passwordLabel);

		final JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(350, 180, 160, 25);
		panel.add(passwordText);

		JLabel RepasswordLabel = new JLabel("RePassword");
		RepasswordLabel.setBounds(250, 220, 80, 25);
		panel.add(RepasswordLabel);

		final JPasswordField RepasswordText = new JPasswordField(20);
		RepasswordText.setBounds(350, 220, 160, 25);
		panel.add(RepasswordText);

		JButton addDevice = new JButton("Add device");
		addDevice.setBounds(350, 260, 160, 25);
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
									response = registerUserDevice(deviceName, pass);
								} catch (JSONException | IOException e1) {
									e1.printStackTrace();
								}

								if (response.equals("true")) {
									try {
										markDeviceAsAdded();
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									dispose();
									new AddDevice().setVisible(true);
								} else if (response.equals("null")) {
									errorMsg("Please check internet connection.");
								} else if (response.equals("error_MacAddressNotNniqe")) {
									errorMsg("This device is already added by another user.");
								} else if (response.equals("false")) {
									errorMsg("There is an error please try again.");
								}

							} else {
								errorMsg("password and re-password are not equal");
							}
						} else {
							errorMsg("you should Re-Enter RePassword");
						}
					} else {
						errorMsg("you should Enter Password");
					}
				} else {
					errorMsg("you should Enter Devcie Name");
				}

			}
		});

	}

	public static void errorMsg(String message) {
		JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
	}

	public static String getMacAddress() {
		InetAddress ip = null;
		StringBuilder sb = new StringBuilder();
		try {
			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			//System.out.println(network.getHardwareAddress().toString());
			byte[] mac = network.getHardwareAddress();
			for (int i = 0; i < mac.length; i++)
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));

		} catch (UnknownHostException | SocketException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String registerUserDevice(String deviceName, String devicePassword)
			throws JSONException, IOException {

		String os = System.getProperty("os.name").toLowerCase().contains("windows") ? "WINDOWS" : "LINUX";
		int userID = CommonUtil.getUserID();
		String deviceID = getMacAddress();

		String url = hostname + "/fmd/webService/device/register/" + deviceName + "/" + devicePassword + "/"
				+ deviceID + "/" + os + "/" + userID;

		String response = WebServiceConnector.getResponeString(url);

		if (response == null) {
			return "null";
		}

		JSONObject obj = new JSONObject(response);

		if (obj.getString("status").equals("Success")) {
			saveDeviceID(obj.getInt("id"));
			return "true";
		} else if (obj.getString("status").contains("MacAddressNotNniqe")) {
			return "error_MacAddressNotNniqe";
		}
		return "false";
	}

	public static void saveDeviceID(int deviceID) throws IOException {
		File addDeviceFile = new File(Constants.FILE_PATH);
		BufferedReader brTest = new BufferedReader(new FileReader(addDeviceFile));
		String[] arr = brTest.readLine().split(" , ");

		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Constants.FILE_PATH, false)))) {
			out.println(arr[0] + " , " + arr[1] + " , " + deviceID);
		} catch (IOException e) {
		}
	}

	public static void markDeviceAsAdded() throws IOException {
		File addDeviceFile = new File(Constants.FILE_PATH);
		BufferedReader brTest = new BufferedReader(new FileReader(addDeviceFile));
		String[] arr = brTest.readLine().split(" , ");

		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Constants.FILE_PATH, false)))) {
			out.println(1 + " , " + arr[1] + " , " + arr[2]);
		} catch (IOException e) {
		}
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
