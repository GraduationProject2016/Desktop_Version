package fmd_desktop_clint.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AddDevice extends JFrame {
	public AddDevice() {
		super("Add  Device");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(250, 115, 800, 550);
		JPanel panel = new JPanel();
		add(panel);
		placeComponents(panel);
		setVisible(true);
	}

	private void placeComponents(JPanel panel) {
		panel.setLayout(null);
		JLabel deviceNameLabel = new JLabel("Device Name");
		deviceNameLabel.setBounds(250, 100, 80, 25);
		panel.add(deviceNameLabel);

		final JTextField deviceNameInput = new JTextField(20);
		deviceNameInput.setBounds(350, 100, 160, 25);
		panel.add(deviceNameInput);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(250, 140, 80, 25);
		panel.add(passwordLabel);

		final JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(350, 140, 160, 25);
		panel.add(passwordText);

		JLabel RepasswordLabel = new JLabel("RePassword");
		RepasswordLabel.setBounds(250, 180, 80, 25);
		panel.add(RepasswordLabel);

		final JPasswordField RepasswordText = new JPasswordField(20);
		RepasswordText.setBounds(350, 180, 160, 25);
		panel.add(RepasswordText);

		JButton addDevice = new JButton("Add");
		addDevice.setBounds(340, 250, 80, 25);
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
								String mac = getMacAddress();
								System.out.println(mac);
							} else {
								String message = "password and re-password are not equal";
								JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
										JOptionPane.ERROR_MESSAGE);
							}
						} else {
							String message = "you should Re-Enter RePassword";
							JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						String message = "you should Enter Password";
						JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					String message = "you should Enter Devcie Name";
					JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
				}

			}
		});

	}

	public static String getMacAddress() {
		InetAddress ip = null;
		StringBuilder sb = new StringBuilder();
		try {

			ip = InetAddress.getLocalHost();
			System.out.println("Current IP address : " + ip.getHostAddress());

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			System.out.print("Current MAC address : ");

			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			System.out.println(sb.toString());

		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (SocketException e) {

			e.printStackTrace();

		}
		return sb.toString();
	}

}
