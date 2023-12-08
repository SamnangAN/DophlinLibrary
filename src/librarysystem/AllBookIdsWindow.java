package librarysystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextArea;
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
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import business.Author;
import business.Book;
import business.BookCopy;
import business.ControllerInterface;
import business.SystemController;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;


public class AllBookIdsWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = 1L;
	public static final AllBookIdsWindow INSTANCE = new AllBookIdsWindow();
	private static final String CREATE_NEW_BOOK_LBL = "> Add New Book";
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;

	private DefaultTableModel tableModel;
    private JTable table;
    private JTextField isbnTxtField;
    private JTextField titleTxtField;
    Vector<String> columnNames = new Vector<>();

    DataAccessFacade dataAccess = new DataAccessFacade();

	//Singleton class
	private AllBookIdsWindow() {}

	@Override
	public void init() {
		setSize(1200, 800);

		ImageBackgroundPanel backgroundPanel = new ImageBackgroundPanel(Util.getImagePath() + "login-background.png");
		backgroundPanel.setLayout(new BorderLayout());
		JPanel formPanel = createBookForm();
		JPanel menuLink = createMenuLink();

		backgroundPanel.setLayout(new BorderLayout());
		backgroundPanel.add(menuLink, BorderLayout.WEST);
		backgroundPanel.add(formPanel,BorderLayout.EAST);

		getContentPane().add(backgroundPanel);
		setLocationRelativeTo(null);
	}

	private JPanel createBookForm() {
		
		loadTableForm();

        JPanel filterPanel = createFilterForm();
        JPanel main = new JPanel(new BorderLayout());
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setOpaque(false);
        JLabel jLabel = new JLabel("Book");
        jLabel.setFont(new Font("Arial", Font.BOLD,22));
        jLabel.setOpaque(false);
        
        JLabel filterLbl = new JLabel("Filter");
        filterLbl.setFont(new Font("Arial", Font.BOLD,14));
        filterLbl.setOpaque(false);
        filterLbl.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        labelPanel.add(jLabel,BorderLayout.NORTH);
        labelPanel.add(filterLbl,BorderLayout.CENTER);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(labelPanel,BorderLayout.NORTH);
        topPanel.add(filterPanel, BorderLayout.CENTER);
        main.add(topPanel,BorderLayout.NORTH);
        main.add(new JScrollPane(table), BorderLayout.CENTER);
        main.setOpaque(false);
        main.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 20));
        return main;
	}

	private JPanel createMenuLink() {
		JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton linkButton = new JButton(CREATE_NEW_BOOK_LBL);
        linkButton.setFont(new Font("Arial", Font.PLAIN, 14));
        linkButton.setForeground(Color.WHITE);
        linkButton.setBorderPainted(false);
        linkButton.setContentAreaFilled(false);
        linkButton.setFocusPainted(false);
        addHoverUnderlineText(linkButton);
        linkButton.addActionListener(new AddNewBookActionListener());
        leftPanel.add(linkButton);
        leftPanel.setOpaque(false);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        return leftPanel;
	}
	
	private void addHoverUnderlineText(JButton linkButton) {
		linkButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	linkButton.setText("<html><u>" + CREATE_NEW_BOOK_LBL + "</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	linkButton.setText("<html><u>" + CREATE_NEW_BOOK_LBL + "</u></html>");
            }
        });
		
	}

	private JPanel createFilterForm() {
        JPanel filterPanel = new JPanel(new GridLayout(1,6));
        filterPanel.setOpaque(false);

        JPanel criteriaPanel = new JPanel(new FlowLayout());
        criteriaPanel.setOpaque(false);
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
	
	private void loadTableForm() {
		
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
		columnNames.add("Add Copy");
	}

	private Vector<Vector<Object>> getBookList() {
		HashMap<String, Book> books =  dataAccess.readBooksMap();
		return covertToTableData(books.values());
	}

	private Vector<Vector<Object>> covertToTableData(Collection<Book> books) {
		Vector<Vector<Object>> data = new Vector<>();
		books.forEach(book -> {
			Vector<Object> bookObject = new Vector<>();
			bookObject.add(book.getIsbn());
			bookObject.add(book.getTitle());
			bookObject.add(book.getAuthors().stream().map(Author::getFullName).collect(Collectors.joining()));
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

		DataAccessFacade dataAccess = new DataAccessFacade();
		List<Book> books =  dataAccess.searchBook(isbn,title);
		Vector<Vector<Object>> bookList = covertToTableData(books);
		tableModel.setDataVector(bookList, columnNames);
		ButtonColumn buttonColumn = new ButtonColumn(table, tableModel.getColumnCount() - 1);
	}

	private static class ImageBackgroundPanel extends JPanel {
        private Image backgroundImage;

        public ImageBackgroundPanel(String imagePath) {
            this.backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
	
	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;

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
			LibrarySystem.hideAllWindows();
			AddBook.INSTANCE.init();
			Util.centerFrameOnDesktop(AddBook.INSTANCE);
			AddBook.INSTANCE.setVisible(true);
		}

    }
	
	private class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, ActionListener, TableCellEditor {
        private JButton button;
        private JTable table;
        private int column;
        private JTextField textField;

        public ButtonColumn(JTable table, int column) {
            this.table = table;
            this.column = column;

            button = new JButton("Click");
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
        
        private void openPopupForm(String isbn) {
            JDialog dialog = new JDialog();
            dialog.setTitle("Add New Copy");

            textField = new JTextField(20);
            textField.setText("1");
            JButton submitButton = new JButton("Submit");
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String enteredText = textField.getText();
                    dataAccess.addNewCopy(isbn, enteredText);
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
}
