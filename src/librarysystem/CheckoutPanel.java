package librarysystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import business.BookCopy;
import business.CheckoutRecordEntry;
import business.ControllerInterface;
import business.SystemController;
import javax.swing.SwingConstants;


public class CheckoutPanel extends JPanel {
	private static CheckoutPanel INSTANCE;
	private static final long serialVersionUID = 1L;

	private JPanel topPanel, centerPanel;
	private JPanel newCheckoutPanel, printCeckoutPanel, checkBookOverDue; 
	private JTextField memberIdField;
	private JTextField isbnField;
	
	private JTextField isbnOverDueField;
	private JTextField printReportMemberIdField;
	
	private JTable resultTable;	
	private final String[] headerNames = {"ISBN", "Title", "Copy number", "Library Member", "Checkout Date", "Due Date"};
	
    private DefaultTableModel allCheckoutDataSource = new DefaultTableModel(0, 0);
    private DefaultTableModel overdueCheckDataSource = new DefaultTableModel(0, 0);
    
    
    private ControllerInterface co = new SystemController();

    private JLabel tableDescription;
    
	public CheckoutPanel() {
		super(new BorderLayout());
		initTopPanel();
		initResultPanel();
	}
	
	private void initTopPanel() {
		// Create the JPanel
		topPanel = new JPanel(new BorderLayout());
		
		newCheckoutPanel = getTitledPanel("New checkout");		
		JPanel printCeckoutPanel = getTitledPanel("Print checkout for member");
		JPanel checkBookOverDue = new JPanel();
		checkBookOverDue.setBorder(BorderFactory.createTitledBorder("Check book overdue"));
		
		topPanel.add(newCheckoutPanel, BorderLayout.EAST);
		topPanel.add(printCeckoutPanel, BorderLayout.CENTER);
		topPanel.add(checkBookOverDue, BorderLayout.WEST);
		
		topPanel.setLayout(new GridLayout(0, 1, 0, 0));
		addCheckoutPanel();
		addOverDuePanel();
	}
	
	private JPanel getTitledPanel(String title) {
		JPanel newPanel = new JPanel();
		newPanel.setBorder(BorderFactory.createTitledBorder("New checkout"));	
		return newPanel;
	}
	
	
	
	private void addCheckoutPanel() {
		JPanel topInnerPanel = new JPanel();
		topInnerPanel.setBorder(new EmptyBorder(10, 30, 10, 30));

		// Add components to the panel
		JLabel memberIdLabel = new JLabel("Member ID: ");	
		memberIdField = new JTextField();
		topInnerPanel.setLayout(new GridLayout(0, 5, 0, 0));
		topInnerPanel.add(memberIdLabel);
		topInnerPanel.add(memberIdField);

		JLabel isbnLabel = new JLabel("ISBN: ");	
		isbnLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		isbnField = new JTextField();
		topInnerPanel.add(isbnLabel);
		topInnerPanel.add(isbnField);
		topPanel.add(topInnerPanel);
		
		JButton checkoutButton = new JButton("Checkout");
		topInnerPanel.add(checkoutButton);
		checkoutButton.setVerticalAlignment(SwingConstants.BOTTOM);
		checkoutButton.addActionListener(e-> {
			// Handle the checkout logic here
			String memberId = memberIdField.getText();
			String isbn = isbnField.getText();
			
			try {
				System.out.println("Checking out for isbn: " + isbn + ", memberId: " + memberId);
				tableDescription.setText("All checkout records: ");
				CheckoutRecordEntry entry = co.checkoutBook(memberId, isbn);
				JOptionPane.showMessageDialog(this, "Checkout successfull!", "Success", JOptionPane.INFORMATION_MESSAGE);
				resultTable.setModel(allCheckoutDataSource);
				addEntryToDataSource(allCheckoutDataSource, entry.getBookCopy(), entry);
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}			
		});

		// Add the panel to the JFrame
		add(topPanel, BorderLayout.NORTH);
	}
	
	private void addOverDuePanel()
	{
		JPanel overDuePanel = new JPanel();
		overDuePanel.setBorder(new EmptyBorder(10, 30, 10, 30));
		overDuePanel.setLayout(new GridLayout(0, 5, 0, 0));

		JLabel isbnLabel = new JLabel("ISBN: ");	
		isbnLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		isbnOverDueField = new JTextField();
		overDuePanel.add(isbnLabel);
		overDuePanel.add(isbnOverDueField);
		topPanel.add(overDuePanel);
		
		JButton checkoutButton = new JButton("Check overdues");
		overDuePanel.add(checkoutButton);
		checkoutButton.setVerticalAlignment(SwingConstants.BOTTOM);
		checkoutButton.addActionListener(e-> {
			try {
				System.out.println("Checking overdue for isbn: " + isbnOverDueField.getText());
				HashMap<BookCopy, CheckoutRecordEntry> entries = co.checkBookOverdue(isbnOverDueField.getText());
				tableDescription.setText("Overdue results: ");				
				overdueCheckDataSource.setRowCount(0);		
				for (BookCopy bookCopy: entries.keySet()) {
					addEntryToDataSource(overdueCheckDataSource, bookCopy, entries.get(bookCopy));
				}	
				resultTable.setModel(overdueCheckDataSource);
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		});

		// Add the panel to the JFrame
		add(topPanel, BorderLayout.NORTH);

	}
	
	private void initResultPanel() {
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));	
			
    	tableDescription = new JLabel("All checkouts");    	
		allCheckoutDataSource.setColumnIdentifiers(headerNames);
		overdueCheckDataSource.setColumnIdentifiers(headerNames);
		
		resultTable = new JTable();
		resultTable.setModel(allCheckoutDataSource);
		resultTable.setDefaultRenderer(Object.class, new CustomCellRenderer());
	  
        initData();
        
        JScrollPane scrollPane = new JScrollPane(resultTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);        
    	centerPanel.add(tableDescription, BorderLayout.NORTH);
    	this.add(centerPanel, BorderLayout.CENTER);    	
    	
	}
	
	//----------------------------------- ADD INIT DATA METHODS -----------------------------------------------------
	

	
	private void initData() {
		List<CheckoutRecordEntry> entries = co.getAllCheckoutEntries();
		for (CheckoutRecordEntry entry: entries) {
			addEntryToDataSource(allCheckoutDataSource, entry.getBookCopy(), entry);
		}
	}
	
	
	private void addEntryToDataSource(DefaultTableModel dataSource, BookCopy bookCopy, CheckoutRecordEntry entry) {
		if (entry != null && entry.getCheckoutRecord() != null) {
			dataSource.addRow(new Object[] {bookCopy.getBook().getIsbn(), 
					bookCopy.getBook().getTitle(),
					bookCopy.getCopyNum(),
					entry.getCheckoutRecord() != null? entry.getCheckoutRecord().getLibraryMember().getFullNameWithId(): null,
					entry.getCheckoutDate(),
					entry.getDueDate()});
		} else {
			dataSource.addRow(new Object[] {bookCopy.getBook().getIsbn(), 
					bookCopy.getBook().getTitle(),
					bookCopy.getCopyNum(),
					null, null, null});
		}
		
	}
	

	public static CheckoutPanel getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CheckoutPanel();
		}
		return INSTANCE;
	}
	
	private class CustomCellRenderer extends DefaultTableCellRenderer {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1542764343693051996L;

		@Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

            // Column index for the due date in your table model
            int dueDateColumnIndex = 5; // Adjust this based on your table structure

            // Get the due date from the table model
            Object dueDateValue = table.getValueAt(row, dueDateColumnIndex);

            // Check if the due date is after today
            boolean isDueOver = isDueOver(dueDateValue);

            // Set the background color for the entire row based on the condition
            if (isDueOver) {
            	  setBackground(Color.RED);
                  setForeground(Color.WHITE); // Set text color for better visibility
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
            }

            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }

        private boolean isDueOver(Object dateValue) {
        	if (dateValue != null) {        		
        		return ((LocalDate)dateValue).isBefore(LocalDate.now());
        	}
            return false;
        }
    }

}
