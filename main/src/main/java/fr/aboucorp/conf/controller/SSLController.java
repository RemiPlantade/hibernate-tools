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
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SSLController extends AbstractController implements Initializable {

	@FXML
	private PasswordField mdp_keystore;
	
	@FXML
	private Button btn_keystore_file;
	
	@FXML 
	private ComboBox<String> cmb_type_keystore;
	
	@FXML
	private TextField alias_keystore;
	
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
