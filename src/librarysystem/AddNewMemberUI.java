package librarysystem;

import javax.imageio.ImageIO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import business.Address;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class AddNewMemberUI extends JPanel {
	private static AddNewMemberUI INSTANCE;
	
	ControllerInterface ci = new SystemController();
	
	DataAccessFacade ac = new DataAccessFacade();
//	ArrayList<LibraryMember> m = new ArrayList<LibraryMember>();
    public AddNewMemberUI() {   	
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

        // Create form on the right side with a background image
        JPanel formPanel = new JPanel() {
        };
        formPanel.setLayout(new GridLayout(9, 3, 20, 5));  

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
        
        JPanel bt = new JPanel();
        JButton addButton = new JButton("Add Members");
        addButton.addActionListener(e -> addMember());
        bt.add(addButton);
        formPanel.add(addButton);
        
        DefaultTableModel modelRight = new DefaultTableModel();
        modelRight.addColumn("Member ID");
        modelRight.addColumn("First Name");
        modelRight.addColumn("Last Name");
        modelRight.addColumn("Street");
        modelRight.addColumn("City");
        modelRight.addColumn("State");
        modelRight.addColumn("ZIP");
        modelRight.addColumn("Telephone");
        
       
        DataAccess access = new DataAccessFacade();
		HashMap<String,LibraryMember> v = access.readMemberMap();
		List<LibraryMember> mems = v.values().stream().collect(Collectors.toList());
        for (LibraryMember member : mems) {
        	System.out.print("id:"+member.getMemberId());
            Object[] rowDatas = {member.getMemberId(), member.getFirstName(), member.getLastName(),
                    member.getAddress().getStreet(), member.getAddress().getCity(), member.getAddress().getState(),member.getAddress().getZip(), member.getTelephone()};
            modelRight.addRow(rowDatas);
        }
        JTable memberTable = new JTable(modelRight);
        JScrollPane tableScrollPane = new JScrollPane(memberTable);
        formPanel.add(memberTable);
        
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 100));


        // Add components to the main frame
        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
       
        
        addButton.addActionListener(new ActionListener() {
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
                Address a = new Address(street,city,state,zip);

                LibraryMember mems = new LibraryMember(memberId,firstName,lastName,telephone,a);
                ci.saveNewMember(mems);
               

                showMessage("You have been added a member successfully");
                Object[] rowData = {memberId,firstName, lastName,
                		street, city,state,zip, telephone};
                modelRight.addRow(rowData);
                
    
            }
        });
        
      //  formPanel.add(addMemberButton);

        formPanel.revalidate();
        formPanel.repaint();
       
    }
    private void showMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
        });
    }
    

    private Object addMember() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AddNewMemberUI().setVisible(true);
        });
    }
	public static Component getInstance() {
		// TODO Auto-generated method stub
		if (INSTANCE == null) {
			INSTANCE = new AddNewMemberUI();
		}
		return INSTANCE;
	}
}


