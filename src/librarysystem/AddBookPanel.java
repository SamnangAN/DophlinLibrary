package librarysystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import business.Address;
import business.Author;
import business.Book;
import business.ControllerInterface;
import business.SystemController;

public class AddBookPanel extends JPanel {
	private static AddBookPanel INSTANCE;

	protected static final String BACK_LBL = "> Back";
	private JTextField isbnTxtField;
	private JTextField titleTxtField;
	private JTextField maximunCheckoutTxtField;
	private JTextField numberOfCopyTxtField;

	private boolean isInitialized = false;

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	private DefaultTableModel tableModel;
	private JTable table;
	private Vector<String> columnNames = new Vector<>();
	private Book book = new Book();
	private JTextField firstNameField, lastNameField, streetField, cityField, stateField, zipField, telephoneField,
			bioField;
	private ControllerInterface ci = new SystemController();

	public AddBookPanel() {
	}

	public static AddBookPanel getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AddBookPanel();
		}
		return INSTANCE;
	}

	public void prepareData() {
		book.resetAuthor();
		isbnTxtField.setText("");
		titleTxtField.setText("");
		maximunCheckoutTxtField.setText("");
		numberOfCopyTxtField.setText("");
		reloadTableForm();
	}

	public JPanel init() {
		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setLayout(new BorderLayout());
		JPanel formPanel = createBookForm();
		JPanel menuLink = createMenuLink();

		backgroundPanel.setLayout(new BorderLayout());
		backgroundPanel.add(formPanel, BorderLayout.CENTER);
		return backgroundPanel;

	}

	private JPanel createMenuLink() {
		JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton linkButton = new JButton(BACK_LBL);
		linkButton.setFont(new Font("Arial", Font.PLAIN, 14));
		linkButton.setForeground(Color.WHITE);
		linkButton.setBorderPainted(false);
		linkButton.setContentAreaFilled(false);
		linkButton.setFocusPainted(false);
		addHoverUnderlineText(linkButton);
		linkButton.addActionListener(new CancelBookActionListener());
		leftPanel.add(linkButton);
		leftPanel.setOpaque(false);
		leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
		return leftPanel;
	}

	private void addHoverUnderlineText(JButton linkButton) {
		linkButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				linkButton.setText("<html><u>" + BACK_LBL + "</u></html>");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				linkButton.setText("<html><u>" + BACK_LBL + "</u></html>");
			}
		});

	}

	private JPanel createBookForm() {
		JPanel form = new JPanel(new BorderLayout());
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(createFormTopPanel(), BorderLayout.NORTH);
		topPanel.setOpaque(false);

		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(createFormCenterPanel(), BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton saveButton = new JButton("Add");
		saveButton.addActionListener(new SaveBookActionListener());
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new CancelBookActionListener());

		bottomPanel.add(cancelButton);
		bottomPanel.add(saveButton);

		form.add(topPanel, BorderLayout.NORTH);
		form.add(centerPanel, BorderLayout.CENTER);
		form.add(bottomPanel, BorderLayout.SOUTH);
		form.setOpaque(false);
		return form;
	}

	private JPanel createFormCenterPanel() {
		loadTableForm();
		JPanel authorPanel = new JPanel(new BorderLayout());

		JPanel addAuthor = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel authorLbl = new JLabel("Author:");
		JButton addAuthorButton = new JButton("+");
		addAuthorButton.addActionListener(new AddAuthorActionListener());
		addAuthor.add(authorLbl);
		addAuthor.add(addAuthorButton);

		authorPanel.add(addAuthor, BorderLayout.NORTH);

		JScrollPane tableScrollPane = new JScrollPane(table);
		authorPanel.add(tableScrollPane, BorderLayout.CENTER);

		authorPanel.setOpaque(false);
		return authorPanel;
	}

	private JPanel createFormTopPanel() {
		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setOpaque(false);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 10, 10, 10);

		JLabel addNewBookLbl = new JLabel("Add New Book");
		addNewBookLbl.setFont(new Font("Arial", Font.BOLD, 20));

		JLabel isbnLbl = new JLabel("ISBN:");
		isbnTxtField = new JTextField(15);
		isbnTxtField.addActionListener(new CheckExitingIsbnActionListener());

		JLabel titleLbl = new JLabel("Title:");
		titleTxtField = new JTextField(15);

		JLabel maximunCheckoutLbl = new JLabel("Maximum Checkout Length:");
		maximunCheckoutTxtField = new JTextField(15);

		JLabel numberOfCopyLbl = new JLabel("Number Of Copy:");
		numberOfCopyTxtField = new JTextField(15);

		gbc.gridx = 0;
		gbc.gridy = 0;
		formPanel.add(addNewBookLbl, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		formPanel.add(isbnLbl, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		formPanel.add(isbnTxtField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		formPanel.add(titleLbl, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		formPanel.add(titleTxtField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		formPanel.add(maximunCheckoutLbl, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		formPanel.add(maximunCheckoutTxtField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		formPanel.add(numberOfCopyLbl, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		formPanel.add(numberOfCopyTxtField, gbc);

		return formPanel;
	}

	private void loadTableForm() {
		initColumnNames();
		List<Author> authors = book.getAuthors();
		Vector<Vector<Object>> authorList = covertToTableData(authors);
		tableModel = new DefaultTableModel();
		tableModel.setDataVector(authorList, columnNames);

		table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(1000, 200));
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
		table.setRowSorter(sorter);
	}

	private void reloadTableForm() {
		List<Author> authors = book.getAuthors();
		Vector<Vector<Object>> authorList = covertToTableData(authors);
		tableModel.setDataVector(authorList, columnNames);
	}

	private Vector<Vector<Object>> covertToTableData(Collection<Author> authors) {
		Vector<Vector<Object>> data = new Vector<>();
		authors.forEach(author -> {
			Vector<Object> bookObject = new Vector<>();
			bookObject.add(author.getFirstName());
			bookObject.add(author.getLastName());
			bookObject.add(author.getTelephone());
			bookObject.add(author.getAddress().toString());
			bookObject.add(author.getBio());
			data.add(bookObject);
		});
		return data;
	}

	private void initColumnNames() {
		columnNames.add("FirstName");
		columnNames.add("LastName");
		columnNames.add("Telephone");
		columnNames.add("Address");
		columnNames.add("Bio");
	}

	class SaveBookActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String mcl = maximunCheckoutTxtField.getText();
			String ncp = numberOfCopyTxtField.getText();
			if (!Util.isNumeric(ncp) || !Util.isNumeric(mcl)) {
				JOptionPane.showMessageDialog(null, "Cannot read Maximum Checkout or Number of copy!");
			} else {
				String isbn = isbnTxtField.getText();
				Book existingBook = ci.searchBook(isbn);
				if (existingBook != null) {
					JOptionPane.showMessageDialog(null, "ISBN already exist!!!");
				} else {
					book.setIsbn(isbn);
					book.setMaxCheckoutLength(Integer.parseInt(mcl));
					book.setTitle(titleTxtField.getText());
					book.setCopies(Integer.parseInt(ncp));
					ci.saveBook(book);
					AllBookPanel.getInstance().performFitler();
					new CancelBookActionListener().actionPerformed(e);
				}
			}
		}

	}

	class CheckExitingIsbnActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String isbn = isbnTxtField.getText();
			Book book = ci.searchBook(isbn);
			if (book != null) {
				AllBookPanel.getInstance().openPopupForm(isbn, true);
			}
		}

	}

	class CancelBookActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			AllBookPanel.getInstance().backgroundPanelMain.setVisible(true);
			AllBookPanel.getInstance().backgroundPanelAddNewMember.setVisible(false);
		}

	}

	class AddAuthorActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			openPopupForm();
		}

		private void openPopupForm() {
			JDialog dialog = new JDialog();
			dialog.setTitle("Add Author");

			JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new GridLayout(9, 2, 10, 5));

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

			mainPanel.add(new JLabel("Bio:"));
			bioField = new JTextField();
			mainPanel.add(bioField);

			JButton submitButton = new JButton("Submit");
			submitButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String firstName = firstNameField.getText();
					String lastName = lastNameField.getText();
					String telephone = telephoneField.getText();
					String street = streetField.getText();
					String city = cityField.getText();
					String zip = zipField.getText();
					String stateNum = stateField.getText();
					Address address = new Address(street, city, stateNum, zip);
					String bio = bioField.getText();
					Author author = new Author(firstName, lastName, telephone, address, bio);
					book.addAuthor(author);
					reloadTableForm();
					JOptionPane.showMessageDialog(dialog, "New Author is saved");
					dialog.dispose();
				}
			});
			mainPanel.add(submitButton);
			mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			JLabel lbl = new JLabel("Add New Author");
			lbl.setFont(new Font("Arial", Font.BOLD, 20));
			dialog.setLayout(new BorderLayout());
			dialog.add(mainPanel, BorderLayout.NORTH);
			dialog.setSize(600, 450);
			dialog.setModal(true);
			dialog.setLocationRelativeTo(null);
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

			dialog.setVisible(true);
		}

	}

}
