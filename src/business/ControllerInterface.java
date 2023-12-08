package business;

import java.util.HashMap;
import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<LibraryMember> getAllMembers();
	public List<Book> getAllBooks();
	public CheckoutRecordEntry checkoutBook(String memberId, String isbn) throws LibrarySystemException;
	public HashMap<BookCopy, CheckoutRecordEntry> checkBookOverdue(String isbn) throws LibrarySystemException;
	public void saveNewMember(LibraryMember mems);
<<<<<<< HEAD
	public CheckoutRecord getCheckoutRecord(String memberID);
	public List<CheckoutRecord> getAllCheckoutRecord();
=======
	public HashMap<String,Book> addNewCopy(String isbn, String input);
	public List<CheckoutRecordEntry> allCheckoutEntries();
	
>>>>>>> bf67862a4f1b87bfadb1f4b47ae76efa50f8af43
	
}
