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
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrintCheckoutRecordForm extends JFrame {

    private JTextField memberIdField;
    private JTable checkoutTable;
    DefaultTableModel modelRight = new DefaultTableModel();

    public PrintCheckoutRecordForm() {
        setTitle("Print Checkout Records");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        modelRight.addColumn("Title");
        modelRight.addColumn("Author");
        modelRight.addColumn("Copies");
        DataAccess access = new DataAccessFacade();
      		HashMap<String, Book> v = access.readBooksMap();
      		List<Book> books = v.values().stream().collect(Collectors.toList());
              for (Book book : books) {
                  Object[] rowDatas = {book.getIsbn(),book.getTitle(),book.getAuthors(),book.getNumCopies()};
                  modelRight.addRow(rowDatas);
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

        // Add example data (replace with actual data retrieval logic)
    	modelRight.addRow(new Object[]{"1001", "Book1", "2023-01-01"});
    	modelRight.addRow(new Object[]{"1001", "Book2", "2023-02-01"});
    	modelRight.addRow(new Object[]{"1002", "Book3", "2023-03-01"});
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

