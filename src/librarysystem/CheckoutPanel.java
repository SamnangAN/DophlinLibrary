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

public class CheckoutPanel extends JPanel {
	private static CheckoutPanel INSTANCE;
	private static final long serialVersionUID = 1L;


	private JTextField memberIdField;
	private JTextField isbnField;

	public CheckoutPanel() {
		
		

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

	}

	public static CheckoutPanel getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CheckoutPanel();
		}
		return INSTANCE;
	}


	

}
