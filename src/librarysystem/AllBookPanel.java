package librarysystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import business.Author;
import business.Book;
import business.ControllerInterface;
import business.SystemController;


public class AllBookPanel extends JPanel {
	private static AllBookPanel INSTANCE;
	private static final long serialVersionUID = 1L;
	private static final String CREATE_NEW_BOOK_LBL = "Add New Book";
    ControllerInterface ci = new SystemController();

	private DefaultTableModel tableModel;
    private JTable table;
    private JTextField isbnTxtField;
    private JTextField titleTxtField;
    Vector<String> columnNames = new Vector<>();
    JPanel backgroundPanelMain, backgroundPanelAddNewMember;

    public static AllBookPanel getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AllBookPanel();
		}
		return INSTANCE;
	}
    
    public AllBookPanel() {
    	init();
	}

	public void init() {
		
		backgroundPanelMain = new JPanel();
		backgroundPanelMain.setLayout(new BorderLayout());
		JPanel formPanel = createBookForm();
		JPanel menuLink = createMenuLink();

		backgroundPanelMain.setLayout(new BorderLayout());
		backgroundPanelMain.add(menuLink, BorderLayout.NORTH);
		backgroundPanelMain.add(formPanel,BorderLayout.CENTER);
		
		backgroundPanelAddNewMember = AddBookPanel.getInstance().init();
		backgroundPanelAddNewMember.setVisible(false);

		add(backgroundPanelMain);
		add(backgroundPanelAddNewMember);
	}

	private JPanel createBookForm() {
		
		loadTableData();

        JPanel filterPanel = createFilterForm();
        JPanel main = new JPanel(new BorderLayout());
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setOpaque(false);
        JLabel jLabel = new JLabel("Book");
        jLabel.setFont(new Font("Arial", Font.BOLD,22));
        jLabel.setOpaque(false);
        labelPanel.add(jLabel,BorderLayout.NORTH);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(labelPanel,BorderLayout.NORTH);
        topPanel.add(filterPanel, BorderLayout.CENTER);
        main.add(topPanel,BorderLayout.NORTH);
        table.setPreferredScrollableViewportSize(new Dimension(1000,500));
        setColumnSize(1,230);
        setColumnSize(2,230);
        setColumnSize(6,150);
        
        JScrollPane jScrollPane = new JScrollPane(table);
        main.add(jScrollPane, BorderLayout.CENTER);
        main.setOpaque(false);
        main.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 20));
        return main;
	}

	private void setColumnSize(int columnIndex, int columnWidth) {
        TableColumn column = table.getColumnModel().getColumn(columnIndex); 
        
        column.setPreferredWidth(columnWidth);
        column.setMinWidth(columnWidth);
        column.setMaxWidth(columnWidth);
	}

	private JPanel createMenuLink() {
		JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton linkButton = new JButton(CREATE_NEW_BOOK_LBL);
        linkButton.setFont(new Font("Arial", Font.PLAIN, 14));

        linkButton.addActionListener(new AddNewBookActionListener());
        leftPanel.add(linkButton);
        leftPanel.setOpaque(false);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        return leftPanel;
	}
	

	private JPanel createFilterForm() {
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setOpaque(false);

        JPanel criteriaPanel = new JPanel(new FlowLayout());
        criteriaPanel.setOpaque(false);
        
        JLabel filterLbl = new JLabel("Filter: ");
        filterLbl.setFont(new Font("Arial", Font.BOLD,14));
        filterLbl.setOpaque(false);
        filterLbl.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        
		JLabel isbnLabel = new JLabel("ISBN");
		isbnTxtField = new JTextField(15);
		isbnTxtField.addActionListener(new AddFilterActionListener());

		criteriaPanel.add(isbnLabel);
		criteriaPanel.add(isbnTxtField);

        JPanel criteriaPanel2 = new JPanel(new FlowLayout());
        criteriaPanel2.setOpaque(false);
		JLabel titleLabel = new JLabel("Title");
		titleTxtField = new JTextField(15);
		titleTxtField.addActionListener(new AddFilterActionListener());

		criteriaPanel2.add(titleLabel);
		criteriaPanel2.add(titleTxtField);

		JPanel butPanel = new JPanel(new FlowLayout());
		butPanel.setOpaque(false);
        JButton applyButton = new JButton("Apply");
        applyButton.setPreferredSize(new Dimension(80, 20));
        JButton clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(80, 20));

        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performFitler();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isbnTxtField.setText("");
                titleTxtField.setText("");
                performFitler();
            }
        });

        butPanel.add(applyButton);
        butPanel.add(clearButton);
        filterPanel.add(criteriaPanel);
        filterPanel.add(criteriaPanel2);
        filterPanel.add(butPanel);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 20, 10));
		return filterPanel;
	}
	
	private void loadTableData() {
		
		Vector<Vector<Object>> bookList = getBookList();
		initColumnNames();
        tableModel = new DefaultTableModel(bookList, columnNames);

        table = new JTable(tableModel);
        ButtonColumn buttonColumn = new ButtonColumn(table, tableModel.getColumnCount() - 1);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
	}

	private void initColumnNames() {
		columnNames.add("ISBN");
		columnNames.add("Title");
		columnNames.add("Authors");
		columnNames.add("MCL");
		columnNames.add("Copies");
		columnNames.add("Available");
		columnNames.add("Action");
	}

	private Vector<Vector<Object>> getBookList() {
		HashMap<String, Book> books =  ci.readBooksMap();
		return covertToTableData(books.values());
	}

	private Vector<Vector<Object>> covertToTableData(Collection<Book> books) {
		Vector<Vector<Object>> data = new Vector<>();
		books.forEach(book -> {
			Vector<Object> bookObject = new Vector<>();
			bookObject.add(book.getIsbn());
			bookObject.add(book.getTitle());
			bookObject.add(book.getAuthors().stream().map(Author::getFullName).collect(Collectors.joining(";")));
			bookObject.add(book.getMaxCheckoutLength());
			bookObject.add(book.getNumCopies());
			bookObject.add(book.isAvailable()?"Yes":"No");
			data.add(bookObject);
		});
		return data;
	}

	void performFitler() {
		String title = titleTxtField.getText();
		String isbn = isbnTxtField.getText();
		
		List<Book> books =  ci.searchBook(isbn,title);
		Vector<Vector<Object>> bookList = covertToTableData(books);
		tableModel.setDataVector(bookList, columnNames);
		ButtonColumn buttonColumn = new ButtonColumn(table, tableModel.getColumnCount() - 1);
	}
	
	class AddFilterActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			performFitler();
		}

    }
	
	class AddNewBookActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			backgroundPanelMain.setVisible(false);
			backgroundPanelAddNewMember.setVisible(true);
			AddBookPanel.getInstance().prepareData();
		}

    }
	
	private class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, ActionListener, TableCellEditor {
        private JButton button;
        private JTable table;
        private int column; 

        public ButtonColumn(JTable table, int column) {
            this.table = table;
            this.column = column;

            button = new JButton("Add Copy");
            button.addActionListener(this);

            table.getColumnModel().getColumn(column).setCellRenderer(this);
            table.getColumnModel().getColumn(column).setCellEditor(this);
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return button;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            String isbn = String.valueOf(table.getValueAt(table.getEditingRow(), 0));
            openPopupForm(isbn);
            fireEditingStopped();
        }
    }
	public void openPopupForm(String isbn) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Add New Copy");

        JTextField textField = new JTextField(20);
        textField.setText("1");
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int enteredText = Integer.parseInt(textField.getText());
                ci.addNewBookCopy(isbn, enteredText);
                performFitler();
                JOptionPane.showMessageDialog(dialog, "New Copies is saved");
                dialog.dispose();
            }
        });
        dialog.setLayout(new FlowLayout());
        dialog.add(new JLabel("Number of new copy:"));
        dialog.add(textField);
        dialog.add(submitButton);
        dialog.setSize(300, 150);
        dialog.setModal(true); 
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        dialog.setVisible(true);
    }
}
