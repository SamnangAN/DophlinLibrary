package librarysystem;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import business.LoginException;
import business.SystemController;

public class LoginWindow extends JFrame implements LibWindow {

	public static final LoginWindow INSTANCE = new LoginWindow();
	private JTextField username;
	private JTextField password;

	private boolean isInitialized = false;

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	private JTextField messageBar = new JTextField();

	public void clear() {
		messageBar.setText("");
		username.setText("");
		password.setText("");
	}

	private LoginWindow() {
		setSize(900, 600);

		ImageBackgroundPanel backgroundPanel = new ImageBackgroundPanel(Util.getImagePath() + "login-background.png");

		JPanel formPanel = createSigninForm();

		backgroundPanel.setLayout(new BorderLayout());
		backgroundPanel.add(formPanel);

		getContentPane().add(backgroundPanel);
		setLocationRelativeTo(null);
		System.out.println("Initiating login window");
		init();
	}

	public void init() {
		clear();
	}

	private JPanel createSigninForm() {
		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setOpaque(false);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 10, 10, 10);

		JLabel usernameLabel = new JLabel("Username:");
		username = new JTextField(15);

		JLabel passwordLabel = new JLabel("Password:");
		password = new JPasswordField(15);

		JButton loginButton = new JButton("Login");
		addLoginButtonListener(loginButton);

		formPanel.add(usernameLabel, getGb(gbc, 0, 0));
		formPanel.add(username, getGb(gbc, 1, 0));
		formPanel.add(passwordLabel, getGb(gbc, 0, 1));
		formPanel.add(password, getGb(gbc, 1, 1));
		formPanel.add(loginButton, getGb(gbc, 1, 2));
		formPanel.setBorder(BorderFactory.createEmptyBorder(0, 300, 0, 0));
		return formPanel;
	}

	private GridBagConstraints getGb(GridBagConstraints gbc, int gridX, int gridY) {
		gbc.gridx = gridX;
		gbc.gridy = gridY;
		return gbc;
	}

	public static boolean isValidUsername(String username) {
		String regex = "^[a-z0-9_]{3,16}$";
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(username).matches();
	}

	public static boolean isValidPassword(String password) {
		String regex = "^[0-9a-zA-Z]+$";
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(password).matches();
	}

	private void addLoginButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			SystemController systemController = new SystemController();
			boolean isValidUsername = false;
			boolean isValidPassword = false;
			System.out.println("Username: " + username.getText());
			System.out.println("Password: " + password.getText());
			try {

				if (isValidUsername(username.getText().toString())) {
					isValidUsername = true;
				} else {
					JOptionPane.showMessageDialog(this, "Username should be only small letter or number!");
					System.out.println("Username should be only small letter or number!");
				}

				if (isValidUsername) {
					if (isValidPassword(password.getText().toString())) {
						isValidPassword = true;
					} else {
						JOptionPane.showMessageDialog(this, "Password should be only number or letter!");
						System.out.println("Password should be only number or letter!");
					}
				}

				if (isValidUsername && isValidPassword) {

					systemController.login(username.getText().toString(), password.getText().toString());

					if (SystemController.currentAuth != null) {
						LibrarySystem.INSTANCE.setLoggedIn(true);
						LibrarySystem.INSTANCE.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(this, "Username or Password mismatch!");
					}
				}

			} catch (LoginException e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
		});
	}

	private static class ImageBackgroundPanel extends JPanel {
		private Image backgroundImage;

		public ImageBackgroundPanel(String imagePath) {
			this.backgroundImage = new ImageIcon(imagePath).getImage();
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}
	}
}
