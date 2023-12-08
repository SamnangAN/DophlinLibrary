package librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import business.Book;
import business.CheckoutRecord;
import business.CheckoutRecordEntry;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrintCheckoutRecordForm extends JPanel {

    private JTextField memberIdField;
    private JTable checkoutTable;
    DefaultTableModel modelRight = new DefaultTableModel();
    static PrintCheckoutRecordForm INSTANCE;

    public PrintCheckoutRecordForm() {
        setSize(600, 400);

        createUI();

        setVisible(true);
    }

    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel memberIdLabel = new JLabel("Member ID:");
        memberIdField = new JTextField(10);
        JButton printButton = new JButton("Print Records");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printCheckoutRecords();
            }
        });
        inputPanel.add(memberIdLabel);
        inputPanel.add(memberIdField);
        inputPanel.add(printButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

       
        modelRight.addColumn("IBN");
        modelRight.addColumn("CheckoutDate");
        modelRight.addColumn("DueDate");
      //  DataAccess access = new DataAccessFacade();
        ControllerInterface ci = new SystemController();
        List<CheckoutRecord> recs = ci.getAllCheckoutRecord();
              for (CheckoutRecord rc : recs) {
            	  List<CheckoutRecordEntry> entr = rc.getCheckoutRecordEntries();
            	  for(CheckoutRecordEntry ent:entr) {
            		  Object[] rowDatas = {ent.getBookCopy().getBook().getIsbn(),ent.getCheckoutDate(),ent.getDueDate()};
                      modelRight.addRow(rowDatas);
            	  }
                  
              }
        JTable bookTable = new JTable(modelRight);
        JScrollPane tableScrollPane = new JScrollPane(bookTable);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        add(mainPanel);

    
    }

    private void printCheckoutRecords() {
        String memberId = memberIdField.getText();
        DataAccess access = new DataAccessFacade();
        // Perform a search for the member based on the provided member ID
        // For simplicity, let's assume the member does not exist
        if (access.searchMember(memberId)!=null) {
            JOptionPane.showMessageDialog(this, "Member with given ID does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Display checkout records associated with the member
            updateCheckoutTable(memberId);
        }
    }
    

    private boolean memberDoesNotExist(String memberId) {
        // Implement logic to check if the member exists in your system
        // For simplicity, let's assume the member does not exist
        return true;
    }

    private void updateCheckoutTable(String memberId) {
        // Implement logic to update the table with checkout records associated with the member
        // For simplicity, let's assume the table is static, and the logic is not implemented
    	JOptionPane.showMessageDialog(this, "Member found!", "Success", JOptionPane.ERROR_MESSAGE);
       // DefaultTableModel model = (DefaultTableModel) checkoutTable.getModel();
    	modelRight.setRowCount(0); // Clear existing rows
    	
    	 DataAccess access = new DataAccessFacade();
    	 CheckoutRecord rc = access.getCheckoutRecord(memberId);
   		List<CheckoutRecordEntry> records = rc.getCheckoutRecordEntries();
           for (CheckoutRecordEntry rec : records) {
               Object[] rowDatas = {rec.getBookCopy().getBook().getIsbn(),rec.getCheckoutDate(),rec.getDueDate()};
               modelRight.addRow(rowDatas);
           }
        
    }
    public static Component getInstance() {
		// TODO Auto-generated method stub
		if (INSTANCE == null) {
			INSTANCE = new PrintCheckoutRecordForm();
		}
		return INSTANCE;
	}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PrintCheckoutRecordForm();
            }
        });
    }
}

