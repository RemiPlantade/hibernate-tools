package fr.aboucorp.conf.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.tool.hbm2x.pojo.EntityPOJOClass;

import api_conf.conf.exception.BadURLException;
import api_conf.conf.model.ApiConf;
import fr.aboucorp.conf.PropertyBindingException;
import fr.aboucorp.conf.ihm.POJOClassCell;
import fr.aboucorp.conf.ihm.POJOClassCellFactory;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanStringProperty;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import javafx.scene.control.Alert.AlertType;

public class EntitiesController extends AbstractController implements Initializable{

	@FXML
	private ListView<EntityPOJOClass> list_view_entities;

	@FXML
	private ListView<ComboBox<EntityPOJOClass>> list_view_foreign_entities;

	private List<EntityPOJOClass> pojos; 

	private Map<EntityPOJOClass,List<ComboBox<EntityPOJOClass>>> comboList;


	private ApiConf baseUrl;
	private ApiConf httpPort;
	private ApiConf httpsPort;
	private ApiConf httpsEnabled;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize();
		comboList = new HashMap<>();
		list_view_entities.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EntityPOJOClass>() {
			@Override
			public void changed(ObservableValue<? extends EntityPOJOClass> observable, EntityPOJOClass oldValue, EntityPOJOClass newValue) {
				refreshComboListView(comboList.get(list_view_entities.getSelectionModel().getSelectedItem()));
			}
		});
	}

	@Override
	public void checkInfo() {

	}

	@Override
	public List<ApiConf> getAllApiConf() {
		return null;
	}


	@Override
	public void getProps() throws SQLException {
		baseUrl = confDao.getEntityFromParamKey("api.base.url");
		httpPort =  confDao.getEntityFromParamKey("api.port.http");
		httpsPort =  confDao.getEntityFromParamKey("api.port.https");
		httpsEnabled = confDao.getEntityFromParamKey("server.ssl.enabled");
	}

	@Override
	public void updateConf() {

	}

	public void removeForeignEntity(int steps, EntityPOJOClass owner) {
		List<ComboBox<EntityPOJOClass>> newContent = comboList.get(owner);
		for(int i = 0 ; i < steps ; i++) {
			newContent.remove(newContent.size()-1);
		}
		refreshComboListView(newContent);
	}


	public void addForeignEntity(int steps, EntityPOJOClass owner) {
		List<ComboBox<EntityPOJOClass>> newContent = comboList.get(owner);
		System.out.println("Size of list before : " + newContent.size());

		for(int i = 0 ; i < steps ; i++) {
			ComboBox<EntityPOJOClass> combo = new ComboBox<>();
			combo.setItems(FXCollections.observableArrayList(pojos));
			combo.valueProperty().addListener(new POJOComboChangeListener(owner));
			newContent.add(combo);
		}
		refreshComboListView(newContent);
	}

	public void refreshComboListView(List<ComboBox<EntityPOJOClass>> newContent) {
		list_view_foreign_entities.getItems().clear();
		list_view_foreign_entities.setItems(FXCollections.observableArrayList(newContent));
	}

	public void injectPojos(List<EntityPOJOClass> pojos) {
		this.pojos = pojos;
		for (EntityPOJOClass entityPOJOClass : pojos) {
			comboList.put(entityPOJOClass, new ArrayList<>());
		}
		ObservableList<EntityPOJOClass> items = FXCollections.observableArrayList (pojos);
		list_view_entities.setItems(items);
		list_view_entities.setCellFactory(new POJOClassCellFactory(this));
	}

	public void selectItemInList(int index) {
		list_view_entities.getSelectionModel().select(index);
		list_view_entities.getFocusModel().focus(index);
	}

	public ApiConf getBaseUrl() {
		return baseUrl;
	}
	public ApiConf getHttpPort() {
		return httpPort;
	}
	public ApiConf getHttpsPort() {
		return httpsPort;
	}
	public ApiConf getHttpsEnabled() {
		return httpsEnabled;
	}

	class POJOComboChangeListener implements ChangeListener<EntityPOJOClass> {
		private EntityPOJOClass owner;
		public POJOComboChangeListener(EntityPOJOClass owner) {
			super();
			this.owner = owner;
		}

		@Override
		public void changed(ObservableValue<? extends EntityPOJOClass> observable, EntityPOJOClass oldValue,
				EntityPOJOClass newValue) {
			System.out.println("In POJOComboChanListener !");
			List<EntityPOJOClass> linked = owner.getLinkedEntities() !=null ?
					owner.getLinkedEntities() : new ArrayList<>();
					if(oldValue != null && !oldValue.equals(newValue)) {
						System.out.println("Removing oldValue");
						linked.remove(oldValue);
					}
					System.out.println("Adding newValue");
					if(newValue != null) {
						linked.add(newValue);
					}
		}
	}

	public void removeAllLinkedEntities(EntityPOJOClass pojo) {
		pojo.setLinkedEntities(null);
		list_view_foreign_entities.getItems().clear();
		comboList.put(pojo, new ArrayList<>());
	}

	public void checkURLValidity(String newURL, EntityPOJOClass pojo) throws BadURLException{
		try {
			new URL(newURL).toURI();
		}catch(Exception e) {
			throw new BadURLException("Malformed URL");
		}
		for (EntityPOJOClass entityPOJOClass : pojos) {
			if(newURL!= null && !entityPOJOClass.equals(pojo) && entityPOJOClass.getUrl().equals(newURL)) {
				throw new BadURLException("This URL already exists for other entity");
			}
		}
	}

	public String getStartURL() {
		return baseUrl.getParamValue() + ":" 
	+ (httpsEnabled.getParamValue().equals("yes") ? httpsPort.getParamValue() : httpPort.getParamValue()) 
	+ "/";
	}
}
