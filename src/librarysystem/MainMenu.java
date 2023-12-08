package librarysystem;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import business.ControllerInterface;
import business.SystemController;


public class MainMenu extends JFrame implements LibWindow {
	ControllerInterface ci = new SystemController();
	public final static MainMenu INSTANCE =new MainMenu();
	JPanel mainPanel;
	JMenuBar menuBar;
    JMenu options;
    JMenuItem login, allBookIds, allMemberIds; 
    String pathToImage;
    private boolean isInitialized = false;
    
    private static LibWindow[] allWindows = { 
    	MainMenu.INSTANCE,
		LoginWindow.INSTANCE,
		AllMemberIdsWindow.INSTANCE,	
		AllBookIdsWindow.INSTANCE
	};
    	
	public static void hideAllWindows() {		
		for(LibWindow frame: allWindows) {
			frame.setVisible(false);			
		}
	}
     
    private MainMenu() {}
    
    public void init() {
    	setPathToImage();
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setSize(900, 600);
    	JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
            	super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon(Util.getImagePath() + "library.jpg");
                Image img = backgroundImage.getImage();
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                g2d.drawImage(img, 0, 0, getWidth(), getHeight(), this);

                g2d.dispose();
            }
        };
        
        panel.setLayout(new BorderLayout());
        
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        JButton button1 = createStyledButton("CHECKOUT_BOOK", "books.png");
        JButton button2 = createStyledButton("PRINT_CHECKOUT", "books.png");
        JButton button3 = createStyledButton("OVERDUE_BOOK", "books.png");
        JButton button4 = createStyledButton("ADD_NEW_MEMBER", "books.png");
        JButton button5 = createStyledButton("ADD_BOOK", "books.png");
        JButton button6 = createStyledButton("ADD_NEW_COPY", "books.png");
        button5.addActionListener(new AddBookActionListener());
        

        GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 10, 10, 10);

		gbc.gridx = 0;
		gbc.gridy = 0;
		buttonPanel.add(button1, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		buttonPanel.add(button2, gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		buttonPanel.add(button3, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		buttonPanel.add(button4, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		buttonPanel.add(button5, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		buttonPanel.add(button6, gbc);

        panel.add(buttonPanel);

        panel.setBorder(new EmptyBorder(100, 100, 100, 100));

        getContentPane().add(panel);
        setLocationRelativeTo(null);
    }
    
    private static JButton createStyledButton(String text, String iconName) {
    	
        JButton button = new JButton(text);
        Insets margin = new Insets(30, 10, 30, 10);
        button.setMargin(margin);
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);
        button.setFont(buttonFont);
        ImageIcon imgIcon = new ImageIcon(Util.getImagePath() + iconName);
        button.setIcon(imgIcon);
        Dimension d = new Dimension(100, button.getPreferredSize().height);
        button.setPreferredSize(d);
        return button;
    }
    
    private void setPathToImage() {
    	String currDirectory = System.getProperty("user.dir");
    	pathToImage = currDirectory+"/src/images/library2.jpg";
    }
    
    class AddBookActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
			AllBookIdsWindow.INSTANCE.setVisible(true);
			
		}
    	
    }

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}


	@Override
	public void isInitialized(boolean val) {
		isInitialized =val;
		
	}
    
}
