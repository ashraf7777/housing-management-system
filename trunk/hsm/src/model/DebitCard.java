package model;

/**
 * This class represents the subtype DebitCard of the supertype Payment.
 * 
 * @author D20018
 * 
 */

public class DebitCard extends Payment {

	private String accountNumber;
	private String bankNumber;
	private String bankName;

	/**
	 * Customized Construcor to create a new debitcard object
	 * 
	 * @param nameOnCard
	 * @param accountNumber
	 * @param bankNumber
	 * @param nameOfBank
	 */
	public DebitCard(String nameOnCard, String accountNumber,
			String bankNumber, String nameOfBank) {
		super();
		super.setName(nameOnCard);
		this.accountNumber = accountNumber;
		this.bankNumber = bankNumber;
		this.bankName = nameOfBank;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber
	 *            the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the bankNumber
	 */
	public String getBankNumber() {
		return bankNumber;
	}

	/**
	 * @param bankNumber
	 *            the bankNumber to set
	 */
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
}
