package librarysystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import business.ControllerInterface;
import business.SystemController;


public class LibrarySystem extends JFrame implements LibWindow {
	ControllerInterface ci = new SystemController();
	public final static LibrarySystem INSTANCE =new LibrarySystem();
	JPanel mainPanel;
	JMenuBar menuBar;
    JMenu options;
    JMenuItem login, allBookIds, allMemberIds; 
    String pathToImage;
    private boolean isInitialized = false;
    
    private static LibWindow[] allWindows = { 
    	LibrarySystem.INSTANCE,
		LoginWindow.INSTANCE,
		AllMemberIdsWindow.INSTANCE,	
		AllBookIdsWindow.INSTANCE
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
    	JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the background image
                ImageIcon backgroundImage = new ImageIcon(Util.getImagePath() + "library.jpg");
                Image image = backgroundImage.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        panel.setLayout(new BorderLayout());

        // Center label
        JLabel centerLabel = new JLabel("Dolphin Library");
        centerLabel.setForeground(Color.WHITE); // Set text color
//        centerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 45));
        panel.add(centerLabel, BorderLayout.CENTER);

        // Login button at the top right
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginListener());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false); // Make the button panel transparent
        buttonPanel.add(loginButton);
        panel.add(buttonPanel, BorderLayout.NORTH);

        // Set an empty border to center the content
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        getContentPane().add(panel);
        setLocationRelativeTo(null); // Center the frame
        
//        
//        
//    	formatContentPane();
//    	
//    	insertSplashImage();
//    	insertLibraryName();
//		
//		createMenus();
//		//pack();
//		setSize(1200,800);
//		isInitialized = true;
    }
    
    private void setPathToImage() {
    	String currDirectory = System.getProperty("user.dir");
    	pathToImage = currDirectory+"/src/images/library2.jpg";
    }
    
//    private void insertLibraryName() {
//    	JLabel label = new JLabel("Dolphin Library");
//        label.setHorizontalAlignment(SwingConstants.CENTER);
//
//        mainPanel.add(label, BorderLayout.CENTER);
//		
//	}
//
//	private void formatContentPane() {
//		mainPanel = new JPanel();
//		mainPanel.setLayout(new GridLayout(1,1));
//		getContentPane().add(mainPanel);	
//	}
//    
    
//    
//    private void insertSplashImage() {
//        ImageIcon image = new ImageIcon(pathToImage);
//        JLabel imageLabel = new JLabel(image);
//        imageLabel.setSize(mainPanel.getWidth(), mainPanel.getHeight());
//		mainPanel.add(imageLabel);	
//    }
//    private void createMenus() {
//    	menuBar = new JMenuBar();
//		menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
//		addMenuItems();
//		setJMenuBar(menuBar);		
//    }
//    
//    private void addMenuItems() {
//       options = new JMenu("Options");  
// 	   menuBar.add(options);
// 	   login = new JMenuItem("Login");
// 	   login.addActionListener(new LoginListener());
// 	   allBookIds = new JMenuItem("All Book Ids");
// 	   allBookIds.addActionListener(new AllBookIdsListener());
// 	   allMemberIds = new JMenuItem("All Member Ids");
// 	   allMemberIds.addActionListener(new AllMemberIdsListener());
// 	   options.add(login);
// 	   options.add(allBookIds);
// 	   options.add(allMemberIds);
//    }
//    
    class LoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			LoginWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
			LoginWindow.INSTANCE.setVisible(true);
			
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
