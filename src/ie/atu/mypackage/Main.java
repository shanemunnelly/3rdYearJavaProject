
package ie.atu.mypackage;

import java.io.File;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

	// new instance of Book manager class
	BookManager bm = new BookManager();

	@Override
	public void start(Stage primaryStage) {

		int noOfCmdLineArgs = 0; // Used to check if any arguments were passed
		String cmdLineArgs = null; // Used to set stage title

		// grid of nodes
		GridPane gridPane1 = new GridPane(); // Create grid pane node to use as root node of scene

		// Creation of Child Nodes

		Text txtHeader = new Text("Please select an option below:");
		txtHeader.setStyle("-fx-background-color: Grey;" + "-fx-text-fill: white;"  + "-fx-border-color:black;"  );
		Button btnAddBook = new Button("Add Book");
		btnAddBook.setStyle("-fx-background-color: Grey;" + "-fx-text-fill: white;"  + "-fx-border-color:black;"  );
		TextField tfBookID = new TextField();
		tfBookID.setPromptText("Book ID");
		TextField tfBookAuthor = new TextField();
		tfBookAuthor.setPromptText("Book Author");
		TextField tfBookPages = new TextField();
		tfBookPages.setPromptText("Number of pages");
		TextField tfBookgenre = new TextField();
		tfBookgenre.setPromptText("Genre");
		TextField tfBookSize = new TextField();
		tfBookSize.setPromptText("Book size(cm)");
		gridPane1.setStyle("-fx-background-color: #00FFFF;");

		// Create TextArea node for bottom of scene 1 to display output
		TextArea taMyOutput = new TextArea();
		// button and file chooser for loading Book list from binary file
		// button and text field to take a Book ID for deleting a Book
		// from the list
		// Book remover button
		Button btnRemoveBook = new Button("Remove Book ");
		btnRemoveBook.setStyle("-fx-background-color: Grey;" + "-fx-text-fill: white;" + "-fx-border-color:black;" );
		TextField tfDelBook = new TextField();
		tfDelBook.setPromptText("Book ID");
		// Book selection button for ID author pages genre and Size
		Button btnSearchBook = new Button("Find Book");
		btnSearchBook.setStyle("-fx-background-color: Grey;" + "-fx-text-fill: white;"+ "-fx-border-color:black;"  );
		TextField tfSearchBook = new TextField();
		tfSearchBook.setPromptText("Book ID");

		Button btnSearchBookAuthor = new Button("Find Author");
		btnSearchBookAuthor.setStyle("-fx-background-color: Grey;" + "-fx-text-fill: white;"+ "-fx-border-color:black;"  );
		TextField tfSearchBookAuthor = new TextField();
		tfSearchBookAuthor.setPromptText("Book Author");

		Button btnSearchBookgenre = new Button("Find genre");
		btnSearchBookgenre.setStyle("-fx-background-color: Grey;" + "-fx-text-fill: white;" + "-fx-border-color:black;" );
		TextField tfSearchBookgenre = new TextField();
		tfSearchBookgenre.setPromptText("Book genre");

		// These are the buttons in
		Button btnShowTotal = new Button("Show Total Books");
		btnShowTotal.setStyle("-fx-background-color: Grey;" + "-fx-text-fill: white;"+ "-fx-border-color:black;"  );
		Button btnShowBookList = new Button("Show Book List");
		btnShowBookList.setStyle("-fx-background-color: Grey;" + "-fx-text-fill: white;" + "-fx-border-color:black;" );
		Button btnSave = new Button("Save List to binary file");
		btnSave.setStyle("-fx-background-color: Grey;" + "-fx-text-fill: white;"+ "-fx-border-color:black;"  );
		Button btnSelectBinFile = new Button("Add Books from bin file");
		btnSelectBinFile.setStyle("-fx-background-color: Grey;" + "-fx-text-fill: white;" + "-fx-border-color:black;" );
		FileChooser fileChooser = new FileChooser();
		FileChooser fileChooser2 = new FileChooser();
		Button btnQuit = new Button("Quit");
		btnQuit.setStyle("-fx-background-color: Grey;" + "-fx-text-fill: white;" + "-fx-border-color:black;"  );
		Button btnClearOutput = new Button("Clear TextBox");
		btnClearOutput.setStyle("-fx-background-color: Grey;" + "-fx-text-fill: white;" + "-fx-border-color:black;" );

		// This is the Grid Layout for the gui window
		// The first number in the layout is the width the object appears and the 2nd
		// number is the height it appears at
		gridPane1.add(txtHeader, 0, 0);
		gridPane1.add(btnSelectBinFile, 0, 1);
		gridPane1.add(btnAddBook, 0, 2);
		gridPane1.add(tfBookID, 1, 2);
		gridPane1.add(tfBookAuthor, 2, 2);
		gridPane1.add(tfBookPages, 3, 2);
		gridPane1.add(tfBookSize, 4, 2);
		gridPane1.add(tfBookgenre, 5, 2);
		gridPane1.add(btnRemoveBook, 0, 3);
		gridPane1.add(tfDelBook, 1, 3);

		gridPane1.add(btnSearchBook, 0, 4);
		gridPane1.add(tfSearchBook, 1, 4);

		gridPane1.add(btnSearchBookAuthor, 0, 5);
		gridPane1.add(tfSearchBookAuthor, 1, 5);

		gridPane1.add(btnSearchBookgenre, 0, 6);
		gridPane1.add(tfSearchBookgenre, 1, 6);

		gridPane1.add(btnShowTotal, 0, 7);
		gridPane1.add(btnShowBookList, 1, 7,2,1);
		gridPane1.add(btnSave, 0, 8, 2,1);
		gridPane1.add(btnQuit, 0, 9);
		gridPane1.add(taMyOutput, 0, 10, 5, 1);
		gridPane1.add(btnClearOutput, 5, 10,2,1);
		// Buttons for implementing user databases to app
		btnSelectBinFile.setOnAction(e -> {
			// Set initial directory to current directory
			fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
			// Choose file
			File BookBinFile = fileChooser.showOpenDialog(primaryStage);
			// Read Books from binary file
			bm = bm.readBookManagerObjectFromFile(BookBinFile.getAbsolutePath());
			// Display message to user in TextArea node taMyOutput
			if (bm == null) {
				taMyOutput.setText("ERROR: File path does not exist \n" + BookBinFile.getAbsolutePath());
			} else {
				taMyOutput.setText("Books loaded successfully from: \n" + BookBinFile.getAbsolutePath());
			}
		});

		// Book Add button
		btnAddBook.setOnAction(e -> {
			// Try Catch statment for Errors
			try {
				if (Book.isValid(tfBookID.getText(), tfBookAuthor.getText(), Integer.parseInt(tfBookPages.getText()),
						tfBookgenre.getText(), Integer.parseInt(tfBookSize.getText())) == false) {
					taMyOutput.setText("Please enter valid Book details \n");
				} else {
					// Create A new Book with information in text fields
					// Add A Book to Book list
					if (bm.addBookToList(tfBookID.getText(), tfBookAuthor.getText(),
							Integer.parseInt(tfBookPages.getText()), tfBookgenre.getText(),
							Integer.parseInt(tfBookSize.getText()))) {
						taMyOutput.setText("Book added to list successfully \n");
					} else {
						taMyOutput.setText("Book not added to list \n");
					}
					// To clear after use
					tfBookAuthor.clear();
					tfBookgenre.clear();
					tfBookPages.clear();
					tfBookSize.clear();
					tfBookID.clear();
				}
				// catch statment for errors
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
				taMyOutput.setText("Please enter Values missing");
			}

		});

		// Remove Book button action
		btnRemoveBook.setOnAction(e -> {

			if (tfDelBook.getText().trim().equals("")) { // If text field is empty
				taMyOutput.setText("Please enter the Book ID you want to remove");
			} else {
				boolean status;
				status = bm.removeBookFromList(tfDelBook.getText());
				if (status == true) {
					taMyOutput.setText(tfDelBook.getText() + " has been removed from the Book list!");
					tfDelBook.clear();
				} else {
					taMyOutput.setText("Book " + tfDelBook.getText() + " not found\n");
					taMyOutput.appendText("No Book removed!");
					tfDelBook.clear();
				}
			}

		});
		// incomplete search function for database

		btnSearchBook.setOnAction(e -> {
			if (tfSearchBook.getText().trim().equals("")) {// If text field is empty
				taMyOutput.setText("Please enter the Book ID you want to Find.");
			} else {
				boolean status;
				status = bm.findBookByID(tfSearchBook.getText()) != null;
				if (status == true) {
					for (Book Book : bm.getBooksList()) {
						taMyOutput.appendText("Book ID: " + Book.getbookId() + "\nBook Author: " + Book.getAuthor()
								+ "\nBook Page length: " + Book.getPages() + "\nBook Genre " + Book.getgenre()
								+ "\nBook Size " + Book.getSize() + "\n\n");
					}
				} else {
					taMyOutput
							.setText("Book With ID: " + tfSearchBook.getText() + " could not be found on the list.\n");
					tfSearchBook.clear();
				}
			}
		}); // end of method
		btnSearchBookAuthor.setOnAction(e -> {
			if (tfSearchBookAuthor.getText().trim().equals("")) {// If text field is empty
				taMyOutput.setText("Please enter the Book ID you want to Find.");
			} else {
				boolean status;
				status = bm.findBooksByName(tfSearchBookAuthor.getText()) != null;
				if (status == true) {
					for (Book Book : bm.getBooksList()) {
						taMyOutput.appendText("Book ID: " + Book.getbookId() + "\nBook Author: " + Book.getAuthor()
								+ "\nBook Page length: " + Book.getPages() + "\nBook Genre " + Book.getgenre()
								+ "\nBook Size " + Book.getSize() + "\n\n");
					}
				} else {
					taMyOutput.setText(
							"Book With ID: " + tfSearchBookAuthor.getText() + " could not be found on the list.\n");
					tfSearchBookAuthor.clear();
				}
			}
		}); // end of method

		btnSearchBookgenre.setOnAction(e -> {
			if (tfSearchBookgenre.getText().trim().equals("")) {// If text field is empty
				taMyOutput.setText("Please enter the Book ID you want to Find.");
			} else {
				boolean status;
				status = bm.findBooksByGenre(tfSearchBookgenre.getText()) != null;
				if (status == true) {
					for (Book Book : bm.getBooksList()) {
						taMyOutput.appendText("Book ID: " + Book.getbookId() + "\nBook Author: " + Book.getAuthor()
								+ "\nBook Page length: " + Book.getPages() + "\nBook Genre " + Book.getgenre()
								+ "\nBook Size " + Book.getSize() + "\n\n");
					}
				} else {
					taMyOutput.setText(
							"Book With genre: " + tfSearchBookgenre.getText() + " could not be found on the list.\n");
					tfSearchBookgenre.clear();
				}
			}
		}); // end of method
		btnClearOutput.setOnAction(e -> {
			taMyOutput.clear();
		});

		// Show total number of Books
		btnShowTotal.setOnAction(e -> {
			taMyOutput.setText("Current Total Books: " + bm.getBooksList().size());
		});
		// Show list of Books
		btnShowBookList.setOnAction(e -> {
			// If Book list is empty
			if (bm.getBooksList().size() == 0) {
				taMyOutput.setText("Book list is empty");
			} else {
				taMyOutput.setText("Book ID,First Name,Age" + "\n");
				taMyOutput.appendText("---------------------------------------------" + "\n");
				for (Book Book : bm.getBooksList()) {
					taMyOutput.appendText(Book.getbookId() + "," + Book.getAuthor() + "," + Book.getPages() + ","
							+ Book.getgenre() + "," + Book.getSize() + "/n");
				}
			}
		});

		// Add action to button to save Books to binary file (e.g. Books.ser)
		btnSave.setOnAction(e -> {
			// Set initial directory to current directory
			fileChooser2.setInitialDirectory(new File(System.getProperty("user.dir")));
			// Opening a dialog box to choose file to save to (e.g. Books.ser)
			bm.writeBookManagerObjectToFile(fileChooser2.showSaveDialog(primaryStage).getAbsolutePath());
		});

		// Quit button added
		btnQuit.setOnAction(e -> Platform.exit());

		// Create scene and add the root node i.e. the gridpane
		Scene scene1 = new Scene(gridPane1, 600, 450);
		// Preparing the Stage (i.e. the container of any JavaFX application)
		// Set Stage Title
		// Find number of command line arguments supplied
		noOfCmdLineArgs = getParameters().getRaw().size();
		if (noOfCmdLineArgs > 0) {
			cmdLineArgs = getParameters().getRaw().toString();
			// Remove unwanted characters ([ and ] and ,)from string
			cmdLineArgs = cmdLineArgs.replaceAll("\\[|\\]|\\,", "");
			primaryStage.setTitle(cmdLineArgs);
		} else {
			primaryStage.setTitle("BookManager Application");
		}
		// Setting the scene on which this stage will show
		primaryStage.setScene(scene1);
		// Display the stage
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

} // End Main
