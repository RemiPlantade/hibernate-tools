package org.hibernate.tool.conf;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.management.BadAttributeValueExpException;

import org.hibernate.tool.hbm2x.pojo.EntityPOJOClass;
import org.hibernate.tool.hbm2x.pojo.POJOClass;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class JavaFXConfigurator extends Application implements Initializable{

	@FXML
	private ListView<EntityPOJOClass> pojo_list_view;
	@FXML 
	private Button btn_launch_gen;

	public static final CountDownLatch latch = new CountDownLatch(1);
	public static final CountDownLatch latch1 = new CountDownLatch(1);
	
	public static JavaFXConfigurator startUpTest = null;
	
	public static AtomicBoolean running = new AtomicBoolean(false);
	
	private List<EntityPOJOClass> pojos;

	private Stage primaryStage;
	private BorderPane rootLayout;

	public JavaFXConfigurator() {
		setJavaFXLauncher(this);
		running.set(true);
	}

	public static JavaFXConfigurator waitForJavaFXLauncher() {
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return startUpTest;
	}

	public static void setJavaFXLauncher(JavaFXConfigurator startUpTest0) {
		startUpTest = startUpTest0;

	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		initRootLayout();
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("fxml/MainWindow.fxml"));
			rootLayout = (BorderPane) loader.load();
			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setPOJOs(List<EntityPOJOClass> pojos) {
		this.pojos = pojos;
		setListViewFactory(pojos);
		ObservableList<EntityPOJOClass> items = FXCollections.observableArrayList (pojos);
		pojo_list_view.setItems(items);
		pojo_list_view.refresh();

	}

	private void setListViewFactory(List<EntityPOJOClass> pojos) {
		pojo_list_view.setCellFactory(new POJOClassCellFactory(pojos));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		latch.countDown();
		btn_launch_gen.setOnMouseClicked((event) -> {
			 try {
				updateAllEntities();
				for (EntityPOJOClass entityPOJOClass : pojos) {
					System.out.println("Linked netities.size()" + entityPOJOClass.getLinkedEntities().size());
					System.out.println("Linker netities.size()" + entityPOJOClass.getLinkerEntities().size());
				}
				latch1.countDown();
			} catch (BadAttributeValueExpException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error dialog");
				alert.setHeaderText("Some problem occurs");
				alert.setContentText("You must set all linked entities for each linker.");
				alert.showAndWait();
			}
			
		});
	}
	
	public void updateAllEntities() throws BadAttributeValueExpException{
		POJOClassCellFactory factory = ((POJOClassCellFactory)pojo_list_view.getCellFactory());
		
		for (POJOClassCell cell : factory.getCells()) {
			if(cell.getItem() != null && cell.getItem() instanceof EntityPOJOClass) {
				try {
					cell.getRendererController().updatePojoFromFields();
				}catch(Exception e){
					throw new BadAttributeValueExpException(e.getMessage());
				}
			}
		}
	}
}
