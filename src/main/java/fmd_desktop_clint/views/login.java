package fmd_desktop_clint.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

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

import fmd_desktop_clint.socet.Connection;
import fmd_desktop_clint.util.CommonUtil;
import fmd_desktop_clint.util.Constants;
import fmd_desktop_clint.util.WebServiceConnector;

public class login extends JFrame {

	private final static String USER_AGENT = "Mozilla/5.0";
	private static String userName;
	private static String password;
	private static String hostname;
	public static File hostNameFile = new File(Constants.HOSTNAME_File);
	public static JFrame frame = new JFrame("Find My Device | Login");

	public static void main(String[] args) throws IOException {

		boolean flag = false;
		if (new File(Constants.FILE_PATH).exists()) {
			String[] arr = readConfigFile();
			if (arr.length > 0) {
				if (!arr[1].equals("0")) {
					flag = true;
				}
			}
		} else {
			File addDeviceFile = new File(Constants.FILE_PATH);
			if (!addDeviceFile.exists()) {
				addDeviceFile.getParentFile().mkdirs();
				addDeviceFile.createNewFile();
				new File(Constants.LOG_FILE).createNewFile();
				PrintWriter writer = new PrintWriter(addDeviceFile, "UTF-8");
				writer.println("0 , 0 , 0");
				writer.close();
			}

		}

		String jarPath = getautostart();

		if (hostNameFile.exists())
			hostname = CommonUtil.getHostName();

		copyFile(getrunningdir() + "\\Find My Device.exe", jarPath + "\\Find My Device.exe");

		String path = new File(".").getCanonicalPath();
		if (path.contains("Microsoft\\Windows\\Start Menu\\Programs\\Startup")) {
			//WorkInBackground(args);
			doWork();
		} else if (flag) {
			new AddDevice().setVisible(true);
			//WorkInBackground(args);
			doWork();
		} else {
			new login();
			// WorkInBackground(args);
			doWork();
		}

	}

	public static void setupHostNameFile(String hostname_) throws IOException {
		if (!hostNameFile.exists())
			hostNameFile.createNewFile();

		PrintWriter writer = new PrintWriter(hostNameFile, "UTF-8");
		writer.println(hostname_);
		writer.close();
	}

	public static void copyFile(String source, String dest) throws IOException {
		if (!new File(dest).exists()) {
			InputStream input = null;
			OutputStream output = null;
			try {
				input = new FileInputStream(source);
				output = new FileOutputStream(dest);
				byte[] buf = new byte[1024];
				int bytesRead;
				while ((bytesRead = input.read(buf)) > 0) {
					output.write(buf, 0, bytesRead);
				}
			} finally {
				input.close();
				output.close();
			}
		}
	}

	public static String getautostart() {
		return System.getProperty("java.io.tmpdir").replace("Local\\Temp\\",
				"Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup");
	}

	public static String getrunningdir() throws IOException {
		String runningdir = new File(".").getCanonicalPath().toString();
		return runningdir;
	}

	public static void WorkInBackground(String[] args) throws IOException {
		String customMessage = "default";
		if (args.length > 0) {
			customMessage = args[0];
		}

		FileOutputStream out = new FileOutputStream(new File(Constants.LOG_FILE), true);
		PrintStream printStream = new PrintStream(out);
		System.setOut(printStream);
		Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook()));

		onStart();
		// doWork(customMessage);
	}

	private static void doWork() throws IOException {
		boolean flag = true;
		Connection con = new Connection();
		while (flag) {		
			if (con.signIn()) {
				
			}else{
				con = new Connection();
			}
			// System.out.println("Connect to server at " + new Date() + " " +
			// customMessage);
			try {
				Thread.sleep(60000 * 4);
			} catch (InterruptedException e) {
				System.out.println("Interrupted at " + new Date());
			}
		}
	}

	// private static void doWork(String customMessage) throws IOException {
	// boolean flag = true;
	// while (flag) {
	// Connection con = new Connection();
	// if (con.signIn()) {
	// flag = false;
	// }
	// System.out.println("Connect to server at " + new Date() + " " +
	// customMessage);
	// try {
	// Thread.sleep(60000 * 4);
	// } catch (InterruptedException e) {
	// System.out.println("Interrupted at " + new Date());
	// }
	// }
	// }

	private static void onStart() {
		System.out.println("Starts at " + new Date());
	}

	private static class ShutdownHook implements Runnable {

		public void run() {
			onStop();
		}

		private void onStop() {
			// System.out.println("Ends at " + new Date());
			System.out.flush();
			System.out.close();
		}

	}

	public login() {
		super("Find My Device | Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setResizable(false);
		frame = this;
		setBounds(250, 115, 800, 550);
		JPanel panel = new JPanel();
		add(panel);

		URL url = getClass().getResource("/resources/logo.png");
		ImageIcon icon = new ImageIcon(url);
		setIconImage(icon.getImage());

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu web = new JMenu("Web");
		JMenu helpMenu = new JMenu("Help");
		JMenu aboutMenu = new JMenu("about");
		JMenu serverMenu = new JMenu("server");

		menuBar.add(web);
		menuBar.add(serverMenu);
		menuBar.add(helpMenu);
		menuBar.add(aboutMenu);

		// Create and add simple menu item to one of the drop down menu
		JMenuItem registerAction = new JMenuItem("Register");
		JMenuItem openAction = new JMenuItem("Open");
		JMenuItem exitAction = new JMenuItem("Exit");
		JMenuItem hostnameAction = new JMenuItem("Host Name");

		serverMenu.add(hostnameAction);

		web.add(registerAction);
		web.add(openAction);
		web.addSeparator();
		web.add(exitAction);

		placeComponents(panel);
		setVisible(true);

		hostnameAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object result;
				try {
					result = JOptionPane.showInputDialog(frame, "Enter Host Name: ", CommonUtil.getHostName());
					hostname = result.toString();
					setupHostNameFile(result.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		exitAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		registerAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String url = hostname + "/fmd/signup.xhtml";
					java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
				} catch (MalformedURLException e) {

					e.printStackTrace();
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

	}

	private static void placeComponents(JPanel panel) {

		panel.setLayout(null);

		JLabel message = new JLabel("Find My Device");
		message.setBounds(255, 20, 750, 100);
		message.setFont(new Font("Time New Roman", Font.ITALIC, 36));
		message.setOpaque(true);
		message.setForeground(Color.BLACK);
		panel.add(message);

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
		loginButton.setBounds(350, 220, 160, 25);
		panel.add(loginButton);

		JButton registerButton = new JButton("Do not have account ?");
		registerButton.setBounds(250, 290, 260, 25);
		panel.add(registerButton);
		registerButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				try {
					String url = hostname + "/fmd/signup.xhtml";
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

						String response = "";
						try {
							response = isAuzorizedUser(userName, password);
						} catch (JSONException e1) {
							e1.printStackTrace();
						}

						if (response.equals("true")) {
							frame.dispose();
							new AddDevice().setVisible(true);
						} else if (response.equals("null")) {
							errorMsg("Please check internet connection.");
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

		String url = hostname + "/fmd/webService/user/login/" + login_by + "/" + username + "/" + password;
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
		File addDeviceFile = new File(Constants.FILE_PATH);
		BufferedReader brTest = new BufferedReader(new FileReader(addDeviceFile));
		String[] arr = brTest.readLine().split(" , ");

		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Constants.FILE_PATH, false)))) {
			out.println(arr[0] + " , " + userID + " , " + arr[2]);
		} catch (IOException e) {
		}
	}

	public static String[] readConfigFile() throws IOException {
		File addDeviceFile = new File(Constants.FILE_PATH);
		BufferedReader brTest = new BufferedReader(new FileReader(addDeviceFile));
		String[] arr = brTest.readLine().split(" , ");
		return arr;
	}

	public static void errorMsg(String message) {
		JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
	}

}