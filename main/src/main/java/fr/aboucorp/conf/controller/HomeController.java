package fr.aboucorp.conf.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import api_conf.conf.model.ApiConf;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class HomeController extends AbstractController implements Initializable{
	@FXML
	private Button btn_begin;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btn_begin.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		      getMainCtrl().onNextPage();
		    }
		});
		
	}

	@Override
	public void checkInfo() {}

	@Override
	public List<ApiConf> getAllApiConf() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateValues() {
		// TODO Auto-generated method stub
		
	}
	
	
}
