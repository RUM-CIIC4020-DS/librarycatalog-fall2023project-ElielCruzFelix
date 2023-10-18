package main;

import interfaces.List;

/**
 * Represents a user in the library catalog.
 * 
 * @author Eliel Cruz Felix
 */
public class User {
	private int id;
    private String name;
    private List<Book> checkedOutBooks; 
    
    /**
     * Constructs a new User object with the provided details.
     *
     * @param id             The unique identifier of the user.
     * @param name           The name of the user.
     * @param checkedOutBooks A list of books checked out by the user.
     */
    public User(int id, String name, List<Book> checkedOutBooks) {
    	this.id = id;
    	this.name = name;
    	this.checkedOutBooks = checkedOutBooks;
    }
    
    /**
     * Gets the unique identifier of the user.
     *
     * @return The user's unique identifier.
     */
	public int getId() {
		return id;
	}
	
	/**
     * Sets the unique identifier of the user.
     *
     * @param id The new unique identifier for the user.
     */
	public void setId(int id) {
		this.id = id;
	}

	/**
     * Gets the name of the user.
     *
     * @return The name of the user.
     */
	public String getName() {
		return name;
	}

	/**
     * Sets the name of the user.
     *
     * @param name The new name for the user.
     */
	public void setName(String name) {
		this.name = name;
	}

	/**
     * Gets the list of books checked out by the user.
     *
     * @return A list of books currently checked out by the user.
     */
	public List<Book> getCheckedOutList() {
		return checkedOutBooks;
	}

	/**
     * Sets the list of books checked out by the user.
     *
     * @param checkedOutList The new list of books checked out by the user.
     */
	public void setCheckedOutList(List<Book> checkedOutList) {
		this.checkedOutBooks = checkedOutList;
	}
	
}
