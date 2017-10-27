package main.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.controller.OfflineController;

public class Offline extends Application {

	private Stage stage;
	private AnchorPane rootLayout;

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		this.stage.setTitle("Offline");
		this.stage.setResizable(false);
		initLayout();
	}

	/**
	 * Initializes the root layout.
	 */
	public void initLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Offline.fxml"));
			AnchorPane rootLayout = (AnchorPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);

			// these two of them return the same stage
			// Swap screen
			((OfflineController) loader.getController()).setStage(stage);
			stage.setScene(scene);
			stage.setTitle("Offline Image Analysis");
			// "file:C://Users//madla//OneDrive//Documents//GitHub//Analize//src//main//view//icon2.jpg"
			Image icon = new Image("file:C://Users//gatil//OneDrive//Dokumentumok//GitHub//Analize//src//main//view//icon2.jpg");
            stage.getIcons().add(icon);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return stage;
	}
}
