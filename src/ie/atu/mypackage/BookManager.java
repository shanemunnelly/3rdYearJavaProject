package ie.atu.mypackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

public class BookManager implements Serializable {
	public static final long serialVersionUID = 2L;

	private List<Book> BooksList;

	// Constructor
	public BookManager() {
		this.BooksList = new ArrayList<>();
	}

	// Getters and Setters
	public List<Book> getBooksList() {
		return BooksList;
	}

	public void setBooksList(List<Book> BooksList) {
		this.BooksList = BooksList;
	}

	public boolean addBookToList(String BookId, String author, int Pages, String genre, int Size) {
		// If Book is NOT already on list
		if (!isOnList(BookId)) {
			// Create Book object with valid details and add Book to the list
			Book newBook = new Book(BookId, author, Pages, genre, Size);
			return this.BooksList.add(newBook);
		}
		// If Book details are invalid or if Book is already on list return false
		System.out.println("Book with ID " + BookId + " could not be added to list!");
		return false;
	}

	// Remove Book from list given studendID
	public boolean removeBookFromList(String BookId) {
		return BooksList.remove(findBookByID(BookId));
	}

	public Book findBookByID(String bookId) {
		// Search all Book objects in the Book list
		for (Book BookObject : BooksList) {
			// Compare IDs to ID passed in
			if (bookId.equals(BookObject.getbookId())) {
				// If a match is found return the Book object
				System.out.println("Book object with ID = " + bookId + " was found on list!");
				return BookObject;
			}
		}
		System.out.println("Book object with ID = " + bookId + " was NOT found on list!");
		return null;
	}

	public boolean isOnList(String bookId) {
		return BooksList.contains(findBookByID(bookId));

	}

	public Book findBooksByName(String Author) {
		// Search all Book objects in the Book list
		for (Book BookObject : BooksList) {
			// Compare IDs to ID passed in
			if (Author.equals(BookObject.getAuthor())) {
				System.out.println("Book object with the author = " + Author + " was found on list!");
				return BookObject;
			}
		}
		System.out.println("Book object with the author = " + Author + " was NOT found on list!");
		return null;
	}

	public Book findBooksByGenre(String genre) {
		// Search all Book objects in the Books list
		for (Book BookObject : BooksList) {
			// Compare IDs to ID passed in
			if (genre.equals(BookObject.getgenre())) {
				// If a match is found print the Book details to console
				System.out.println("Book object with the genre = " + genre + " was found on list!");
				return BookObject;
			}
		}
		System.out.println("Book object with the genre = " + genre + " was NOT found on list!");
		return null;
	}

	// Show total number of Books in List
	public void printTotalNumberOfBooks() {
		System.out.println(this.BooksList.size());
	}

	// Print Book list to console
	public void printBooksList() {
		System.out.println("ID,NAME,AGE,GENRE, SIZE");
		System.out.println("========================");
		for (Book BookObject : BooksList) {
			System.out.println(BookObject.toString());
		}
		System.out.println("========================");
	}

	// Read Book details from file
	public void readBookDataFromCSVFile(String pathToBookCSVFile) {
		File BookCSVFile = null;
		FileReader BookCSVFileReader = null;
		BufferedReader bufferedBookCSVFileReader = null;
		String bufferData = null; // Used to store lines of data we read from the buffer

		// Create a file reader
		try {
			BookCSVFile = new File(pathToBookCSVFile);
			BookCSVFileReader = new FileReader(BookCSVFile);
			// Add a buffer to the file reader
			bufferedBookCSVFileReader = new BufferedReader(BookCSVFileReader);
			// Read first line of file and discard it. It contains column headers.
			bufferedBookCSVFileReader.readLine();

			while ((bufferData = bufferedBookCSVFileReader.readLine()) != null) {
				// System.out.println(bufferData);
				String[] BookFieldValues = bufferData.split(",");
				// System.out.println(Arrays.toString(BookFieldValues));
				String bookId = BookFieldValues[0];
				String Author = BookFieldValues[1];
				int Pages = Integer.parseInt(BookFieldValues[2]);
				String genre = BookFieldValues[3];
				int Size = Integer.parseInt(BookFieldValues[4]);
				this.addBookToList(bookId, Author, Pages, genre, Size); // Add Book to the BookList
			}
			System.out.println("Book data read from CSV file located at " + pathToBookCSVFile);
		} catch (NullPointerException npExc) {
			System.err.println("ERROR: Books NOT saved to file!");
			npExc.printStackTrace();
		} catch (FileNotFoundException fnfExc) {
			System.err.println("ERROR: Books NOT saved to file!");
			fnfExc.printStackTrace();
		} catch (IOException IOExc) {
			System.err.println("ERROR: Books NOT saved to file!");
			IOExc.printStackTrace();
		} finally {
			try {
				// Flushes buffer, which transfers buffer data to the file, then closes buffer.
				bufferedBookCSVFileReader.close();
				// Close the file reader stream
				BookCSVFileReader.close();
			} catch (IOException IOExc) {
				System.err.println("ERROR: Could not close the buffer file reader!");
				IOExc.printStackTrace();
			} // End catch
		} // End finally
	} // End read method

	// Write Book details to file
	public void writeBookDataToCSVFile(String pathToBookCSVFile) {
		File BookCSVFile = null;
		FileWriter BookFileWriterStream = null;
		BufferedWriter bufferedBookFileWriterStream = null;
		try {
			// Create a buffered file writer which can write one line at a time
			BookCSVFile = new File(pathToBookCSVFile);
			BookFileWriterStream = new FileWriter(BookCSVFile);
			bufferedBookFileWriterStream = new BufferedWriter(BookFileWriterStream);

			// Write column headers to CSV file
			bufferedBookFileWriterStream.write("ID,Firstname,Age" + "\n");

			// Write out Book data from Book List to buffer and flush it to CSV file
			for (Book BookObject : BooksList) {
				bufferedBookFileWriterStream.write(BookObject.getbookId() + "," + BookObject.getAuthor() + ","
						+ BookObject.getPages() + "," + BookObject.getPages() + "," + BookObject.getSize() + "/n");
				bufferedBookFileWriterStream.flush(); // Flush buffer and transfer buffer data to the file
			}
			System.out.println("Book data written to CSV file located at " + pathToBookCSVFile);
		} catch (NullPointerException npExc) {
			System.err.println("ERROR: Books NOT saved to file!");
			npExc.printStackTrace();
		} catch (FileNotFoundException fnfExc) {
			System.err.println("ERROR: Books NOT saved to file!");
			fnfExc.printStackTrace();
		} catch (IOException IOExc) {
			System.err.println("ERROR: Books NOT saved to file!");
			IOExc.printStackTrace();
		} finally {
			try {
				// Close buffer
				bufferedBookFileWriterStream.close();
				// Close file writer
				BookFileWriterStream.close();
			} catch (NullPointerException npExc) {
				System.out.println("ERROR: Could not close the ObjectOutputStream or FileOutputStream!");
				npExc.printStackTrace();
			} catch (IOException ioExc) {
				ioExc.printStackTrace();
			} // End catchEnd catch
		} // End finally
	} // End Save method

	// Method to serialize the Book Manager Object
	public void writeBookManagerObjectToFile(String pathToFile) {

		File BookObjectByteFile = null;
		FileOutputStream fosToBookManagerObjectByteFile = null;
		ObjectOutputStream oosToBookManagerObjectByteFile = null;
//Try catch statment to catch errors
		try {
			BookObjectByteFile = new File(pathToFile);
			fosToBookManagerObjectByteFile = new FileOutputStream(BookObjectByteFile);
			oosToBookManagerObjectByteFile = new ObjectOutputStream(fosToBookManagerObjectByteFile);
			oosToBookManagerObjectByteFile.writeObject(this);
		} catch (NullPointerException npExc) {
			npExc.printStackTrace();
		} catch (FileNotFoundException fnfExc) {
			fnfExc.printStackTrace();
		} catch (SecurityException secExc) {
			secExc.printStackTrace();
		} catch (InvalidClassException icExc) {
			icExc.printStackTrace();
		} catch (NotSerializableException nsExc) {
			nsExc.printStackTrace();
		} catch (IOException IOExc) {
			IOExc.printStackTrace();
		} finally {
			try {
				// Close ObjectOutputStream
				oosToBookManagerObjectByteFile.close();
				// Close FileOutputStream
				fosToBookManagerObjectByteFile.close();
			} catch (NullPointerException npExc) {
				System.out.println("ERROR: Could not close the ObjectOutputStream or FileOutputStream!");
				npExc.printStackTrace();
			} catch (IOException ioExc) {
				ioExc.printStackTrace();
			} // End catch
		} // End finally
	}

	// Method to de-serialize the Book Manager Object
	public BookManager readBookManagerObjectFromFile(String pathToFile) {
		File BookManagerObjectByteFile = null;
		FileInputStream fisFromBookManagerObjectByteFile = null;
		ObjectInputStream oisFromBookManagerObjectByteFile = null;
		BookManager BookManagerObjectReadIn = null; // Create empty BookManager object to store read in object
//try catch statment to stop errors
		try {
			BookManagerObjectByteFile = new File(pathToFile);
			fisFromBookManagerObjectByteFile = new FileInputStream(BookManagerObjectByteFile);
			oisFromBookManagerObjectByteFile = new ObjectInputStream(fisFromBookManagerObjectByteFile);
			BookManagerObjectReadIn = (BookManager) oisFromBookManagerObjectByteFile.readObject();
		} catch (NullPointerException npExc) {
			npExc.printStackTrace();
		} catch (FileNotFoundException fnfExc) {
			fnfExc.printStackTrace();
		} catch (SecurityException secExc) {
			secExc.printStackTrace();
		} catch (StreamCorruptedException scExc) {
			scExc.printStackTrace();
		} catch (InvalidClassException icExc) {
			icExc.printStackTrace();
		} catch (OptionalDataException odExc) {
			odExc.printStackTrace();
		} catch (IOException IOExc) {
			IOExc.printStackTrace();
		} catch (ClassNotFoundException cnfExc) {
			cnfExc.printStackTrace();
		} finally {
			try {
				// Close ObjectOutputStream
				oisFromBookManagerObjectByteFile.close();
				// Close FileOutputStream
				fisFromBookManagerObjectByteFile.close();
			} catch (NullPointerException npExc) {
				System.out.println("ERROR: Could not close the ObjectOutputStream or FileOutputStream!");
				npExc.printStackTrace();
			} catch (IOException ioExc) {
				ioExc.printStackTrace();
			} // End catch
		} // End finally
		return BookManagerObjectReadIn; // Returns null if no object is read in.
	}
}
