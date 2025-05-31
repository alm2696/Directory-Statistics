package mod07_OYO;

import javafx.application.Application;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * DirectoryStatisticsApp is an application that allows the user to
 * select a directory and identifies the smallest and largest files. 
 * The file sizes and relative paths are displayed in the GUI.
 * 
 * @author angel
 */
public class DirectoryStatisticsApp extends Application {
	private Label smallestFileLabel = new Label("Smallest File:");
	private Label largestFileLabel = new Label("Largest File:");
	private File smallestFile = null;
	private File largestFile = null;

	/**
	 * Sets up the GUI and event handling for the application.
	 * @param primaryStage The main window for this application.
	 */
	@Override
	public void start(Stage primaryStage) {
		// Button to trigger directory selection
		Button selectDirButton = new Button("Select Directory");
		selectDirButton.setOnAction(e -> chooseDirectory(primaryStage));

		// Layout setup
		VBox layout = new VBox(10, selectDirButton, smallestFileLabel, largestFileLabel);
		Scene scene = new Scene(layout, 400, 200);

		primaryStage.setTitle("Directory Statistics");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Opens a DirectoryChooser dialog to allow the user to select a directory.
	 * Starts file traversal to find smallest and largest files if a directory is selected.
	 * @param stage The main window stage to show the dialog.
	 */
	private void chooseDirectory(Stage stage) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(stage);

		if (selectedDirectory != null && selectedDirectory.isDirectory()) {
			// Find smallest and largest files in the selected directory
			findSmallestAndLargestFiles(selectedDirectory);
			// Update labels with file information
			updateLabels(selectedDirectory);
		} else {
			// Handle invalid or no directory selection
			smallestFileLabel.setText("Invalid directory selection.");
			largestFileLabel.setText("");
		}
	}

	/**
	 * Initializes smallest and largest file references and traverses the directory tree.
	 * @param dir The root directory to start the traversal.
	 */
	private void findSmallestAndLargestFiles(File dir) {
		smallestFile = null;
		largestFile = null;
		traverseDirectory(dir);
	}

	/**
	 * Recursively traverses the given directory to find the smallest and largest 
	 * files. Updates smallestFile and largestFile based on file sizes.
	 * @param dir The directory to traverse.
	 */
	private void traverseDirectory(File dir) {
		File[] files = dir.listFiles(); // List all files and directories in the current directory
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					// Recursive call for subdirectories
					traverseDirectory(file);
				} else {
					// Check and update smallest and largest files
					if (smallestFile == null || file.length() < smallestFile.length()) {
						smallestFile = file;
					}
					if (largestFile == null || file.length() > largestFile.length()) {
						largestFile = file;
					}
				}
			}
		}
	}

	/**
	 * Updates the GUI labels with relative paths and sizes of the smallest and largest files.
	 * @param baseDirectory The directory chosen by the user to calculate relative paths.
	 */
	private void updateLabels(File baseDirectory) {
		if (smallestFile != null && largestFile != null) {
			Path basePath = Paths.get(baseDirectory.getPath());
			// Compute relative paths
			String smallestRelativePath = basePath.relativize(smallestFile.toPath()).toString();
			String largestRelativePath = basePath.relativize(largestFile.toPath()).toString();

			// Display results on GUI
			smallestFileLabel.setText("Smallest File: " + smallestRelativePath + 
					" (" + smallestFile.length() + " bytes)");
			largestFileLabel.setText("Largest File: " + largestRelativePath + 
					" (" + largestFile.length() + " bytes)");
		} else {
			// Handle case where no files are found in the directory
			smallestFileLabel.setText("No files found.");
			largestFileLabel.setText("");
		}
	}

	/**
	 * Main method to launch the application.
	 * @param args The command-line arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
