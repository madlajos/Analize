package main.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Online extends Application {

	 private Stage primaryStage;
	    private AnchorPane rootLayout;

	    @Override
	    public void start(Stage primaryStage) {
	        this.primaryStage = primaryStage;
	        this.primaryStage.setTitle("Online");
	        this.primaryStage.setResizable(false);
	        initLayout();
	    }

	    /**
	     * Initializes the root layout.
	     */
	    public void initLayout() {
	        try {
	            // Load root layout from fxml file.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Online.class.getResource("Online.fxml"));
	            rootLayout = (AnchorPane) loader.load();

	            // Show the scene containing the root layout.
	            Scene scene = new Scene(rootLayout);
	            primaryStage.setScene(scene);
	            primaryStage.show();
	            // "file:C://Users//madla//OneDrive//Documents//GitHub//Analize//src//main//view//icon2.jpg"
	            Image icon = new Image("file:C://Users//gatil//OneDrive//Dokumentumok//GitHub//Analize//src//main//view//icon2.jpg");
	            primaryStage.getIcons().add(icon);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    /**
	     * Returns the main stage.
	     * @return
	     */
	    public Stage getPrimaryStage() {
	        return primaryStage;
	    }
	}
