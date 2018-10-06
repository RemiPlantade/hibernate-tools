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
		btn_begin.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				getMainCtrl().onNextPage();
				getMainCtrl().displayBottomPane(true);
			}
		});
	}

	@Override
	public void checkInfo() throws IllegalArgumentException{}

	@Override
	public void updateConf() {}

	@Override
	public void getProps() {}


}
