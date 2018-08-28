package fr.aboucorp.conf.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import api_conf.conf.model.ApiBean;
import api_conf.conf.model.ApiConf;
import fr.aboucorp.conf.PropertyBindingException;
import fr.aboucorp.conf.model.GenericDao;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;

public abstract class AbstractController {
	
	private String id;
	
	private MainController mainCtrl;
	
	public MainController getMainCtrl() {
		return mainCtrl;
	}

	public void setMainCtrl(MainController mainCtrl) {
		this.mainCtrl = mainCtrl;
	}
	
	public abstract void checkInfo() throws IllegalArgumentException;
	
	public abstract void updateConf();
	
	public abstract List<ApiConf> getAllApiConf();
		
	public abstract void getProps() throws SQLException;
	
	protected static GenericDao<ApiConf> confDao;
	
	protected static GenericDao<ApiBean> beanDao;

	public void initialize() {
		try {
			getProps();
		} catch (SQLException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Binding error");
			alert.setContentText(e1.getMessage());
			alert.showAndWait();
		}
	}
	
	public GenericDao<ApiConf> getConfDao(){
		return confDao;
	}
	
	public GenericDao<ApiBean> getBeanDao(){
		return beanDao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
