package model;

public class CreditCard extends Payment{

	private String creditCardNumber;
	private String cvvCode;
	private String expieringDate;
	
	public CreditCard(String cardHoldersName, String creditCardNumber, String cvvCode, String expieringDate) {
		super();
		super.setName(cardHoldersName);
		this.creditCardNumber = creditCardNumber;
		this.cvvCode = cvvCode;
		this.expieringDate = expieringDate;
	}
	/**
	 * @return the creditCardNumber
	 */
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	/**
	 * @param creditCardNumber the creditCardNumber to set
	 */
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	/**
	 * @return the cvvCode
	 */
	public String getCvvCode() {
		return cvvCode;
	}
	/**
	 * @param cvvCode the cvvCode to set
	 */
	public void setCvvCode(String cvvCode) {
		this.cvvCode = cvvCode;
	}
	/**
	 * @return the expieringDate
	 */
	public String getExpieringDate() {
		return expieringDate;
	}
	/**
	 * @param expieringDate the expieringDate to set
	 */
	public void setExpieringDate(String expieringDate) {
		this.expieringDate = expieringDate;
	}
	
}
