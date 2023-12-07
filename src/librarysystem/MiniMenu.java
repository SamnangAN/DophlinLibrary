package librarysystem;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import business.LoginException;
import business.SystemController;
import javax.swing.JOptionPane;

import business.LoginException;


public class MiniMenu extends JFrame implements LibWindow {
	public static final MiniMenu INSTANCE = new MiniMenu();
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

	private MiniMenu() {
	}

	public void init() {
		setSize(900, 600);

		ImageBackgroundPanel backgroundPanel = new ImageBackgroundPanel(Util.getImagePath() + "login-background.png");

		JScrollPane formPanel = createSigninForm();
		
		backgroundPanel.add(formPanel, BorderLayout.WEST);

		getContentPane().add(backgroundPanel);
		setLocationRelativeTo(null);
	}

	private JScrollPane createSigninForm() {
		String[] buttonLabels = {"Button 1", "Button 2", "Button 3", "Button 4"};

        // Create a JList with the button labels
        JList<String> buttonList = new JList<>(buttonLabels);

        // Set a custom cell renderer for the JList
        buttonList.setCellRenderer(new ButtonListCellRenderer());

        // Add the JList to the content pane
        return new JScrollPane(buttonList);
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
	
    static class ButtonListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JButton button = new JButton(value.toString());
            button.setBorder(new EmptyBorder(5, 10, 5, 10)); // Add padding
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            button.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());

            return button;
        }
    }

}
