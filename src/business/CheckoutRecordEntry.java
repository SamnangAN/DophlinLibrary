package business;

import java.io.Serializable;
import java.time.LocalDate;

public final class CheckoutRecordEntry implements Serializable {

	private static final long serialVersionUID = -4982887275869890032L;

	private BookCopy bookCopy;
	private LocalDate checkoutDate;
	private LocalDate dueDate;
	private CheckoutRecord checkoutRecord;

	public CheckoutRecordEntry(BookCopy bookCopy, LocalDate checkoutDate, LocalDate dueDate,
			CheckoutRecord checkoutRecord) {
		this.bookCopy = bookCopy;
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
		this.setCheckoutRecord(checkoutRecord);
	}

	public BookCopy getBookCopy() {
		return bookCopy;
	}

	public void setBookCopy(BookCopy bookCopy) {
		this.bookCopy = bookCopy;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public boolean isOverDue() {
		return dueDate.isBefore(LocalDate.now()) && !bookCopy.isAvailable();
	}

	public CheckoutRecord getCheckoutRecord() {
		return checkoutRecord;
	}

	public void setCheckoutRecord(CheckoutRecord checkoutRecord) {
		this.checkoutRecord = checkoutRecord;
	}

}
