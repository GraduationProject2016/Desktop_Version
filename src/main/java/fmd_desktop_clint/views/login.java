package fmd_desktop_clint.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;

import javax.imageio.ImageIO;
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

@SuppressWarnings("serial")
public class login extends JFrame {

	private static String userName;
	private static String password;
	private static String hostname;
	public static File hostNameFile = new File(Constants.HOSTNAME_File);
	public static JFrame frame = new JFrame("Find My Device | Login");

	public static void main(String[] args) throws IOException, JSONException {

		boolean flag = false;
		if (new File(Constants.FILE_PATH).exists()) {
			String[] arr = CommonUtil.readConfigFile();
			if (arr.length > 0) {
				if (!arr[1].equals("0")) {
					flag = true;
				}

				if (arr[0].equals("1")) {
					boolean deletedDevice = CommonUtil.isDeletedDevice(getMacAddress());
					if (deletedDevice)
						CommonUtil.DeleteDevice();
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

		if (hostNameFile.exists())
			hostname = CommonUtil.getHostName();

		login l = new login("aaaaaaa");
		l.copyConfigsFiles();

		if (getrunningdir().contains("Microsoft\\Windows\\Start Menu\\Programs\\Startup")) {
			doWork();
		} else if (flag) {
			new AddDevice().setVisible(true);
			doWork();
		} else {
			new login();
			doWork();
		}

	}

	public void copyConfigsFiles() {
		String jarPath = getautostart();
		if (!new File(jarPath + "\\Find My Device.exe").exists()) {
			try {
				copyFile(getrunningdir() + "\\Find My Device.exe", jarPath + "\\Find My Device.exe");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		URL url = getClass().getResource("/resources/webrecording.jar");
		File webrecordingJar = new File(url.getPath());

		if (!new File(Constants.APPDATA + "\\webrecording.jar").exists()) {
			try {
				copyFile(webrecordingJar.getAbsolutePath(), Constants.APPDATA + "\\webrecording.jar");
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		// String customMessage = "default";
		// if (args.length > 0)
		// customMessage = args[0];

		FileOutputStream out = new FileOutputStream(new File(Constants.LOG_FILE), true);
		PrintStream printStream = new PrintStream(out);
		System.setOut(printStream);
		Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook()));

		// doWork(customMessage);
	}

	private static void doWork() throws IOException {
		Connection con = new Connection();
		while (true) {

			if (!con.isConnected()) {
				con = new Connection();
			}

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				System.out.println("Interrupted at " + new Date());
			}

		}
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
					String url = "http://" + hostname + ":8080/fmd/signup.xhtml";
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
					String url = "http://" + hostname + ":8080/fmd/";
					java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
				} catch (MalformedURLException e) {

					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

	}

	public login(String string) {
		// TODO Auto-generated constructor stub
	}

	public static String getMacAddress() {
		InetAddress ip = null;
		StringBuilder sb = new StringBuilder();
		try {
			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			// System.out.println(network.getHardwareAddress().toString());
			byte[] mac = network.getHardwareAddress();
			for (int i = 0; i < mac.length; i++)
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));

		} catch (UnknownHostException | SocketException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	private void placeComponents(JPanel panel) {

		panel.setLayout(null);

		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File(getClass().getResource("/resources/bkimage.jpg").getPath()));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			picLabel.setBounds(-100, 0, 1000, 150);
			panel.add(picLabel);
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		JLabel message = new JLabel("Find My Device");
		message.setBounds(255, 140, 750, 100);
		message.setFont(new Font("Time New Roman", Font.ITALIC, 36));
		message.setOpaque(true);
		message.setForeground(Color.BLACK);
		panel.add(message);

		JLabel userLabel = new JLabel("Username/Email");
		userLabel.setBounds(250, 240, 100, 25);
		panel.add(userLabel);

		final JTextField userText = new JTextField(20);
		userText.setBounds(350, 240, 160, 25);
		panel.add(userText);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(250, 280, 80, 25);
		panel.add(passwordLabel);

		final JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(350, 280, 160, 25);
		panel.add(passwordText);

		JButton loginButton = new JButton("login");
		loginButton.setBounds(350, 320, 160, 25);
		panel.add(loginButton);

		JButton registerButton = new JButton("Do not have account ?");
		registerButton.setBounds(250, 360, 260, 25);
		panel.add(registerButton);
		registerButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				try {
					String url = "http://" + hostname + ":8080/fmd/signup.xhtml";
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
							try {
								new AddDevice().setVisible(true);
							} catch (JSONException e1) {
								e1.printStackTrace();
							}
						} else if (response.equals("null")) {
							SuperUtil.errorMsg("Please check internet connection.");
						} else if (response.equals("error_not_active")) {
							SuperUtil.errorMsg("Please Activate your account, check your mail.");
						} else if (response.equals("false")) {
							SuperUtil.errorMsg("Login Error, check your username/email or password.");
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				} else {
					if (userName.equals("") && password.equals("")) {
						SuperUtil.errorMsg("Please insert your username/email and password");
					} else if (userName.equals("")) {
						SuperUtil.errorMsg("Please insert your username or email");
					} else if (password.equals("")) {
						SuperUtil.errorMsg("Please insert your password");
					}
				}

			}
		});

	}

	public static String isAuzorizedUser(String username, String password) throws JSONException, IOException {
		String login_by = "";
		login_by = SuperUtil.isEmail(username) ? "email" : "username";

		String url = "http://" + hostname + ":8080/fmd/webService/user/login/" + login_by + "/" + username + "/"
				+ password;
		String response = WebServiceConnector.getResponeString(url);
		if (response == null)
			return "null";

		JSONObject obj = new JSONObject(response);
		if (obj.getString("status").equals("Success") && obj.getBoolean("active") == true) {
			SuperUtil.saveUserID(obj.getInt("id"));
			return "true";
		} else if (obj.getString("status").equals("Success") && obj.getBoolean("active") == false) {
			return "error_not_active";
		}
		return "false";
	}

}