package model;


/**
 * This class represents the supertype of the subtypes CreditCard and DebitCard.
 * It is not possible to create an object out of this class,
 * because it just provides the common atttributes of the both subtypes.
 * @author D20018
 *
 */
abstract public class Payment {

	private String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}
