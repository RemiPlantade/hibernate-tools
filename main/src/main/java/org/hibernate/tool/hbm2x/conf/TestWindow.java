package org.hibernate.tool.hbm2x.conf;

import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TestWindow extends Application {

	@FXML
	private static ListView<String> entity_list;
	private Stage primaryStage;
    private BorderPane rootLayout;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");
        initRootLayout(); 
	}
	
	 /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("fxml/TestView.fxml"));
            rootLayout = (BorderPane) loader.load();
            entity_list = new ListView<>();
            rootLayout.setCenter(entity_list);
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Initialyze listview
     */
    public static void setListView(List<String> list) {
    	ObservableList<String> items = FXCollections.observableArrayList (list);
    	entity_list = new ListView<>();
    	entity_list.setItems(items);
    	entity_list.refresh();
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
