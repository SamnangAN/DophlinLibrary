package librarysystem;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CheckoutBookWindow extends JFrame implements LibWindow {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;

	private JTextField memberIdField;
	private JTextField isbnField;

	public CheckoutBookWindow() {
		
		// Set up the JFrame
		setTitle("Checkout Form");
		setSize(400, 200);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(mainPanel);
		
		

		// Create the JPanel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));

		// Add components to the panel
		panel.add(new JLabel("Member ID:"));
		memberIdField = new JTextField();
		panel.add(memberIdField);

		panel.add(new JLabel("ISBN:"));
		isbnField = new JTextField();
		panel.add(isbnField);

		JButton checkoutButton = new JButton("Checkout");
		checkoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Handle the checkout logic here
				String memberId = memberIdField.getText();
				String isbn = isbnField.getText();
				// Implement book checkout logic
				// ...

				// For now, just print the values
				System.out.println("Member ID: " + memberId);
				System.out.println("ISBN: " + isbn);
			}
		});
		panel.add(checkoutButton);

		// Add the panel to the JFrame
		add(panel);

		// Display the JFrame
		setVisible(true);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CheckoutBookWindow frame = new CheckoutBookWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void isInitialized(boolean val) {
		// TODO Auto-generated method stub

	}

}
