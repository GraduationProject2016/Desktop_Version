package fmd_desktop_clint.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class login extends JFrame {
	private int lastOnlineTimeSeconds;
	private int registrationTimeSeconds;
	private final static String USER_AGENT = "Mozilla/5.0";
	private static String name;
	private static String pass;
	public static JFrame frame = new JFrame("Login");

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// frame.setSize(800, 550);
		frame.setBounds(250, 115, 800, 550);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		// panel.setBounds(800, 550, 800, 100);
		frame.add(panel);
		placeComponents(panel);

		frame.setVisible(true);

	}

	private static void placeComponents(JPanel panel) {

		panel.setLayout(null);

		JLabel userLabel = new JLabel("User");
		userLabel.setBounds(250, 140, 80, 25);
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
				if (userText.getText().trim().length() > 0 && passwordText.getPassword().length > 0) {
					name = userText.getText().trim();
					pass = passwordText.getPassword().toString();
					System.out.println(name + " " + pass);
					new AddDevice().setVisible(true);
					frame.dispose();

				} else {
					errorMsg();
				}

			}
		});

	}

	private static void HttpRequst(String url) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println(response.toString());

	}

	public static void rating() throws Exception {
		String url = " http://codeforces.com/api/user.rating?handle=" + name;
		HttpRequst(url);
	}

	public static void errorMsg() {
		String message = "you should write somthing";
		JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
	}
}
