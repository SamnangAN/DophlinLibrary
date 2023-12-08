package librarysystem;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import business.Address;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class AddNewMemberPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -223504918808742781L;
	private static AddNewMemberPanel INSTANCE;
	private JTextField memberIdField, firstNameField, lastNameField, streetField, cityField, stateField, zipField, telephoneField;
	ControllerInterface ci = new SystemController();
	DefaultTableModel modelRight = new DefaultTableModel();
	 public AddNewMemberPanel() {
	        // Set up the main frame properties
		 /*	JPanel titledPanel = new JPanel(new BorderLayout());
		 	JLabel jLabel = new JLabel("Create new member");
	        jLabel.setFont(new Font("Arial", Font.BOLD,22));
	        jLabel.setOpaque(false);
	        titledPanel.add(jLabel,BorderLayout.NORTH);*/
		 	
	        //titledPanel.setBorder(BorderFactory.createTitledBorder("Create new member"));
	        
	        JPanel mainPanel = new JPanel();
	    //    titledPanel.add(mainPanel);
	        mainPanel.setLayout(new GridLayout(9, 2, 10, 5));
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
	       // addButton.addActionListener();
	        buttonPanel.add(addButton);

	        // Set up the main frame layout
	        setLayout(new BorderLayout());
	        add(mainPanel, BorderLayout.NORTH);
	        add(buttonPanel, BorderLayout.CENTER);
	        
	      
            modelRight.addColumn("Member ID");
            modelRight.addColumn("First Name");
            modelRight.addColumn("Last Name");
            modelRight.addColumn("Street");
            modelRight.addColumn("City");
            modelRight.addColumn("State");
            modelRight.addColumn("ZIP");
            modelRight.addColumn("Telephone");
            Object[] rowData = {"Member ID","First Name","Last Name","Street","City","State","ZIP","Telephone"};
            modelRight.addRow(rowData);
            
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
	        mainPanel.add(new JLabel("All members: "));
	        add(memberTable, BorderLayout.SOUTH);
	        
	        
	        
	        addButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Retrieve the data from text fields
	            	
	            	if(validateAndSave()) {
	            		 showMessage("You have been added a member successfully");
	            	}
	                
	    
	            }
	        });
	        
	        memberTable.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                if (e.getClickCount() == 1) { // Single click
	                    int selectedRow = memberTable.getSelectedRow();
	                    if (selectedRow != -1) {
	                        // Get member ID from the selected row
	                        String memberId = memberTable.getValueAt(selectedRow, 0).toString();		
	                        System.out.print(memberId);
	                        // Open the edit member UI (you need to implement this method)
	                        openEditMemberUI(memberId);
	                       
	                    }
	                }
	            }
	        });
	        addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                handlePanelClick();
	            }
	        });
	        
	    }
	 private void handlePanelClick() {
		 rewriteData();
	    }
	 private void rewriteData() {
		 DataAccess access = new DataAccessFacade();
		 modelRight.setRowCount(0);
		 Object[] rowData = {"Member ID","First Name","Last Name","Street","City","State","ZIP","Telephone"};
         modelRight.addRow(rowData);
 		HashMap<String,LibraryMember> v = access.readMemberMap();
 		List<LibraryMember> mems = v.values().stream().collect(Collectors.toList());
         for (LibraryMember member : mems) {
         	System.out.print("id:"+member.getMemberId());
             Object[] rowDatas = {member.getMemberId(), member.getFirstName(), member.getLastName(),
                     member.getAddress().getStreet(), member.getAddress().getCity(), member.getAddress().getState(),member.getAddress().getZip(), member.getTelephone()};
             modelRight.addRow(rowDatas);
         }
	 }
	 
	 private void openEditMemberUI(String memberId) {
	        // Implement logic to open the edit member UI based on the selected member ID
	        // For simplicity, let's assume it's a new JFrame named EditMemberUI
	        new EditMemberInfoUI(memberId).show();
	    }

	  private void showMessage(String message) {
	        SwingUtilities.invokeLater(() -> {
	            JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
	        });
	    }
	 
	  private boolean validateAndSave() {
	        // Check if any field is empty
	        if (memberIdField.getText().isEmpty() || firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
	            return false;
	        }
	        DataAccess dataacess = new DataAccessFacade();
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
 
             Object[] rowData = {memberId,firstName, lastName,
             		street, city,state,zip, telephone};
             modelRight.addRow(rowData);
             
              clearFields();
	        // Perform additional validation logic if needed

	        // For simplicity, let's assume validation always passes in this example
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

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            new AddNewMemberPanel().setVisible(true);
	        });
	    }
	    public static Component getInstance() {
			// TODO Auto-generated method stub
			if (INSTANCE == null) {
				INSTANCE = new AddNewMemberPanel();
			}
			return INSTANCE;
		}
}
