package librarysystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

import business.ControllerInterface;

import business.SystemController;


public class LoginWindow extends JFrame implements LibWindow {
    public static final LoginWindow INSTANCE = new LoginWindow();
	
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
	
	/* This class is a singleton */
    private LoginWindow () {}
    
    public void init() {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);

        JPanel mainPanel = new JPanel();
        
        
        // Create a panel with a custom background image
        //ImageBackgroundPanel leftPanel = new ImageBackgroundPanel(Util.getImagePath() + "left.png");
        JPanel leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                super.setSize(600, 800);
                // Load the background image
                Image image =new ImageIcon(Util.getImagePath() + "library_left.jpg").getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        // Right panel with login form
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        //formPanel.setSize(600,800);
        formPanel.setOpaque(true); // Make the form panel transparent

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Login form components
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(15);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your login logic here
                JOptionPane.showMessageDialog(LoginWindow.this, "Login button clicked!");
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(loginButton, gbc);

        // Add the form panel to the background panel
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(formPanel, BorderLayout.EAST);

        getContentPane().add(mainPanel);
        setLocationRelativeTo(null);
    }      
    
}
