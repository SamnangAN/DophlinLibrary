package librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrintCheckoutRecordForm extends JFrame {

    private JTextField memberIdField;

    public PrintCheckoutRecordForm() {
        setTitle("Print Checkout Record");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createUI();

        setVisible(true);
    }

    private void createUI() {
        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel memberIdLabel = new JLabel("Member ID:");
        memberIdField = new JTextField();

        JButton printButton = new JButton("Print Checkout Record");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printCheckoutRecord();
            }
        });

        mainPanel.add(memberIdLabel);
        mainPanel.add(memberIdField);
        mainPanel.add(new JLabel()); // Empty space for layout
        mainPanel.add(new JLabel());
        mainPanel.add(new JLabel()); // Empty space for layout
        mainPanel.add(printButton);

        add(mainPanel);
    }

    private void printCheckoutRecord() {
        String memberId = memberIdField.getText();

        // Perform a search for the member based on the provided member ID
        // For simplicity, let's assume the member does not exist
        if (memberDoesNotExist(memberId)) {
            JOptionPane.showMessageDialog(this, "Member with given ID does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Logic to print the checkout record (not implemented in this example)
            JOptionPane.showMessageDialog(this, "Checkout record printed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        }
    }

    private boolean memberDoesNotExist(String memberId) {
        // Implement logic to check if the member exists in your system
        // For simplicity, let's assume the member does not exist
        return true;
    }

    private void clearFields() {
        memberIdField.setText("");
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
