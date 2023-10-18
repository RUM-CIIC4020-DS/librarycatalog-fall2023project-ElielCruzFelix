package main;

import java.time.LocalDate;

/**
 * Represents a book in a library catalog.
 * 
 * @author Eliel Cruz Felix
 */
public class Book {
	
	private int id;
    private String title;
    private String author;
    private String genre;
    private LocalDate lastCheckOut;
    private boolean checkedOut;
    
    /**
     * Constructs a new Book object with the provided details.
     *
     * @param id          The unique identifier of the book.
     * @param title       The title of the book.
     * @param author      The author of the book.
     * @param genre       The genre of the book.
     * @param lastCheckOut The date of the last check-out.
     * @param checkedOut  A boolean indicating if the book is currently checked out.
     */
    public Book(int id, String title, String author, String genre, LocalDate lastCheckOut, boolean checkedOut) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.lastCheckOut = lastCheckOut;
        this.checkedOut = checkedOut;
    }
	
    /**
     * Gets the unique identifier of the book.
     *
     * @return The book's unique identifier.
     */
	public int getId() {
		return id;
	}
	
	/**
     * Sets the unique identifier of the book.
     *
     * @param id The new unique identifier for the book.
     */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
     * Gets the title of the book.
     *
     * @return The title of the book.
     */
	public String getTitle() {
		return title;
	}
	
	/**
     * Sets the title of the book.
     *
     * @param title The new title for the book.
     */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
     * Gets the author of the book.
     *
     * @return The author of the book.
     */
	public String getAuthor() {
		return author;
	}
	
	/**
     * Sets the author of the book.
     *
     * @param author The new author for the book.
     */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
     * Gets the genre of the book.
     *
     * @return The genre of the book.
     */
	public String getGenre() {
		return genre;
	}
	

    /**
     * Sets the genre of the book.
     *
     * @param genre The new genre for the book.
     */
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	/**
     * Gets the date of the last check-out of the book.
     *
     * @return The date of the last check-out.
     */
	public LocalDate getLastCheckOut() {
		return lastCheckOut;
	}
	
	/**
     * Sets the date of the last check-out of the book.
     *
     * @param lastCheckOut The new date of the last check-out.
     */
	public void setLastCheckOut(LocalDate lastCheckOut) {
		this.lastCheckOut = lastCheckOut;
	}
	
	/**
     * Checks if the book is currently checked out.
     *
     * @return True if the book is checked out, false otherwise.
     */
	public boolean isCheckedOut() {
		return checkedOut;
	}
	
	/**
     * Sets the checked-out status of the book.
     *
     * @param checkedOut A boolean indicating if the book is checked out.
     */
	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}
	
	/**
     * Generates a formatted string representation of the book.
     *
     * @return A string in the format "{TITLE} By {AUTHOR}" where both title and author are in uppercase.
     */
	@Override
	public String toString() {
		/*
		 * This is supposed to follow the format
		 * 
		 * {TITLE} By {AUTHOR}
		 * 
		 * Both the title and author are in uppercase.
		 */
		return getTitle().toUpperCase() + " BY " + getAuthor().toUpperCase();
	}
	
	/**
     * Calculates late fees for the book if it is checked out and overdue.
     *
     * @return The calculated late fee amount, or 0 if the book is not checked out or not overdue.
     */
	public float calculateFees() {
		/*
		 * fee (if applicable) = base fee + 1.5 per additional day
		 */
		 LocalDate currentDate = LocalDate.of(2023, 9, 15);

	     if (checkedOut && lastCheckOut != null) {
	         long daysBetween = currentDate.toEpochDay() - lastCheckOut.toEpochDay();
	         if (daysBetween >= 31) {
	             float fee = 10f + (float)((daysBetween - 31) * 1.5);
	             return fee;
	         }
	     }

	     return 0;
	}
}
