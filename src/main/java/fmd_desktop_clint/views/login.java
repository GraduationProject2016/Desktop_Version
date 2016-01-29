package fmd_desktop_clint.views;

import java.awt.BorderLayout;
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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;

import org.json.JSONException;
import org.json.JSONObject;

import fmd_desktop_clint.util.CommonUtil;
import fmd_desktop_clint.util.WebServiceConnector;

public class login extends JFrame {

	private int lastOnlineTimeSeconds;
	private int registrationTimeSeconds;
	private final static String USER_AGENT = "Mozilla/5.0";
	private static String userName;
	private static String password;
	public static JFrame frame = new JFrame("Find My Device | Login");

	public login() throws IOException {
		boolean flag = false;
		if (new File("configfile.txt").exists()) {
			String[] arr = readConfigFile();
			if (arr.length > 0) {
				if (!arr[1].equals("0")) {
					flag = true;
				}
			}
		}

		this.setResizable(false);
		if (flag) {
			frame.dispose();
			new AddDevice().setVisible(true);
		} else {
			frame.setSize(800, 550);
			frame.setBounds(250, 115, 800, 550);

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			JPanel panel = new JPanel();
			// panel.setBounds(800, 550, 800, 100);
			frame.add(panel);
			placeComponents(panel);
			// Creates a menubar for a JFrame
			JMenuBar menuBar = new JMenuBar();

			// Add the menubar to the frame
			setJMenuBar(menuBar);

			// Define and add two drop down menu to the menubar
			JMenu fileMenu = new JMenu("File");
			JMenu editMenu = new JMenu("Edit");
			JMenu helpMenu = new JMenu("Help");
			JMenu aboutMenu = new JMenu("about");
			menuBar.add(fileMenu);
			menuBar.add(editMenu);
			menuBar.add(helpMenu);
			menuBar.add(aboutMenu);
			menuBar.add(editMenu);

			// Create and add simple menu item to one of the drop down menu
			JMenuItem newAction = new JMenuItem("New");
			JMenuItem openAction = new JMenuItem("Open");
			JMenuItem exitAction = new JMenuItem("Exit");
			JMenuItem cutAction = new JMenuItem("Cut");
			JMenuItem copyAction = new JMenuItem("Copy");
			JMenuItem pasteAction = new JMenuItem("Paste");

			// Create and add CheckButton as a menu item to one of the drop down
			// menu
			JCheckBoxMenuItem checkAction = new JCheckBoxMenuItem("Check Action");
			// Create and add Radio Buttons as simple menu items to one of the
			// drop
			// down menu
			JRadioButtonMenuItem radioAction1 = new JRadioButtonMenuItem("Radio Button1");
			JRadioButtonMenuItem radioAction2 = new JRadioButtonMenuItem("Radio Button2");
			// Create a ButtonGroup and add both radio Button to it. Only one
			// radio
			// button in a ButtonGroup can be selected at a time.
			ButtonGroup bg = new ButtonGroup();
			bg.add(radioAction1);
			bg.add(radioAction2);
			fileMenu.add(newAction);
			fileMenu.add(openAction);
			fileMenu.add(checkAction);
			fileMenu.addSeparator();
			fileMenu.add(exitAction);
			editMenu.add(cutAction);
			editMenu.add(copyAction);
			editMenu.add(pasteAction);
			editMenu.addSeparator();
			editMenu.add(radioAction1);
			editMenu.add(radioAction2);
			// Add a listener to the New menu item. actionPerformed() method
			// will
			// invoked, if user triggred this menu item
			newAction.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					System.out.println("You have clicked on the new action");
				}
			});

			setVisible(true);
		}
	}

	private static void placeComponents(JPanel panel) {

		panel.setLayout(new BorderLayout());

		JLabel userLabel = new JLabel("Username/Email");
		userLabel.setBounds(250, 140, 100, 25);
		panel.add(userLabel);

		final JTextField userText = new JTextField(20);
		userText.setBounds(350, 140, 160, 25);
		panel.add(userText);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(250, 180, 80, 25);
		panel.add(passwordLabel);

		final JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(350, 180, 160, 25);
		panel.add(passwordText);

		JButton loginButton = new JButton("login");
		loginButton.setBounds(250, 250, 80, 25);
		panel.add(loginButton);

		JButton registerButton = new JButton("register");
		registerButton.setBounds(430, 250, 80, 25);
		panel.add(registerButton);
		registerButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				try {
					String url = "http://www.google.com";
					java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
				} catch (MalformedURLException e) {

					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		loginButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				userName = userText.getText().trim();
				password = passwordText.getText().trim();

				if (!userName.equals("") && !password.equals("")) {

					try {
						File addDeviceFile = new File("configfile.txt");
						if (!addDeviceFile.exists()) {
							addDeviceFile.createNewFile();
							PrintWriter writer = new PrintWriter(addDeviceFile, "UTF-8");
							writer.println("0 , 0 , 0");
							writer.close();
						}

						boolean isDeviceAdded = CommonUtil.isAddedDevice();

						String response = "";
						try {
							response = isAuzorizedUser(userName, password);
						} catch (JSONException e1) {
							e1.printStackTrace();
						}

						if (!isDeviceAdded && response.equals("true")) {
							new AddDevice().setVisible(true);
							frame.dispose();
						} else if (response.equals("null")) {
							errorMsg("Please check internet connection.");
						} else if (isDeviceAdded) {
							errorMsg("This device is already added.");
						} else if (response.equals("error_not_active")) {
							errorMsg("Please Activate your account, check your mail.");
						} else if (response.equals("false")) {
							errorMsg("Login Error, check your username/email or password.");
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				} else {
					if (userName.equals("") && password.equals("")) {
						errorMsg("Please insert your username/email and password");
					} else if (userName.equals("")) {
						errorMsg("Please insert your username or email");
					} else if (password.equals("")) {
						errorMsg("Please insert your password");
					}
				}

			}
		});

	}

	private static boolean isEmail(String input) {
		int atIndex = input.indexOf('@');

		if (atIndex == -1)
			return false;

		int lastDotIndex = input.lastIndexOf('.');

		return atIndex < lastDotIndex && lastDotIndex - atIndex != 1 && lastDotIndex != input.length() - 1
				&& atIndex != 0;
	}

	public static String isAuzorizedUser(String username, String password) throws JSONException, IOException {
		String login_by = "";
		if (isEmail(username))
			login_by = "email";
		else
			login_by = "username";

		String url = "http://localhost:8080/fmd/webService/user/login/" + login_by + "/" + username + "/" + password;
		String response = WebServiceConnector.getResponeString(url);

		if (response == null) {
			return "null";
		}

		JSONObject obj = new JSONObject(response);
		if (obj.getString("status").equals("Success") && obj.getBoolean("active") == true) {
			saveUserID(obj.getInt("id"));
			return "true";
		} else if (obj.getString("status").equals("Success") && obj.getBoolean("active") == false) {
			return "error_not_active";
		}
		return "false";
	}

	public static void saveUserID(int userID) throws IOException {
		File addDeviceFile = new File("configfile.txt");
		BufferedReader brTest = new BufferedReader(new FileReader(addDeviceFile));
		String[] arr = brTest.readLine().split(" , ");

		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("configfile.txt", false)))) {
			out.println(arr[0] + " , " + userID + " , " + arr[2]);
		} catch (IOException e) {
		}
	}

	public static String[] readConfigFile() throws IOException {
		File addDeviceFile = new File("configfile.txt");
		BufferedReader brTest = new BufferedReader(new FileReader(addDeviceFile));
		String[] arr = brTest.readLine().split(" , ");
		return arr;
	}

	public static void logout() throws IOException {
		File addDeviceFile = new File("configfile.txt");
		BufferedReader brTest = new BufferedReader(new FileReader(addDeviceFile));
		String[] arr = brTest.readLine().split(" , ");

		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("configfile.txt", false)))) {
			out.println(arr[0] + " , " + 0 + " , " + arr[2]);
		} catch (IOException e) {
		}
	}

	public static void errorMsg(String message) {
		JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
	}

}