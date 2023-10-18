package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import data_structures.ArrayList;
import data_structures.DoublyLinkedList;
import data_structures.SinglyLinkedList;
import interfaces.FilterFunction;
import interfaces.List;

/**
 * Represents a library catalog that manages books and users.
 * 
 * @author Eliel Cruz Felix
 */
public class LibraryCatalog {
	private List<Book> books;
    private List<User> users;
    
    /**
     * Constructs a new LibraryCatalog and initializes it with data from files.
     *
     * @throws IOException If there is an issue reading data files.
     */
    public LibraryCatalog() throws IOException {
        this.books = new ArrayList<>();
        this.books = getBooksFromFiles();
        this.users = new ArrayList<>();
        this.users = getUsersFromFiles();
    }
    
    /**
     * Loads books from a CSV file and returns a list of books.
     *
     * @return A list of books loaded from the data file.
     * @throws IOException If there is an issue reading from the file.
     */
    private List<Book> getBooksFromFiles() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("data/catalog.csv"));
        String line;
        
        try {
            reader.readLine(); // Skip the header line
            while ((line = reader.readLine()) != null) {
                String[] bookInfo = line.split(",");
                Book book = new Book(
                        Integer.parseInt(bookInfo[0].trim()),
                        bookInfo[1].trim(),
                        bookInfo[2].trim(),
                        bookInfo[3].trim(),
                        LocalDate.parse(bookInfo[4].trim()),
                        Boolean.parseBoolean(bookInfo[5].trim())
                );
                this.books.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
        return this.books;
    }
    
    /**
     * Loads users from a CSV file and returns a list of users.
     *
     * @return A list of users loaded from the data file.
     * @throws IOException If there is an issue reading from the file.
     */
    private List<User> getUsersFromFiles() throws IOException {
    	BufferedReader reader = new BufferedReader(new FileReader("data/user.csv"));
	    String line;
	    
	    try {
	        reader.readLine(); // Skip the header line
	        while ((line = reader.readLine()) != null) {
	            String[] userInfo = line.split(",");
	            List<Book> checkedOutList = new ArrayList<>();
	            if (userInfo.length > 2) {
	                String[] bookIds = userInfo[2].trim().split(" "); 
	                for (String bookId : bookIds) {
	                    int id = Integer.parseInt(bookId.replace("{", "").replace("}", ""));
	                    checkedOutList.add(getBookById(id));
	                }
	            }
	            User user = new User(
	                    Integer.parseInt(userInfo[0].trim()),
	                    userInfo[1].trim(),
	                    checkedOutList
	            );
	            this.users.add(user);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        reader.close();
	    }
	    return this.users;
    }
    
    /**
     * Retrieves a book by its unique ID.
     *
     * @param id The unique ID of the book to retrieve.
     * @return The book with the specified ID, or null if not found.
     */
    private Book getBookById(int id) {
        for (Book book : this.books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null; 
    }
    
    /**
     * Gets the list of books in the catalog.
     *
     * @return The list of books in the catalog.
     */
    public List<Book> getBookCatalog() {
        return this.books;
    }
    
    /**
     * Gets the list of users in the catalog.
     *
     * @return The list of users in the catalog.
     */
    public List<User> getUsers() {
        return this.users;
    }

    /**
     * Adds a new book to the catalog with the given title, author, and genre.
     *
     * @param title  The title of the new book.
     * @param author The author of the new book.
     * @param genre  The genre of the new book.
     */
    public void addBook(String title, String author, String genre) {
        int id = this.books.size() + 1;
        Book book = new Book(id, title, author, genre, LocalDate.of(2023, 9, 15), false);
        
        this.books.add(book);
    }
    
    /**
     * Removes a book from the catalog by its unique ID.
     *
     * @param id The unique ID of the book to be removed.
     */
    public void removeBook(int id) {
        for (int i = 0; i < this.books.size(); i++) {
            if (this.books.get(i).getId() == id) {
                this.books.remove(i);
                break;
            }
        }
    }
    
    /**
     * Checks out a book by its unique ID.
     *
     * @param id The unique ID of the book to be checked out.
     * @return True if the book is successfully checked out, false otherwise.
     */
    public boolean checkOutBook(int id) {
        Book book = getBookById(id);
        
        if (book != null && !book.isCheckedOut()) {
            book.setCheckedOut(true);
            book.setLastCheckOut(LocalDate.now());
            return true;
        }
        return false;
    }

    /**
     * Returns a book by its unique ID.
     *
     * @param id The unique ID of the book to be returned.
     * @return True if the book is successfully returned, false otherwise.
     */
    public boolean returnBook(int id) {
        Book book = getBookById(id);
        
        if (book != null && book.isCheckedOut()) {
            book.setCheckedOut(false);
            book.setLastCheckOut(null);
            return true;
        }
        return false;
    }

    /**
     * Checks the availability of a book by its unique ID.
     *
     * @param id The unique ID of the book to check.
     * @return True if the book is available, false if it's checked out.
     */
    public boolean getBookAvailability(int id) {
        Book book = getBookById(id);
        
        return book != null && !book.isCheckedOut();
    }
    
    /**
     * Counts the number of books with a specific title in the catalog.
     *
     * @param title The title of the books to count.
     * @return The count of books with the specified title.
     */
    public int bookCount(String title) {
        int count = 0;
        
        for (Book book : this.books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                count++;
            }
        }
        return count;
    }
	
    /**
     * Generates a library catalog report and writes it to a file.
     *
     * @throws IOException If there is an issue writing the report to a file.
     */
	public void generateReport() throws IOException {
		
		String output = "\t\t\t\tREPORT\n\n";
		output += "\t\tSUMMARY OF BOOKS\n";
		output += "GENRE\t\t\t\t\t\tAMOUNT\n";
		
		 // Initialize variables to count books by genre
	    int adventureCount = 0;
	    int fictionCount = 0;
	    int classicsCount = 0;
	    int mysteryCount = 0;
	    int sciFiCount = 0;

	    for (Book book : books) {
	        switch (book.getGenre()) {
	            case "Adventure":
	                adventureCount++;
	                break;
	            case "Fiction":
	                fictionCount++;
	                break;
	            case "Classics":
	                classicsCount++;
	                break;
	            case "Mystery":
	                mysteryCount++;
	                break;
	            case "Science Fiction":
	                sciFiCount++;
	                break;
	        }
	    }
		/*
		 * In this section you will print the amount of books per category.
		 * 
		 * Place in each parenthesis the specified count. 
		 * 
		 * Note this is NOT a fixed number, you have to calculate it because depending on the 
		 * input data we use the numbers will differ.
		 * 
		 * How you do the count is up to you. You can make a method, use the searchForBooks()
		 * function or just do the count right here.
		 */
		output += "Adventure\t\t\t\t\t" + (adventureCount) + "\n";
		output += "Fiction\t\t\t\t\t\t" + (fictionCount) + "\n";
		output += "Classics\t\t\t\t\t" + (classicsCount) + "\n";
		output += "Mystery\t\t\t\t\t\t" + (mysteryCount) + "\n";
		output += "Science Fiction\t\t\t\t\t" + (sciFiCount) + "\n";
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + (books.size()) + "\n\n";
		
		/*
		 * This part prints the books that are currently checked out
		 */
		output += "\t\t\tBOOKS CURRENTLY CHECKED OUT\n\n";
		/*
		 * Here you will print each individual book that is checked out.
		 * 
		 * Remember that the book has a toString() method. 
		 * Notice if it was implemented correctly it should print the books in the 
		 * expected format.
		 * 
		 * PLACE CODE HERE
		 */
		
		// Print the books that are currently checked out
		for (Book book : books) {
	        if (book.isCheckedOut()) {
	            output += book.toString() + "\n";
	        }
	    }
		
		// Count the total number of books that are checked out
	    int totalCheckedOutBooks = 0;
	    for (Book book : books) {
	        if (book.isCheckedOut()) {
	            totalCheckedOutBooks++;
	        }
	    }
		
		
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + totalCheckedOutBooks + "\n\n";
		
		
		/*
		 * Here we will print the users the owe money.
		 */
		output += "\n\n\t\tUSERS THAT OWE BOOK FEES\n\n";
		/*
		 * Here you will print all the users that owe money.
		 * The amount will be calculating taking into account 
		 * all the books that have late fees.
		 * 
		 * For example if user Jane Doe has 3 books and 2 of them have late fees.
		 * Say book 1 has $10 in fees and book 2 has $78 in fees.
		 * 
		 * You would print: Jane Doe\t\t\t\t\t$88.00
		 * 
		 * Notice that we place 5 tabs between the name and fee and 
		 * the fee should have 2 decimal places.
		 * 
		 * PLACE CODE HERE!
		 */
		
		// Calculate and print the users who owe money
		for (User user : users) {
	        double totalFees = 0.0;
	        for (Book book : user.getCheckedOutList()) {
	            totalFees += book.calculateFees();
	        }
	        if (totalFees > 0) {
	            output += user.getName() + "\t\t\t\t\t$" + String.format("%.2f", totalFees) + "\n";
	        }
	    }

	    // Calculate and print the total amount of money owed to the library
		double totalFeesOwed = 0.0;
	    for (User user : users) {
	        for (Book book : user.getCheckedOutList()) {
	            totalFeesOwed += book.calculateFees();
	        }
	    }
	    
		output += "====================================================\n";
		output += "\t\t\t\tTOTAL DUE\t$" + (String.format("%.2f", totalFeesOwed)) + "\n\n\n";
		output += "\n\n";
		System.out.println(output);// You can use this for testing to see if the report is as expected.
		
		/*
		 * Here we will write to the file.
		 * 
		 * The variable output has all the content we need to write to the report file.
		 * 
		 * PLACE CODE HERE!!
		 */
		
		// Write the report to a text file
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter("report/expected_report.txt"))) {
	        writer.write(output);
	    }
		
	}
	
	/*
	 * BONUS Methods
	 * 
	 * You are not required to implement these, but they can be useful for
	 * other parts of the project.
	 */
	
	/**
     * Searches for books in the catalog using a filter function.
     *
     * @param func The filter function to use for searching.
     * @return A list of books that match the filter criteria.
     */
	public List<Book> searchForBook(FilterFunction<Book> func) {
		 List<Book> result = new ArrayList<>();
	        
		 for (Book book : this.books) {
	            if (func.filter(book)) {
	                result.add(book);
	            }
	     }
         return result;
	}
	
	 /**
     * Searches for users in the catalog using a filter function.
     *
     * @param func The filter function to use for searching.
     * @return A list of users that match the filter criteria.
     */
	public List<User> searchForUsers(FilterFunction<User> func) {
		  List<User> result = new ArrayList<>();

		    for (User user : this.users) {
		        if (func.filter(user)) {
		            result.add(user);
		        }
		    }
		    return result;
	}
	
}
