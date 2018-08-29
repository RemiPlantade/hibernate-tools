package fr.aboucorp.conf.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import api_conf.conf.model.ApiConf;
import fr.aboucorp.conf.PropertyBindingException;
import fr.aboucorp.conf.model.GenericDao;
import javafx.beans.property.adapter.JavaBeanStringProperty;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CredentialsController extends AbstractController implements Initializable{

	@FXML
	private PasswordField txt_mdp;

	@FXML
	private PasswordField txt_confirm;

	@FXML
	private TextField txt_ident;
	
	private ApiConf admin_username;
	private ApiConf admin_pwd;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize();
	}

	@Override
	public void checkInfo() {
		System.out.println("!!!!!!!!!!!!! Credential Check infos !");
		if(txt_ident.getText().length() <= 0 ) {
			throw new IllegalArgumentException("Le champ identifiant ne peut pas être vide");
		}
		if(!txt_mdp.getText().equals(txt_confirm.getText())) {
			throw new IllegalArgumentException("Les mots de passe ne correspondent pas");
		}else if(txt_mdp.getText().length() <=4) {
			throw new IllegalArgumentException("Votre mot de passe doit contenir au moins 5 caractères");
		} 
	}

	@Override
	public List<ApiConf> getAllApiConf() {
		return new ArrayList<>(Arrays.asList(admin_username,admin_pwd));
	}

	@Override
	public void getProps() throws SQLException {
		try {
			admin_username = confDao.getEntityFromParamKey("api.admin.username");
			admin_pwd = confDao.getEntityFromParamKey("api.admin.password");
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		
	}

	@Override
	public void updateConf() {
		admin_username.setParamValue(txt_ident.getText());
		admin_pwd.setParamValue(txt_mdp.getText());
		
	}
}
