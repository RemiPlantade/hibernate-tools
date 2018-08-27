package fr.aboucorp.conf.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.management.BadAttributeValueExpException;

import org.hibernate.tool.hbm2x.pojo.EntityPOJOClass;

import api_conf.conf.model.ApiConf;
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

	private LinkedList<SimpleEntry<String,VBox>> pages;

	private List<AbstractController> controllers = new ArrayList<>();

	public Boolean hideSSL = false;

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
			pages.add(new SimpleEntry<String, VBox>("home",home));
			HomeController homectrl =  loader.getController();
			homectrl.setMainCtrl(this);

			loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("fxml/credentials.fxml"));
			VBox credential = (VBox) loader.load();
			pages.add(new SimpleEntry<String, VBox>("credential",credential));
			CredentialsController credentctrl = loader.getController();
			credentctrl.setMainCtrl(this);
			controllers.add(credentctrl);

			loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("fxml/web.fxml"));
			VBox web = (VBox) loader.load();
			pages.add(new SimpleEntry<String, VBox>("web",web));
			WebController webctrl = loader.getController();
			webctrl.setMainCtrl(this);
			controllers.add(webctrl);

			loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("fxml/https_quota.fxml"));
			VBox https_quota = (VBox) loader.load();
			pages.add(new SimpleEntry<String, VBox>("https_quota",https_quota));
			HTTPSQuotaController httpsctrl = loader.getController();
			httpsctrl.setMainCtrl(this);
			controllers.add(httpsctrl);

			loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("fxml/ssl.fxml"));
			VBox ssl = (VBox) loader.load();
			pages.add(new SimpleEntry<String, VBox>("ssl",ssl));
			SSLController sslctrl = loader.getController();
			sslctrl.setMainCtrl(this);
			controllers.add(sslctrl);

			loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("fxml/database.fxml"));
			VBox database = (VBox) loader.load();
			pages.add(new SimpleEntry<String, VBox>("database",database));
			DatabaseController databasectrl = loader.getController();
			databasectrl.setMainCtrl(this);
			controllers.add(databasectrl);

			contentPane.getChildren().add(home);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onNextPage() {
		contentPane.getChildren().clear();
		SimpleEntry<String, VBox> actual = pages.removeFirst();
		pages.addLast(actual);
		if(hideSSL && pages.getFirst().getKey().equals("ssl")) {
			SimpleEntry<String, VBox> ssl = pages.removeFirst();
			pages.addLast(ssl);
		}
		contentPane.getChildren().add(pages.getFirst().getValue());
		;
	}

	public void onPreviousPage() {
		contentPane.getChildren().clear();
		SimpleEntry<String, VBox> future = pages.removeLast();
		pages.addFirst(future);
		if(hideSSL && pages.getFirst().getKey().equals("ssl")) {
			SimpleEntry<String, VBox> ssl = pages.removeLast();
			pages.addFirst(ssl);
		}
		contentPane.getChildren().add(pages.getFirst().getValue());
	}

	public void updateAllEntities() throws SQLException{
		for (AbstractController absCtrl : controllers) {
			for (ApiConf conf : absCtrl.getAllApiConf()) {
				absCtrl.getConfDao().updateEntity(conf);
			}
		}
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

	public Stage getPrimaryStage() {
		return primaryStage;
	}


}