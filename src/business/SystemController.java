package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	public static List<LibraryMember> memberList = new ArrayList<LibraryMember> ();
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		
	}
	
	public void addNewBookCopy(String isbn,int numberOfCopy) {
		DataAccess da = new DataAccessFacade();
		Book b = da.searchBook(isbn);
		b.addCopy();
    	while(numberOfCopy>1) {
    		b.addCopy();
    		numberOfCopy -= 1;
    	}
    	da.addNewCopy(b);
    }
	@Override
	public HashMap<String, Book> readBooksMap() {
		DataAccess da = new DataAccessFacade();
		return da.readBooksMap();
	}
	
	@Override
	public Book searchBook(String isbn) {
		DataAccess da = new DataAccessFacade();
		return da.searchBook(isbn);
	}
	@Override
	public List<Book> searchBook(String isbn, String title) {
		HashMap<String,Book> bookMap = readBooksMap();
		return bookMap.values().stream()
		.filter(book -> filterBooks(book,isbn,title))
		.collect(Collectors.toList());
	}
	@Override
	public void saveBook(Book book) {
		new DataAccessFacade().saveBook(book);
	}
	
	private boolean filterBooks(Book book, String isbn, String title) {
		boolean matchIsbn = false;
		boolean matchTitle = false;
		if(isbn != null && !isbn.isEmpty()) {
			if(book.getIsbn().toLowerCase().contains(isbn.toLowerCase())) {
				matchIsbn = true;
			}
		}else {
			matchIsbn = true;
		}
		if(title != null && !title.isEmpty()) {
			if(book.getTitle().toLowerCase().contains(title.toLowerCase())) {
				matchTitle = true;
			}
		}else {
			matchTitle = true;
		}
		return matchIsbn && matchTitle;
	}
	
	@Override
	public CheckoutRecordEntry checkoutBook(String memberId, String isbn) throws LibrarySystemException{
		DataAccess da = new DataAccessFacade();
		LibraryMember member = da.searchMember(memberId);
		if(member == null) {
			throw new LibrarySystemException("Given Member ID does not exist!	@Override\r\n"
					+ "	public List<CheckoutRecordEntry> allCheckoutEntries() {\r\n"
					+ "		// TODO Auto-generated method stub\r\n"
					+ "		return null;\r\n"
					+ "	}\r\n"
					+ "	");
		}
		Book book = da.searchBook(isbn);
		if(book == null || !book.isAvailable()) {
			throw new LibrarySystemException("Book with given ISBN is not available!");
		}
		System.out.println("available book: " + book.getNextAvailableCopy());
		CheckoutRecordEntry cr = member.checkout(book.getNextAvailableCopy(), LocalDate.now(), LocalDate.now().plusDays(book.getMaxCheckoutLength()));
		da.saveNewMember(member);
		da.saveBook(book);
		return cr;
	}
	
	@Override
	public HashMap<BookCopy, CheckoutRecordEntry> checkBookOverdue(String isbn) throws LibrarySystemException{
		DataAccess da = new DataAccessFacade();		
		HashMap<BookCopy, CheckoutRecordEntry> result = new HashMap<BookCopy, CheckoutRecordEntry> ();
		Book book = da.searchBook(isbn);
		if(book==null) {
			throw new LibrarySystemException("Book with given ISBN was not found!");
		}
		
		for (BookCopy bookCopy: book.getCopies()) {
			result.put(bookCopy, null);
		}

		for (LibraryMember member: da.readMemberMap().values()) {
			for (CheckoutRecordEntry entry: member.getCheckoutRecord().getCheckoutRecordEntries()) {
				if (entry.getBookCopy().getBook().equals(book)) {
					result.put(entry.getBookCopy(), entry);
				}
			}
		}
		return result; 		
	}
	@Override
	public void saveNewMember(LibraryMember mems) {
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(mems);
	}
	
	@Override
	public List<CheckoutRecordEntry> getAllCheckoutEntries() {
		DataAccess da = new DataAccessFacade();
		List<CheckoutRecordEntry> allEntries = new ArrayList<CheckoutRecordEntry>();
		for (LibraryMember member: da.readMemberMap().values()) {
			System.out.println("member checkoutRecord: " + member.getCheckoutRecord());
			if (member.getCheckoutRecord() != null && member.getCheckoutRecord().getCheckoutRecordEntries() != null) {
				allEntries.addAll(member.getCheckoutRecord().getCheckoutRecordEntries());
			}
		}
		return allEntries;
	}
	@Override
	public List<LibraryMember> getAllMembers() {
		DataAccess da = new DataAccessFacade();
		return new ArrayList<LibraryMember>(da.readMemberMap().values());
	}
	@Override
	public List<Book> getAllBooks() {
		DataAccess da = new DataAccessFacade();
		return new ArrayList<Book>(da.readBooksMap().values());
	}	
	
	public CheckoutRecord getCheckoutRecord(String memberID) {
		DataAccess da = new DataAccessFacade();	
		LibraryMember mb = da.searchMember(memberID);
		CheckoutRecord rc = new CheckoutRecord(mb);
		return rc;
	}
	public List<CheckoutRecord> getAllCheckoutRecord() {
		DataAccess da = new DataAccessFacade();	
		List<CheckoutRecord> re = new ArrayList<>();
		HashMap<String, LibraryMember> us = da.readMemberMap();
		List<LibraryMember> mems = us.values().stream().collect(Collectors.toList());
		for(LibraryMember m:mems) {
			re.add(new CheckoutRecord(m));
		}
		return re;
	}
}
