package fr.aboucorp.conf.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.tool.hbm2x.pojo.EntityPOJOClass;

import api_conf.conf.model.ApiConf;
import fr.aboucorp.conf.PropertyBindingException;
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
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.util.StringConverter;
import javafx.scene.control.Alert.AlertType;

public class EntitiesController extends AbstractController implements Initializable{
	
	@FXML
	private ListView<EntityPOJOClass> list_view_entities;
	
	@FXML
	private ListView<ComboBox<EntityPOJOClass>> list_view_foreign_entities;

	private List<EntityPOJOClass> pojos; 
	
	private ApiConf baseUrl;
	private ApiConf httpPort;
	private ApiConf httpsPort;
	private ApiConf httpsEnabled;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize();
		
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

	public void removeForeignEntity(int steps, EntityPOJOClass pojo) {
		//cmb_container.getChildren().clear();
		//							for (int i = 0 ; i < new_val;i++) {
		//								ComboBox<EntityPOJOClass> cmb = new ComboBox<EntityPOJOClass>();
		//								cmb.setItems(FXCollections.observableArrayList(pojo_list));
		//								cmb.setCellFactory(cmbbx_factory);
		//								cmb_container.getChildren().add(cmb);
		//							}
		
	}

	public void addForeignEntity(int steps, EntityPOJOClass pojo) {
		for(int i = 0 ; i < steps;i++) {
			
		}
	}

	public void injectPojos(List<EntityPOJOClass> pojos) {
		this.pojos = pojos;
		ObservableList<EntityPOJOClass> items = FXCollections.observableArrayList (pojos);
		list_view_entities.setItems(items);
		list_view_entities.setCellFactory(new POJOClassCellFactory(this));
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
}
