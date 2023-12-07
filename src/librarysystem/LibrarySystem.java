package librarysystem;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import business.ControllerInterface;
import business.SystemController;


public class LibrarySystem extends JFrame implements LibWindow {
	
	public final static LibrarySystem INSTANCE =new LibrarySystem();
	private JPanel mainPanel, cardPanel;
	private JMenuBar menuBar = new JMenuBar();
    private String pathToImage;
    private boolean isInitialized = false;
    
    private ControllerInterface ci = new SystemController();
    
    private static LibWindow[] allWindows = { 
    	LibrarySystem.INSTANCE,
		LoginWindow.INSTANCE,
		AllMemberIdsWindow.INSTANCE,	
		AllBookIdsWindow.INSTANCE,
		MainMenu.INSTANCE,
		AddBook.INSTANCE
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

    	setSize(900, 600);
    	JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon(Util.getImagePath() + "library.jpg");
                Image image = backgroundImage.getImage();
                
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(new BorderLayout());
        JLabel centerLabel = new JLabel("Dolphin Library");
        centerLabel.setForeground(Color.WHITE); 
        centerLabel.setFont(new Font("Arial", Font.BOLD, 45));
        panel.add(centerLabel, BorderLayout.CENTER);
        
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginListener());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        panel.add(buttonPanel, BorderLayout.NORTH);

        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        getContentPane().add(panel);
        setLocationRelativeTo(null);
    }
    
    private void setPathToImage() {
    	String currDirectory = System.getProperty("user.dir");
    	pathToImage = currDirectory+"/src/images/";
    }
    
    private void insertLibraryName() {
        JLabel centerLabel = new JLabel("Dolphin Library");
        centerLabel.setForeground(Color.WHITE); // Set text color
        centerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 45));
        mainPanel.add(centerLabel, BorderLayout.CENTER);
    }
    

	private void createMenus() {
	     // Add the custom menu to the menu bar
	     menuBar.add(createCustomJMenu(MENUS.HOME, new MenuListener()));
	     menuBar.add(createCustomJMenu(MENUS.BOOKS, new MenuListener()));
	     menuBar.add(createCustomJMenu(MENUS.CHECKOUTS, new MenuListener()));
	     menuBar.add(createCustomJMenu(MENUS.MEMBERS, new MenuListener()));
	     setJMenuBar(menuBar);
    }
	
	private JMenu createCustomJMenu(MENUS menu, ActionListener actionListener) {
		JMenu customMenu = new JMenu(menu.getText());
	    customMenu.setIcon(new ImageIcon(menu.getIconName()));
	    customMenu.addActionListener(actionListener);
	    return customMenu;	
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
    
    class MenuListener implements ActionListener {
    	@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			LoginWindow.INSTANCE.setVisible(false);
	//		Util.centerFrameOnDesktop(.INSTANCE);
	//		LoginWindow.INSTANCE.setVisible(true);
			
		}
    }
//    class AllBookIdsListener implements ActionListener {
//
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			LibrarySystem.hideAllWindows();
//			AllBookIdsWindow.INSTANCE.init();
//			
//			List<String> ids = ci.allBookIds();
//			Collections.sort(ids);
//			StringBuilder sb = new StringBuilder();
//			for(String s: ids) {
//				sb.append(s + "\n");
//			}
//			System.out.println(sb.toString());
//			AllBookIdsWindow.INSTANCE.setData(sb.toString());
//			AllBookIdsWindow.INSTANCE.pack();
//			//AllBookIdsWindow.INSTANCE.setSize(660,500);
//			Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
//			AllBookIdsWindow.INSTANCE.setVisible(true);
//			
//		}
//    	
//    }
//    
//    class AllMemberIdsListener implements ActionListener {
//
//    	@Override
//		public void actionPerformed(ActionEvent e) {
//			LibrarySystem.hideAllWindows();
//			AllMemberIdsWindow.INSTANCE.init();
//			AllMemberIdsWindow.INSTANCE.pack();
//			AllMemberIdsWindow.INSTANCE.setVisible(true);
//			
//			
//			LibrarySystem.hideAllWindows();
//			AllBookIdsWindow.INSTANCE.init();
//			
//			List<String> ids = ci.allMemberIds();
//			Collections.sort(ids);
//			StringBuilder sb = new StringBuilder();
//			for(String s: ids) {
//				sb.append(s + "\n");
//			}
//			System.out.println(sb.toString());
//			AllMemberIdsWindow.INSTANCE.setData(sb.toString());
//			AllMemberIdsWindow.INSTANCE.pack();
//			//AllMemberIdsWindow.INSTANCE.setSize(660,500);
//			Util.centerFrameOnDesktop(AllMemberIdsWindow.INSTANCE);
//			AllMemberIdsWindow.INSTANCE.setVisible(true);
//			
//			
//		}
//    	
//    }


	@Override
	public boolean isInitialized() {
		return isInitialized;
	}


	@Override
	public void isInitialized(boolean val) {
		isInitialized =val;
		
	}
    
}
