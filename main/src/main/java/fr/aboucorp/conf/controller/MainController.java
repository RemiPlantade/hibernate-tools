package fr.aboucorp.conf.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.hibernate.tool.hbm2x.pojo.EntityPOJOClass;

import api_conf.conf.model.ApiConf;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainController extends Application implements Initializable{

	public static final CountDownLatch latch = new CountDownLatch(1);
	public static final CountDownLatch latch1 = new CountDownLatch(1);
	public static final CountDownLatch latch3 = new CountDownLatch(1);

	public static MainController controller = null;

	public static AtomicBoolean running = new AtomicBoolean(false);

	public static AtomicBoolean waitForGen = new AtomicBoolean(false);

	public AtomicInteger pageIdx = new AtomicInteger(0);

	private Stage primaryStage;

	private BorderPane rootLayout = null;

	private LinkedList<SimpleEntry<AbstractController,VBox>> pages;

	private Properties props = new Properties();;

	@FXML
	private FlowPane contentPane;
	@FXML
	private VBox bottom_pane;
	@FXML
	private Button btn_prev;
	@FXML
	private Button btn_next;
	@FXML
	private Label lbl_step;
	@FXML
	private Rectangle rect_blue;
	@FXML
	private Rectangle rect_white;
	@FXML
	private FlowPane nav_button_bar;

	private int step_number = 0;

	public MainController() {
		pages = new LinkedList<>();
		setController(this);
		running.set(true);
	}

	public static MainController waitForBasicConfiguration() {
		try {
			System.out.println("Yolo latch wait");
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return controller;
	}

	public static MainController waitForEntityConfiguration() {
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

	public void injectPOJOs(List<EntityPOJOClass> pojos) {
		EntitiesController entitiesCtrl = (EntitiesController) getControllerByID("entities");
		entitiesCtrl.injectPojos(pojos);
		onNextPage();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Yolo latch countdwon initialize");
		latch.countDown();
		try {
			loadLayouts();
			displayBottomPane(false);
			btn_prev.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					onPreviousPage();
				}
			});

			btn_next.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent event) {
					onNextPage();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updateDatabaseConf() {
		System.out.println("On updateDatabaseConf");
		for (java.util.Iterator<SimpleEntry<AbstractController, VBox>> iter = pages.iterator(); iter.hasNext();) {
			SimpleEntry<AbstractController, VBox> entry = iter.next();
			if(entry.getKey().getId().equals("database")) {
				List<ApiConf> confList = entry.getKey().getAllApiConf();
				if(confList != null) {
					for (ApiConf conf : confList) {
						if(conf != null && conf.getParamKey() != null) {
							if(conf.getParamKey().startsWith("hibernate.")){
								props.setProperty(conf.getParamKey(), conf.getParamValue());
							}
						}
					}
				}
			}
		}
	}

	public void validate() {
		System.out.println("On validate");
		try {
			updateAllEntities();
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Erreur lors de la mise à jour de la configuration");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}	
	}

	public void loadLayouts() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("fxml/home.fxml"));
		VBox home = (VBox) loader.load();
		HomeController homectrl =  loader.getController();
		homectrl.setId("home");
		pages.add(new SimpleEntry<>(homectrl,home));

		homectrl.setMainCtrl(this);

		loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("fxml/credentials.fxml"));
		VBox credential = (VBox) loader.load();
		CredentialsController credentctrl = loader.getController();
		pages.add(new SimpleEntry<>(credentctrl,credential));
		credentctrl.setId("credential");
		credentctrl.setMainCtrl(this);

		loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("fxml/web.fxml"));
		VBox web = (VBox) loader.load();
		WebController webctrl = loader.getController();
		pages.add(new SimpleEntry<>(webctrl,web));
		webctrl.setId("web");
		webctrl.setMainCtrl(this);

		loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("fxml/https_quota.fxml"));
		VBox https_quota = (VBox) loader.load();
		HTTPSQuotaController httpsctrl = loader.getController();
		pages.add(new SimpleEntry<>(httpsctrl,https_quota));
		httpsctrl.setId("https_quota");
		httpsctrl.setMainCtrl(this);

		loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("fxml/ssl.fxml"));
		VBox ssl = (VBox) loader.load();
		SSLController sslctrl = loader.getController();
		pages.add(new SimpleEntry<>(sslctrl,ssl));
		sslctrl.setId("ssl");
		sslctrl.setMainCtrl(this);

		loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("fxml/database.fxml"));
		VBox database = (VBox) loader.load();
		DatabaseController databasectrl = loader.getController();
		pages.add(new SimpleEntry<>(databasectrl,database));
		databasectrl.setId("database");
		databasectrl.setMainCtrl(this);

		loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("fxml/entities.fxml"));
		VBox entities;
		entities = (VBox) loader.load();
		EntitiesController entitiesctrl = loader.getController();
		pages.add(new SimpleEntry<>(entitiesctrl,entities));
		entitiesctrl.setId("entities");
		entitiesctrl.setMainCtrl(this);

		contentPane.getChildren().add(home);
	}

	public AbstractController getControllerByID(String id) {
		for (java.util.Iterator<SimpleEntry<AbstractController, VBox>> iter = pages.iterator(); iter.hasNext();) {
			SimpleEntry<AbstractController, VBox> entry = iter.next();
			if(entry.getKey().getId().equals(id)) {
				return entry.getKey();
			}
		}
		return null;
	}

	public void onNextPage()  {
		SimpleEntry<AbstractController,VBox> actual = pages.getFirst();
		try {
			actual.getKey().checkInfo();
			pages.removeFirst();
			step_number++;
			contentPane.getChildren().clear();
			pages.addLast(actual);
			switch(step_number) {
			case 5 :
				btn_next.setText("Next");
				updateDatabaseConf();	
				latch1.countDown();
				break;
			case 6:
				btn_next.setText("Finish");
				nav_button_bar.getChildren().remove(btn_prev);
				validate();
				latch3.countDown();
				Platform.exit();
				break;
			default:
				btn_next.setText("Next");
				break;
			}
			contentPane.getChildren().add(pages.getFirst().getValue());
			refreshStepState();
		}catch(IllegalArgumentException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Erreur de saisie");
			alert.setContentText(e.getMessage());
			alert.showAndWait();

		}catch(Exception e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Unexpected error");
			alert.setContentText(e1.getMessage());
			alert.showAndWait();
		}
	}

	public void onPreviousPage() {
		step_number--;
		btn_next.setText("Next");
		contentPane.getChildren().clear();
		SimpleEntry<AbstractController,VBox> future = pages.removeLast();
		pages.addFirst(future);
		switch(step_number) {
		case 0 :
			bottom_pane.setVisible(false);
			break;
		case 5:
			btn_next.setText("Next");
			break;
		default:
			btn_next.setText("Next");
			break;
		}
		contentPane.getChildren().add(pages.getFirst().getValue());
		refreshStepState();
	}

	public void refreshStepState() {
		lbl_step.setText(step_number + " / 6");
		rect_blue.setWidth(40*step_number);
		rect_white.setWidth(240-(40*step_number));
	}

	public void updateAllEntities() throws SQLException{
		for (java.util.Iterator<SimpleEntry<AbstractController, VBox>> iter = pages.iterator(); iter.hasNext();) {
			SimpleEntry<AbstractController, VBox> entry = iter.next();
			List<ApiConf> confList = entry.getKey().getAllApiConf();
			if(confList != null) {
				for (ApiConf conf : confList) {
					if(conf != null && conf.getParamKey() != null) {
						entry.getKey().getConfDao().updateEntity(conf);
					}
				}
			}
		}
	}

	public void displayBottomPane(boolean displayed) {
		bottom_pane.setVisible(displayed);
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public Properties getProperties() {
		// TODO Auto-generated method stub
		return props;
	}

}