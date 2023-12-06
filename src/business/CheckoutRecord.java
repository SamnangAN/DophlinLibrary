package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class CheckoutRecord implements Serializable{
	private static final long serialVersionUID = 6578027413885460074L;	
	
	private List<CheckoutRecordEntry> checkoutRecordEntries;
	private LibraryMember libraryMember;
	
	public CheckoutRecord(LibraryMember libraryMember) {
		this.setCheckoutRecordEntries(new ArrayList<CheckoutRecordEntry>());
		this.setLibraryMember(libraryMember);
	}

	public List<CheckoutRecordEntry> getCheckoutRecordEntries() {
		return checkoutRecordEntries;
	}

	public void setCheckoutRecordEntries(List<CheckoutRecordEntry> checkoutRecordEntries) {
		this.checkoutRecordEntries = checkoutRecordEntries;
	}
	
	public void addCheckoutRecordEntry(CheckoutRecordEntry entry) {
		this.checkoutRecordEntries.add(entry);
	}

	@Override
	public String toString() {
		return "CheckoutRecord [checkoutRecordEntries=" + checkoutRecordEntries + "]";
	}

	public LibraryMember getLibraryMember() {
		return libraryMember;
	}

	public void setLibraryMember(LibraryMember libraryMember) {
		this.libraryMember = libraryMember;
	}
	
}
