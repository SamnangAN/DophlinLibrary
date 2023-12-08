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
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}
	
	@Override
	public void checkoutBook(String memberId, String isbn) throws LibrarySystemException{
		DataAccess da = new DataAccessFacade();
		LibraryMember member = da.searchMember(memberId);
		if(member == null) {
			throw new LibrarySystemException("Given Member ID does not exist!");
		}
		Book book = da.searchBook(isbn);
		if(book == null || !book.isAvailable()) {
			throw new LibrarySystemException("Book with given ISBN is not available!");
		}

		member.checkout(book.getNextAvailableCopy(), LocalDate.now(), LocalDate.now().plusDays(book.getMaxCheckoutLength()));
		da.saveNewMember(member);
		da.saveBook(book);
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
