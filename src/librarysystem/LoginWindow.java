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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import business.LoginException;
import business.SystemController;
import dataaccess.Auth;

import javax.swing.JOptionPane;

import business.LoginException;


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
	}

	private LoginWindow() {
	}

	public void init() {
		setSize(900, 600);

		ImageBackgroundPanel backgroundPanel = new ImageBackgroundPanel(Util.getImagePath() + "login-background.png");

		JPanel formPanel = createSigninForm();
		
		backgroundPanel.setLayout(new BorderLayout());
		backgroundPanel.add(formPanel);

		getContentPane().add(backgroundPanel);
		setLocationRelativeTo(null);
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

		gbc.gridx = 0;
		gbc.gridy = 0;
		formPanel.add(usernameLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		formPanel.add(username, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		formPanel.add(passwordLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		formPanel.add(password, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		formPanel.add(loginButton, gbc);

		formPanel.setBorder(BorderFactory.createEmptyBorder(0, 300, 0, 0));
		return formPanel;
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
	
	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
		});
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
			
			try {
				
				if (isValidUsername(username.getText().toString())) {
    				isValidUsername = true;
				}else {
					JOptionPane.showMessageDialog(this,"Username should be only small letter or number!");
    				System.out.println("Username should be only small letter or number!");
				}
    			
    			if (isValidUsername) {
					if (isValidPassword(password.getText().toString())) {
	    				isValidPassword = true;
					}else {
						JOptionPane.showMessageDialog(this,"Password should be only number or letter!");
	    				System.out.println("Password should be only number or letter!");
					}
				}
    			
    			
    			if (isValidUsername && isValidPassword) {
    				
    				systemController.login(username.getText().toString(), password.getText().toString());
    				
					if (SystemController.currentAuth == Auth.BOTH) {
						LibrarySystem.hideAllWindows();
						MainMenu.INSTANCE.init();
						Util.centerFrameOnDesktop(MainMenu.INSTANCE);
						MainMenu.INSTANCE.setVisible(true);
					}else if(SystemController.currentAuth == Auth.LIBRARIAN || 
						SystemController.currentAuth == Auth.ADMIN) {
						LibrarySystem.hideAllWindows();
						MiniMenu.INSTANCE.init();
						Util.centerFrameOnDesktop(MiniMenu.INSTANCE);
						MiniMenu.INSTANCE.setVisible(true);
					}
					else {
						JOptionPane.showMessageDialog(this,"Username or Password mismatch!");
					}
				}
    			
			} catch (LoginException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this,e.getMessage());
			}
		});
	}
}
