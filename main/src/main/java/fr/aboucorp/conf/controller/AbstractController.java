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
	
	private MainController mainCtrl;
	
	public MainController getMainCtrl() {
		return mainCtrl;
	}

	public void setMainCtrl(MainController mainCtrl) {
		this.mainCtrl = mainCtrl;
	}
	
	public abstract void checkInfo();
	
	public abstract List<ApiConf> getAllApiConf();
		
	public abstract void bindProps() throws PropertyBindingException;
	
	protected static GenericDao<ApiConf> confDao;
	
	protected static GenericDao<ApiBean> beanDao;

	public void initialize(Button btn_prev, Button btn_next) {
		try {
			bindProps();
			btn_prev.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					getMainCtrl().onPreviousPage();
				}
			});

			btn_next.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent event) {
					try {
						checkInfo();
						getMainCtrl().onNextPage();
					}catch(IllegalArgumentException e) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error Dialog");
						alert.setHeaderText("Erreur de saisie");
						alert.setContentText(e.getMessage());
						alert.showAndWait();
					}
				}
			});
		} catch (PropertyBindingException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Binding error");
			alert.setContentText(e1.getMessage());
			alert.showAndWait();
		}
	}
	
	
}
