package librarysystem;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JOptionPane;


public class AddBook extends JFrame implements LibWindow {
	public static final AddBook INSTANCE = new AddBook();
	private JTextField isbnTxtField;
	private JTextField titleTxtField;
	private JTextField maximunCheckoutTxtField;
	private JTextField numberOfCopyTxtField;
	private JTextField authorTxtField;

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

	private AddBook() {
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

		JLabel isbnLbl = new JLabel("ISBN:");
		isbnTxtField = new JTextField(15);

		JLabel titleLbl = new JLabel("Title:");
		titleTxtField = new JPasswordField(15);
		
		JLabel maximunCheckoutLbl = new JLabel("Maximum Checkout Length:");
		maximunCheckoutTxtField = new JPasswordField(15);
		
		JLabel numberOfCopyLbl = new JLabel("Number Of Copy:");
		numberOfCopyTxtField = new JPasswordField(15);

		JButton addButton = new JButton("Add");
//		addLoginButtonListener(loginButton);

		gbc.gridx = 0;
		gbc.gridy = 0;
		formPanel.add(isbnLbl, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		formPanel.add(isbnTxtField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		formPanel.add(titleLbl, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		formPanel.add(titleTxtField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		formPanel.add(maximunCheckoutLbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		formPanel.add(maximunCheckoutTxtField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		formPanel.add(numberOfCopyLbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		formPanel.add(numberOfCopyTxtField, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		formPanel.add(addButton, gbc);

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

	private void addLoginButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			MainMenu.INSTANCE.init();
			Util.centerFrameOnDesktop(MainMenu.INSTANCE);
			MainMenu.INSTANCE.setVisible(true);
		});
	}

}
