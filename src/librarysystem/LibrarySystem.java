package librarysystem;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuEvent;

import business.ControllerInterface;
import business.SystemController;
import dataaccess.Auth;


public class LibrarySystem extends JFrame implements LibWindow {
	
	public final static LibrarySystem INSTANCE =new LibrarySystem();
	private JPanel mainPanel, cardPanel;
	private JPanel landscapePanel;
	private JMenuBar menuBar = new JMenuBar();
    private String pathToImage;
    private boolean isInitialized = false;
    private boolean isLoggedIn = false;
        
    private static LibWindow[] allWindows = { 
    	LibrarySystem.INSTANCE,
		LoginWindow.INSTANCE,
	};
    	
	public static void hideAllWindows() {		
		for(LibWindow frame: allWindows) {
			frame.setVisible(false);			
		}
	}
     
    private LibrarySystem() {}
    
    public void init() {
    	setPathToImage();
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null); // Center the frame
        initLandscapePanel();
        isInitialized = true;
    }
       
    private void initLandscapePanel() {
    	landscapePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the background image
                ImageIcon backgroundImage = new ImageIcon(pathToImage + "library.jpg");
                Image image = backgroundImage.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        landscapePanel.setLayout(new BorderLayout());  
        insertLibraryName(landscapePanel);    

        // Login button at the top right
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginListener());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false); // Make the button panel transparent
        buttonPanel.add(loginButton);
        landscapePanel.add(buttonPanel, BorderLayout.NORTH);

        // Set an empty border to center the content
        landscapePanel.setBorder(new EmptyBorder(20, 20, 20, 20));
       // getContentPane().get(landsc)
        getContentPane().add(landscapePanel);        
    }
    
    private void setPathToImage() {
    	String currDirectory = System.getProperty("user.dir");
    	pathToImage = currDirectory+"/src/images/";
    }
    
    private void insertLibraryName(JPanel panel) {
        JLabel centerLabel = new JLabel("Dolphin Library");
        centerLabel.setForeground(Color.WHITE); // Set text color
        centerLabel.setFont(new Font("Arial", Font.BOLD, 45));
        panel.add(centerLabel, BorderLayout.CENTER);
    }
    
    public void initCardPanel() {
    	if (mainPanel == null) {
    		mainPanel = new JPanel();
    		cardPanel = new JPanel(new CardLayout());    	
	    	cardPanel.add(CheckoutPanel.getInstance(), MENUS.CHECKOUTS.getText());
			cardPanel.add(AddBook.getInstance(), MENUS.BOOKS.getText());
			cardPanel.add(AddNewMember.getInstance(), MENUS.MEMBERS.getText());    	
	    	mainPanel.add(cardPanel, BorderLayout.CENTER);
	    	landscapePanel.setVisible(false);
	    	LoginWindow.INSTANCE.setVisible(false);
	    	mainPanel.setVisible(true);
	    	getContentPane().add(mainPanel);
    	}
    }
    

	private void createMenus() {
	     // Add the custom menu to the menu bar
		if (SystemController.currentAuth == Auth.BOTH || SystemController.currentAuth == Auth.ADMIN) {
			menuBar.add(createCustomJMenu(MENUS.BOOKS));
			menuBar.add(createCustomJMenu(MENUS.MEMBERS));
		}
		if (SystemController.currentAuth == Auth.BOTH || SystemController.currentAuth == Auth.LIBRARIAN) {
			menuBar.add(createCustomJMenu(MENUS.CHECKOUTS));
		} 
	    menuBar.add(createCustomJMenu(MENUS.LOGOUT));
	    setJMenuBar(menuBar);
    }
	
	private JMenu createCustomJMenu(MENUS menu) {
		JMenu customMenu = new JMenu(menu.getText());
	    customMenu.setIcon(new ImageIcon(menu.getIconName()));
	    customMenu.addMouseListener(new MenuListener());
	    return customMenu;	
	}
	    
       
	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		//JOptionPane.showMessageDialog(this, "Loggedin changed to " + isLoggedIn);
		this.isLoggedIn = isLoggedIn;
		if (this.isLoggedIn) {
			initCardPanel();
			createMenus();		
			CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
			cardLayout.first(cardPanel);
		} else {
			
			mainPanel.setVisible(false);
			initLandscapePanel();
			menuBar.setVisible(false);
		}
	}
	
	private void showCard(String selectedMenuText) {
		CardLayout cl = (CardLayout) (cardPanel.getLayout());
		cl.show(cardPanel, selectedMenuText);
	}


	
    class LoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			LoginWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
			LoginWindow.INSTANCE.setVisible(true);
		}
    	
    }
    
    class MenuListener implements MouseListener {    	
    	 @Override
         public void mouseClicked(MouseEvent e) {
             JMenuItem source = (JMenuItem) e.getSource();
             String menuText = source.getText();
 			if (menuText.equals(MENUS.LOGOUT.getText())) {
 				setLoggedIn(false);
 			} else {
 				showCard(menuText);
 			}
             
         }

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
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
