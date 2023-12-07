package librarysystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class AddNewMember extends JFrame{
	private JTextField memberIdField, firstNameField, lastNameField, streetField, cityField, stateField, zipField, telephoneField;
	 public AddNewMember() {
	        // Set up the main frame properties
	        setTitle("Library Management System");
	        setSize(400, 300);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	     // Create and set up the main panel
	        JPanel mainPanel = new JPanel();
	        mainPanel.setLayout(new GridLayout(9, 2, 10, 5));

	        // Add components to the main panel
	        mainPanel.add(new JLabel("Member ID:"));
	        memberIdField = new JTextField();
	        mainPanel.add(memberIdField);

	        mainPanel.add(new JLabel("First Name:"));
	        firstNameField = new JTextField();
	        mainPanel.add(firstNameField);

	        mainPanel.add(new JLabel("Last Name:"));
	        lastNameField = new JTextField();
	        mainPanel.add(lastNameField);

	        mainPanel.add(new JLabel("Street:"));
	        streetField = new JTextField();
	        mainPanel.add(streetField);

	        mainPanel.add(new JLabel("City:"));
	        cityField = new JTextField();
	        mainPanel.add(cityField);

	        mainPanel.add(new JLabel("State:"));
	        stateField = new JTextField();
	        mainPanel.add(stateField);

	        mainPanel.add(new JLabel("ZIP:"));
	        zipField = new JTextField();
	        mainPanel.add(zipField);

	        mainPanel.add(new JLabel("Telephone:"));
	        telephoneField = new JTextField();
	        mainPanel.add(telephoneField);

	        // Create and set up the button panel
	        JPanel buttonPanel = new JPanel();
	        JButton addButton = new JButton("Add Member");
	        addButton.addActionListener(e -> addMember());
	        buttonPanel.add(addButton);

	        // Set up the main frame layout
	        setLayout(new BorderLayout());
	        add(mainPanel, BorderLayout.CENTER);
	        add(buttonPanel, BorderLayout.SOUTH);
	    }

	    private void addMember() {
	        // Retrieve member information from fields
	        String memberId = memberIdField.getText();
	        String firstName = firstNameField.getText();
	        String lastName = lastNameField.getText();
	        String street = streetField.getText();
	        String city = cityField.getText();
	        String state = stateField.getText();
	        String zip = zipField.getText();
	        String telephone = telephoneField.getText();

	        // Process the member information (add to the library, etc.)
	        // For this example, let's print the information to the console
	        System.out.println("Member ID: " + memberId);
	        System.out.println("First Name: " + firstName);
	        System.out.println("Last Name: " + lastName);
	        System.out.println("Street: " + street);
	        System.out.println("City: " + city);
	        System.out.println("State: " + state);
	        System.out.println("ZIP: " + zip);
	        System.out.println("Telephone: " + telephone);

	        // Optionally, you can perform additional actions like adding to a database, etc.
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            new AddNewMember().setVisible(true);
	        });
	    }
}
