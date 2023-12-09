package business;

import java.io.Serializable;
import java.time.LocalDate;


import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private CheckoutRecord checkoutRecord;
	
	public LibraryMember(String memberId, String fname, String lname, String tel,Address add) {
		super(fname,lname, tel, add);
		this.memberId = memberId;
		this.checkoutRecord = new CheckoutRecord(this);
	}
	
	
	public String getMemberId() {
		return memberId;
	}
	
	public CheckoutRecord getCheckoutRecord() {
		return this.checkoutRecord;
	}

	public CheckoutRecordEntry checkout(BookCopy bookCopy, LocalDate checkoutDate, LocalDate dueDate) {
		bookCopy.changeAvailability();
		return checkoutRecord.addCheckoutRecordEntry(bookCopy, checkoutDate, dueDate);
	}
	
	public String getFullNameWithId() {
		return memberId + " " + getFirstName() + " " + getLastName();
	}
	
	
	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + 
				", " + getTelephone() + " " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
