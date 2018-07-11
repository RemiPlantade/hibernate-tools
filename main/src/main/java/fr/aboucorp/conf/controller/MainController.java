package fr.aboucorp.conf.controller;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.management.BadAttributeValueExpException;

import org.hibernate.tool.hbm2x.pojo.EntityPOJOClass;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController extends Application implements Initializable{

	

	public static final CountDownLatch latch = new CountDownLatch(1);
	public static final CountDownLatch latch1 = new CountDownLatch(1);
	
	public static MainController controller = null;
	
	public static AtomicBoolean running = new AtomicBoolean(false);
	
	public AtomicInteger pageIdx = new AtomicInteger(0);
	
	private List<EntityPOJOClass> pojos;

	private Stage primaryStage;
	
	private BorderPane rootLayout = null;
	
	private LinkedList<VBox> pages;
		
	@FXML
	private FlowPane contentPane;

	public MainController() {
		pages = new LinkedList<>();
		setController(this);
		running.set(true);
	}

	public static MainController waitForJavaFXLauncher() {
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return controller;
	}

	public static void setController(MainController control) {
		controller = control;

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
			loader.setLocation(getClass().getClassLoader().getResource("fxml/main_window.fxml"));
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
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		latch.countDown();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("fxml/home.fxml"));
			VBox home = (VBox) loader.load();
			pages.add(home);
			HomeController homectrl =  loader.getController();
			homectrl.setMainCtrl(this);
			
			loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("fxml/credentials.fxml"));
			VBox credential = (VBox) loader.load();
			pages.add(credential);
			CredentialsController credentctrl = loader.getController();
			credentctrl.setMainCtrl(this);
			
			loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("fxml/web.fxml"));
			VBox web = (VBox) loader.load();
			pages.add(web);
			WebController webctrl = loader.getController();
			webctrl.setMainCtrl(this);
			
			loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("fxml/https_quota.fxml"));
			VBox https_quota = (VBox) loader.load();
			pages.add(https_quota);
			HTTPSQuotaController httpsctrl = loader.getController();
			httpsctrl.setMainCtrl(this);
			
			loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("fxml/ssl.fxml"));
			VBox ssl = (VBox) loader.load();
			pages.add(ssl);
			SSLController sslctrl = loader.getController();
			sslctrl.setMainCtrl(this);
			
			loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("fxml/database.fxml"));
			VBox database = (VBox) loader.load();
			pages.add(database);
			DatabaseController databasectrl = loader.getController();
			databasectrl.setMainCtrl(this);
			
			contentPane.getChildren().add(home);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onNextPage() {
		contentPane.getChildren().clear();
		contentPane.getChildren().add(pages.get(pageIdx.addAndGet(1)));
	}
	
	public void onPreviousPage() {
		contentPane.getChildren().clear();
		contentPane.getChildren().add(pages.get(pageIdx.addAndGet(-1)));
	}
	
	public void updateAllEntities() throws BadAttributeValueExpException{
//		POJOClassCellFactory factory = ((POJOClassCellFactory)pojo_list_view.getCellFactory());
//		
//		for (POJOClassCell cell : factory.getCells()) {
//			if(cell.getItem() != null && cell.getItem() instanceof EntityPOJOClass) {
//				try {
//					cell.getRendererController().updatePojoFromFields();
//				}catch(Exception e){
//					throw new BadAttributeValueExpException(e.getMessage());
//				}
//			}
//		}
	}
}
