package business;

import java.util.HashMap;
import java.util.List;

public interface ControllerInterface {
	public void addNewBookCopy(String isbn, int numberOfCopy);
	public HashMap<String, Book> readBooksMap();
	public void saveBook(Book book);
	public void login(String id, String password) throws LoginException;
	public List<LibraryMember> getAllMembers();
	public List<Book> getAllBooks();
	public CheckoutRecordEntry checkoutBook(String memberId, String isbn) throws LibrarySystemException;
	public HashMap<BookCopy, CheckoutRecordEntry> checkBookOverdue(String isbn) throws LibrarySystemException;
	public void saveNewMember(LibraryMember mems);
	public CheckoutRecord getCheckoutRecord(String memberID);
	public List<CheckoutRecord> getAllCheckoutRecord();
	public Book searchBook(String isbn);
	public List<Book> searchBook(String isbn,String title);
	public List<CheckoutRecordEntry> getAllCheckoutEntries();
	
}
