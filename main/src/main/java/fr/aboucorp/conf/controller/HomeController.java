package fr.aboucorp.conf.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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

public class HomeController extends AbstractController implements Initializable{
	@FXML
	private Button btn_begin;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			confDao = new GenericDao<>(ApiConf.class);
			beanDao = new GenericDao<>(ApiBean.class);
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Database error");
			alert.setContentText("Canot access to configuration database.");
			alert.showAndWait();
		}
		btn_begin.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				getMainCtrl().onNextPage();
			}
		});
	}

	@Override
	public void checkInfo() throws IllegalArgumentException{}

	@Override
	public List<ApiConf> getAllApiConf() {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public void getProps() throws SQLException {}

	@Override
	public void updateConf() {
		// TODO Auto-generated method stub
		
	}


}
