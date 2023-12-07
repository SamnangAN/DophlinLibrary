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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import business.ControllerInterface;
import business.SystemController;
import dataaccess.DataAccessFacade;


public class AllBookIdsWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = 1L;
	public static final AllBookIdsWindow INSTANCE = new AllBookIdsWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;

	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private TextArea textArea;

	private DefaultTableModel tableModel;
    private JTable table;
    private JTextField isbnTxtField;
    private JTextField titleTxtField;
    Vector<String> columnNames = new Vector<>();


	//Singleton class
	private AllBookIdsWindow() {}

	@Override
	public void init() {
		setSize(1200, 800);

		ImageBackgroundPanel backgroundPanel = new ImageBackgroundPanel(Util.getImagePath() + "login-background.png");

		JPanel formPanel = createBookForm();
		formPanel.setOpaque(false);
		formPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		JButton menuLink = createMenuLink();

		backgroundPanel.setLayout(new BorderLayout());
		backgroundPanel.add(menuLink, BorderLayout.WEST);
		backgroundPanel.add(formPanel,BorderLayout.EAST);

		getContentPane().add(backgroundPanel);
		setLocationRelativeTo(null);
	}

	private JButton createMenuLink() {
		JButton linkButton = new JButton("> Add New Book");
		linkButton.setLayout(new FlowLayout(FlowLayout.LEFT));
        linkButton.setFont(new Font("Arial", Font.PLAIN, 14));
        linkButton.setForeground(Color.WHITE);
        linkButton.setBorderPainted(false);
        linkButton.setContentAreaFilled(false);
        linkButton.setFocusPainted(false);
        linkButton.setPreferredSize(new Dimension(100,100));
        return linkButton;
	}

	private JPanel createBookForm() {
		
		loadTableForm();

        JPanel filterPanel = createFilterForm();
        JPanel main = new JPanel(new BorderLayout());
        JLabel jLabel = new JLabel("Book");
        jLabel.setFont(new Font("Arial", Font.BOLD,22));
        jLabel.setOpaque(false);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(jLabel,BorderLayout.NORTH);
        topPanel.add(filterPanel, BorderLayout.CENTER);
        main.add(topPanel,BorderLayout.NORTH);
        main.add(new JScrollPane(table), BorderLayout.CENTER);
        return main;
	}

	private void loadTableForm() {
		
		Vector<Vector<Object>> bookList = getBookList();
		getColumnNames();
        tableModel = new DefaultTableModel(bookList, columnNames);

        table = new JTable(tableModel);
        ButtonColumn buttonColumn = new ButtonColumn(table, tableModel.getColumnCount() - 1);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
	}

	private void getColumnNames() {
		columnNames.add("ISBN");
		columnNames.add("Title");
		columnNames.add("Authors");
		columnNames.add("MCL");
		columnNames.add("Copies");
		columnNames.add("Available");
		columnNames.add("Add Copy");
	}

	private Vector<Vector<Object>> getBookList() {
		DataAccessFacade dataAccess = new DataAccessFacade();
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

	private class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, ActionListener, TableCellEditor {
        private JButton button;
        private JTable table;
        private int column;

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
            // Perform the action for the clicked button
            // For demonstration purposes, let's display a message
            JOptionPane.showMessageDialog(null, "Button clicked in row: " + row);
            fireEditingStopped(); // Make sure to stop editing after the action
        }
    }

	private JPanel createFilterForm() {
        JPanel filterPanel = new JPanel(new GridLayout(1,6));

        JPanel criteriaPanel = new JPanel(new FlowLayout());
		JLabel isbnLabel = new JLabel("ISBN");
		isbnTxtField = new JTextField(15);
		isbnTxtField.addActionListener(new AddFilterActionListener());

		criteriaPanel.add(isbnLabel);
		criteriaPanel.add(isbnTxtField);

        JPanel criteriaPanel2 = new JPanel(new FlowLayout());
		JLabel titleLabel = new JLabel("Title");
		titleTxtField = new JTextField(15);
		titleTxtField.addActionListener(new AddFilterActionListener());

		criteriaPanel2.add(titleLabel);
		criteriaPanel2.add(titleTxtField);

		JPanel butPanel = new JPanel(new FlowLayout());
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
        filterPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		return filterPanel;
	}

	JPanel createFilterField(JTextField txt, String title) {
		JPanel filterPanel = new JPanel(new FlowLayout());
		JLabel jLabel = new JLabel(title);
        txt = new JTextField(15);
        txt.addActionListener(new AddFilterActionListener());

        filterPanel.add(jLabel);
        filterPanel.add(txt);
        return filterPanel;
	}


	void performFitler() {
		String title = titleTxtField.getText();
		String isbn = isbnTxtField.getText();
		String data = title + " , " + isbn;

		DataAccessFacade dataAccess = new DataAccessFacade();
		List<Book> books =  dataAccess.searchBook(isbn,title);
		Vector<Vector<Object>> asdas = covertToTableData(books);
		tableModel.setDataVector(asdas, columnNames);
	}

	class AddFilterActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			performFitler();
		}

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

	public void init1() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		defineTopPanel();
		defineMiddlePanel();
		defineLowerPanel();
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		isInitialized = true;
	}

	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AllIDsLabel = new JLabel("All Book IDs");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(AllIDsLabel);
	}

	public void defineMiddlePanel() {
		middlePanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
		middlePanel.setLayout(fl);
		textArea = new TextArea(8, 20);
		//populateTextArea();
		middlePanel.add(textArea);

	}

	public void defineLowerPanel() {

		JButton backToMainButn = new JButton("<= Back to Main");
		backToMainButn.addActionListener(new BackToMainListener());
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerPanel.add(backToMainButn);
	}

	class BackToMainListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);

		}
	}

	public void setData(String data) {
		textArea.setText(data);
	}

//	private void populateTextArea() {
//		//populate
//		List<String> ids = ci.allBookIds();
//		Collections.sort(ids);
//		StringBuilder sb = new StringBuilder();
//		for(String s: ids) {
//			sb.append(s + "\n");
//		}
//		textArea.setText(sb.toString());
//	}

	@Override
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;

	}
}
