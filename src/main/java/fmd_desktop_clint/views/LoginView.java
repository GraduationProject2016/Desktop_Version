package fmd_desktop_clint.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

import fmd_desktop_clint.socet.Connection;
import fmd_desktop_clint.util.CommonUtil;
import fmd_desktop_clint.util.Constants;
import fmd_desktop_clint.util.SuperUtil;
import fmd_desktop_clint.util.WSInvokes;

@SuppressWarnings("serial")
public class LoginView extends JFrame {

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
					boolean deletedDevice = WSInvokes.isDeletedDevice(getMacAddress());
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

		SuperUtil u = new SuperUtil();
		u.copyConfigsFiles();

		if (SuperUtil.getrunningdir().contains("Microsoft\\Windows\\Start Menu\\Programs\\Startup")) {
			doWork();
		} else if (flag) {
			new AddDeviceView().setVisible(true);
			doWork();
		} else {
			new LoginView();
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
			System.out.flush();
			System.out.close();
		}

	}

	public LoginView() {
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

	private void placeComponents(JPanel panel) {
		panel.setLayout(null);

		BufferedImage myPicture, iconProfile;
		try {
			iconProfile = ImageIO.read(new File(getClass().getResource("/resources/st_logo.png").getPath()));
			JLabel iconProfileLab = new JLabel(new ImageIcon(iconProfile));
			iconProfileLab.setBounds(100, 80, 150, 150);
			panel.add(iconProfileLab);

			myPicture = ImageIO.read(new File(getClass().getResource("/resources/bkimage.jpg").getPath()));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			picLabel.setBounds(-100, 0, 1000, 150);
			panel.add(picLabel);
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		JLabel view = new JLabel("We help people to access thier");
		view.setBounds(40, 220, 400, 100);
		view.setFont(new Font("Time New Roman", Font.BOLD, 20));
		view.setForeground(Color.BLACK);
		panel.add(view);

		JLabel view2 = new JLabel("lost devices and enable them to");
		view2.setBounds(40, 250, 400, 100);
		view2.setFont(new Font("Time New Roman", Font.BOLD, 20));
		view2.setForeground(Color.BLACK);
		panel.add(view2);

		JLabel view3 = new JLabel("retrive/delete file , know the");
		view3.setBounds(40, 280, 400, 100);
		view3.setFont(new Font("Time New Roman", Font.BOLD, 20));
		view3.setForeground(Color.BLACK);
		panel.add(view3);

		JLabel view4 = new JLabel("location of the device and record");
		view4.setBounds(40, 310, 400, 100);
		view4.setFont(new Font("Time New Roman", Font.BOLD, 20));
		view4.setForeground(Color.BLACK);
		panel.add(view4);

		JLabel view5 = new JLabel("voice or video.");
		view5.setBounds(40, 340, 400, 100);
		view5.setFont(new Font("Time New Roman", Font.BOLD, 20));
		view5.setForeground(Color.BLACK);
		panel.add(view5);

		JLabel message = new JLabel("Find My Device");
		message.setBounds(415, 160, 750, 100);
		message.setFont(new Font("Time New Roman", Font.ITALIC, 36));
		message.setOpaque(true);
		message.setForeground(Color.BLACK);
		panel.add(message);

		JLabel userLabel = new JLabel("Username/Email");
		userLabel.setBounds(410, 260, 100, 25);
		panel.add(userLabel);

		final JTextField userText = new JTextField(20);
		userText.setBounds(510, 260, 160, 25);
		panel.add(userText);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(410, 300, 80, 25);
		panel.add(passwordLabel);

		final JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(510, 300, 160, 25);
		panel.add(passwordText);

		JButton loginButton = new JButton("login");
		loginButton.setBounds(510, 340, 160, 25);
		panel.add(loginButton);

		JButton registerButton = new JButton("Do not have account ?");
		registerButton.setBounds(410, 380, 260, 25);
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
							response = WSInvokes.isAuzorizedUser(userName, password);
						} catch (JSONException e1) {
							e1.printStackTrace();
						}

						if (response.equals("true")) {
							frame.dispose();
							try {
								new AddDeviceView().setVisible(true);
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