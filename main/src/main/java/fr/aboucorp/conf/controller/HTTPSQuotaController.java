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
import javafx.scene.control.CheckBox;

public class HTTPSQuotaController  extends AbstractController implements Initializable {
	
	@FXML 
	private CheckBox chkbx_https;
	
	@FXML 
	private CheckBox chkbx_quota;
	
	@FXML
	private Button btn_prev;
	
	@FXML
	private Button btn_next;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btn_prev.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
			      getMainCtrl().onPreviousPage();
			    }
			});
		
		btn_next.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
			      getMainCtrl().onNextPage();
			    }
			});
	}

	@Override
	public void checkInfo() {
		// TODO Auto-generated method stub
		
	}

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
