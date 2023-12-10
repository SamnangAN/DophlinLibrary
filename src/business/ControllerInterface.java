package business;

import java.util.HashMap;
import java.util.List;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;

	// BOOK methods
	public HashMap<String, Book> readBooksMap();

	public void addNewBookCopy(String isbn, int numberOfCopy);

	public void saveBook(Book book);

	public List<Book> getAllBooks();

	public Book searchBook(String isbn);

	public List<Book> searchBook(String isbn, String title);

	
	// MEMBER methods
	public List<LibraryMember> getAllMembers();

	public void saveNewMember(LibraryMember mems);

	public LibraryMember searchMember(String memberId);

	
	// CHECKOUT records
	public CheckoutRecordEntry checkoutBook(String memberId, String isbn) throws LibrarySystemException;

	public HashMap<BookCopy, CheckoutRecordEntry> checkBookOverdue(String isbn) throws LibrarySystemException;

	public List<CheckoutRecordEntry> getAllCheckoutEntries();

	public CheckoutRecord getCheckoutRecord(String memberId) throws LibrarySystemException;
}
