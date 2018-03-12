package org.hibernate.tool.conf;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import org.hibernate.tool.hbm2x.pojo.POJOClass;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class JavaFXConfigurator extends Application implements Initializable{

	@FXML
	private ListView<POJOClass> pojo_list;
	@FXML 
	private Button btn_launch_gen;

	public static final CountDownLatch latch = new CountDownLatch(1);
	public static final CountDownLatch latch1 = new CountDownLatch(1);
	public static JavaFXConfigurator startUpTest = null;
	public static AtomicBoolean running = new AtomicBoolean(false);
	private List<POJOClass> pojos;


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

	public void setPOJOs(List<POJOClass> pojos) {
		this.pojos = pojos;
		setListViewFactory(pojos);
		ObservableList<POJOClass> items = FXCollections.observableArrayList (pojos);
		System.out.println("Set Pojos to ListView");
		pojo_list.setItems(items);
		pojo_list.refresh();

	}

	private void setListViewFactory(List<POJOClass> pojos) {
		System.out.println("Set POJO factory");
		pojo_list.setCellFactory(new POJOClassCellFactory(pojos));

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Initialize JavaFXConfigurator");
		latch.countDown();
		btn_launch_gen.setOnMouseClicked((event) -> {
			pojo_list.setItems( FXCollections.observableArrayList (pojos));
			latch1.countDown();
		});

		//pojo_list.setCellFactory(new POJOClassCellFactory());
		//		pojo_list.setCellFactory(new Callback<ListView<POJOClass>, ListCell<POJOClass>>(){
		//
		//			@Override
		//			public ListCell<POJOClass> call(ListView<POJOClass> p) {
		//
		//				ListCell<POJOClass> cell = new ListCell<POJOClass>(){
		//
		//					@Override
		//					protected void updateItem(POJOClass t, boolean bln) {
		//						super.updateItem(t, bln);
		//						if (t != null) {
		//							setText(t.getDeclarationName());
		//						}
		//					}
		//
		//				};
		//
		//				return cell;
		//			}
		//		});		
	}
}
