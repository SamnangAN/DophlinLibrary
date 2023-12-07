package librarysystem;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class LibraryManagementUI extends JFrame {
    public LibraryManagementUI() {
        setTitle("Library Management System");
        setSize(2000, 1000);
       
        setLayout(new BorderLayout());
        add(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = new ImageIcon("src/images/bg.jpg").getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }, BorderLayout.CENTER);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(70, 50, 20, 50)); // top, left, bottom, right

        // Create buttons on the left side
        JPanel buttonPanel = new JPanel();
        
        buttonPanel.setLayout(new GridLayout(7, 1, 10, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 100));

        JButton checkoutButton = new JButton("Checkout Book");
        checkoutButton.setPreferredSize(new Dimension(300,30));
        JButton overdueButton = new JButton("Overdue Date");
        JButton printCheckoutButton = new JButton("Print Member Checkout");
        JButton addMemberButton = new JButton("Add Member");
        JButton addBookButton = new JButton("Add Book");
        JButton addBookCopyButton = new JButton("Add Book Copy");
        JButton editMemberButton = new JButton("Edit Member");

        buttonPanel.add(checkoutButton);
        buttonPanel.add(overdueButton);
        buttonPanel.add(printCheckoutButton);
        buttonPanel.add(addMemberButton);
        buttonPanel.add(addBookButton);
        buttonPanel.add(addBookCopyButton);
        buttonPanel.add(editMemberButton);

        // Create form on the right side with a background image
        JPanel formPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = new ImageIcon("src/images/bg.jpg").getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), (ImageObserver) this);
            }
        };
        formPanel.setLayout(new GridLayout(8, 2, 10, 5));  

        formPanel.add(new JLabel("Member ID:"));
        JTextField memberIdField = new JTextField();
        formPanel.add(memberIdField);

        formPanel.add(new JLabel("First Name:"));
        JTextField firstNameField = new JTextField();
        formPanel.add(firstNameField);

        formPanel.add(new JLabel("Last Name:"));
        JTextField lastNameField = new JTextField();
        formPanel.add(lastNameField);

        formPanel.add(new JLabel("Street:"));
        JTextField streetField = new JTextField();
        formPanel.add(streetField);

        formPanel.add(new JLabel("City:"));
        JTextField cityField = new JTextField();
        formPanel.add(cityField);

        formPanel.add(new JLabel("State:"));
        JTextField stateField = new JTextField();
        formPanel.add(stateField);

        formPanel.add(new JLabel("ZIP:"));
        JTextField zipField = new JTextField();
        formPanel.add(zipField);
        
        formPanel.add(new JLabel("Telephone:"));
        JTextField telephoneField = new JTextField();
        formPanel.add(telephoneField);

        // Add components to the main frame
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.WEST);
        add(formPanel, BorderLayout.CENTER);
       

        addMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the data from text fields
                String memberId = memberIdField.getText();
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String street = streetField.getText();
                String city = cityField.getText();
                String state = stateField.getText();
                String zip = zipField.getText();
                String telephone = telephoneField.getText();

                // Perform the action (e.g., add new member)
               // addMember(memberId, firstName, lastName, street, city, state, zip, telephone);
            }
        });
      //  formPanel.add(addMemberButton);

        formPanel.revalidate();
        formPanel.repaint();
    }


    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LibraryManagementUI().setVisible(true);
        });
    }
}


