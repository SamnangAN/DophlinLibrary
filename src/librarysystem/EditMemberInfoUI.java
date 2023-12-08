package librarysystem;


import javax.swing.*;

import business.Address;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditMemberInfoUI extends JPanel {

    private JTextField memberIdField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField streetField;
    private JTextField cityField;
    private JTextField stateField;
    private JTextField zipField;
    private JTextField telephoneField;
    static EditMemberInfoUI INSTANCE;
    static String memberId;
    public EditMemberInfoUI(String memberId) {
    	this.memberId = memberId;
        setSize(1000, 600);
        setVisible(true);
        
        
        JPanel formPanel = new JPanel() {
        };
        formPanel.setLayout(new GridLayout(9, 2, 10, 5));  

        formPanel.add(new JLabel("Member ID:"));
         memberIdField = new JTextField();
        formPanel.add(memberIdField);

        formPanel.add(new JLabel("First Name:"));
         firstNameField = new JTextField();
        formPanel.add(firstNameField);

        formPanel.add(new JLabel("Last Name:"));
         lastNameField = new JTextField();
        formPanel.add(lastNameField);

        formPanel.add(new JLabel("Street:"));
         streetField = new JTextField();
        formPanel.add(streetField);

        formPanel.add(new JLabel("City:"));
         cityField = new JTextField();
        formPanel.add(cityField);

        formPanel.add(new JLabel("State:"));
         stateField = new JTextField();
        formPanel.add(stateField);

        formPanel.add(new JLabel("ZIP:"));
         zipField = new JTextField();
        formPanel.add(zipField);
        
        formPanel.add(new JLabel("Telephone:"));
         telephoneField = new JTextField();
        formPanel.add(telephoneField);
        
        formPanel.add(new JLabel());

        JPanel bt = new JPanel();
        JButton update = new JButton("Edit Members");
        bt.add(update);
        formPanel.add(update);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 100));
        add(formPanel);
        
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateAndSave()) {             
                    clearFields();
                }
            }
        });

    }

    


    private boolean validateAndSave() {
        // Check if any field is empty
        if (memberIdField.getText().isEmpty() || firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        DataAccess dataacess = new DataAccessFacade();
        if(dataacess.searchMember((memberIdField.getText()))!=null) {
        	 LibraryMember member = dataacess.searchMember(this.memberIdField.getText());
             member.setFirstName(this.firstNameField.getText());
             member.setLastName(this.lastNameField.getText());
             member.setTelephone(this.telephoneField.getText());

             Address address = new Address(
                     this.streetField.getText(),
                     this.cityField.getText(),
                     this.stateField.getText(),
                     this.zipField.getText()
             );

             member.setAddress(address);
             dataacess.saveNewMember(member);
             JOptionPane.showMessageDialog(this, "Member info updated", "Successful", JOptionPane.INFORMATION_MESSAGE);
        }else {
        	 JOptionPane.showMessageDialog(this, "Member with this ID cannot be found", "Failed", JOptionPane.INFORMATION_MESSAGE);
        }
        return true;
    }

    private void clearFields() {
        memberIdField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        streetField.setText("");
        cityField.setText("");
        stateField.setText("");
        zipField.setText("");
        telephoneField.setText("");
    }

    public static Component getInstance() {
		// TODO Auto-generated method stub
		if (INSTANCE == null) {
			INSTANCE = new EditMemberInfoUI(memberId);
		}
		return INSTANCE;
	}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EditMemberInfoUI(memberId).setVisible(true);;
            }
        });
    }
}


