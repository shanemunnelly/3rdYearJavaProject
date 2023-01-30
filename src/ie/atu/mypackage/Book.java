package ie.atu.mypackage;

import java.io.Serializable;

public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	// Instanced varibles
	private String bookId;
	private String Author;
	private int Pages;
	private String genre;
	private int Size;

	// Constructor
	public Book(String bookId, String Author, int Pages, String genre, int Size) {
		this.bookId = bookId;
		this.Author = Author;
		this.Pages = Pages;
		this.genre = genre;
		this.Size = Size;
	}

	// Getters
	public String getbookId() {
		return bookId;
	}

	public String getAuthor() {
		return Author;
	}

	public int getPages() {
		return Pages;
	}

	public String getgenre() {
		return genre;
	}

	public int getSize() {
		return Size;
	}

	// Setters
	public void setbookId(String bookId) {
		this.bookId = bookId;
	}

	public void setAuthor(String Author) {
		this.Author = Author;
	}

	public void setPages(int Pages) {
		this.Pages = Pages;
	}

	public void setgenre(String genre) {
		this.genre = genre;
	}

	public void setSize(int Size) {
		this.Size = Size;
	}

	// Check if Book ID is valid
	public static boolean bookidIsValid(String bookId) {
		// Check if Book ID is valid
		if (bookId == null) {
			System.err.println("Book IDs cannot be Blank");
			return false;
		} else if (!(bookId.matches("B0\\d{4}"))) {
			System.err.println("Book ID " + bookId + " does not match the format B01234 ");
			return false;
		} else {
			return true;
		}
	}

	// Check if Author is valid
	public static boolean AuthorIsValid(String Author) {
		if (Author == null) {
			System.err.println("Author name can not be null");
			return false;
		} else if (Author.length() < 2) {
			System.err.println("Author name must be at least 2 characters long");
			return false;
		} else if (!Author.matches("[a-zA-Z]+")) {
			System.err.println("Author name must conatin Upper and lover case letters only");
			return false;
		} else {
			return true;
		}
	}

	// Check if Page is valid
	public static boolean PagesIsValid(int Pages) {
		if (Pages < 100 || Pages > 999) {
			System.err.println("Books must Contain A minimum of 100 and a maximum of 999 words to enter this database");
			return false;
		} else {
			return true;
		}
	}

	// Check if Genre is valid
	public static boolean GenreIsValid(String genre) {
		if (genre == null) {
			System.err.println("genre name can not be Blank");
			return false;
		} else if (genre.length() < 2) {
			System.err.println("Genre must be at least 2 characters long");
			return false;
		} else if (!genre.matches("[a-zA-Z]+")) {
			System.err.println("Genre must conatin Upper and lover case letters only");
			return false;
		} else {
			return true;
		}
	}

	// Check if Size is valid
	public static boolean SizeIsValid(int Size) {
		if (Size < 10 || Size > 100) {
			System.err.println("Size must Be A minimum of 10 and a maximum of 100 cm to enter this database");
			return false;
		} else {
			return true;
		}
	}

	// Check if Book details are valid
	public static boolean isValid(String bookId, String Author, int Pages, String genre, int Size) {
		return bookidIsValid(bookId) && AuthorIsValid(Author) && PagesIsValid(Pages) && GenreIsValid(genre)
				&& SizeIsValid(Size);
	}

	// Method to print BooksId Author pages genre and size
	@Override
	public String toString() {
		return this.bookId + "," + this.Author + "," + this.Pages + "," + this.genre + "," + this.Size;
	}

}
