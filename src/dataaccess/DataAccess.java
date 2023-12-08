package dataaccess;

import java.util.HashMap;
import java.util.List;

import business.Book;
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
	List<Book> searchBook(String isbn, String title);
	HashMap<String,Book> addNewCopy(String isbn, String input);
}
