package sample;

import javafx.stage.FileChooser;

import java.io.File;

public class FileChooserHelper {

    private FileChooser fileChooser = new FileChooser();
    private String title;
    private String initialDirectory;

    public FileChooserHelper(String title, String initialDirectoryURL) {
        this.title = title;
        this.initialDirectory = initialDirectoryURL;

        configuringFileChooser();
    }

    public FileChooser getFileChooser() {
        return fileChooser;
    }

    private void configuringFileChooser() {
        // Set title for FileChooser
        fileChooser.setTitle(title);

        // Set Initial Directory
        fileChooser.setInitialDirectory(new File(System.getProperty(initialDirectory)));

        // Add Extension Filters
        fileChooser.getExtensionFilters().addAll(//
                new FileChooser.ExtensionFilter("MP4", "*.mp4"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
    }
}
