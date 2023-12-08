package dataaccess;

import java.util.HashMap;
import java.util.List;

import business.Book;
import business.CheckoutRecord;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;

public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public void saveNewMember(LibraryMember member); 
	public void saveBook(Book book);
	
	public LibraryMember searchMember(String memberId);
	public Book searchBook(String isbn);
	public void addNewCopy(Book book);
	public CheckoutRecord getCheckoutRecord(String memberID);
	public List<CheckoutRecord> getAllCheckoutRecord();
}
