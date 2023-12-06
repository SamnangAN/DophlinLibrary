package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class CheckoutRecord implements Serializable{
	private static final long serialVersionUID = 6578027413885460074L;	
	
	private List<CheckoutRecordEntry> checkoutRecordEntries;
	
	public CheckoutRecord() {
		this.setCheckoutRecordEntries(new ArrayList<CheckoutRecordEntry>());
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
	
}
