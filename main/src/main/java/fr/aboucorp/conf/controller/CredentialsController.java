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

	@FXML
	protected Button btn_prev;

	@FXML
	protected Button btn_next;
	
	private ApiConf admin_username;
	private ApiConf admin_pwd;
	
	private JavaBeanStringProperty ident = null;
	private JavaBeanStringProperty pwd = null;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(btn_prev, btn_next);
	}

	@Override
	public void checkInfo() {
		if(!txt_mdp.getText().equals(txt_confirm.getText())) {
			throw new IllegalArgumentException("Les mots de passe ne correspondent pas");
		}else if(txt_mdp.getText().length() <=4) {
			throw new IllegalArgumentException("Votre mot de passe doit contenir au moins 5 caractères");
		}else if(txt_ident.getText().length() <= 0 ) {
			throw new IllegalArgumentException("Le champ identifiant ne peut pas être vide");
		}
	}

	@Override
	public List<ApiConf> getAllApiConf() {
		return new ArrayList<>(Arrays.asList(admin_username,admin_pwd));
	}

	@Override
	public void bindProps() throws PropertyBindingException {
		try {
			admin_username = confDao.getEntityFromParamKey("api.admin.username");
			admin_pwd = confDao.getEntityFromParamKey("api.admin.password");
			ident = new JavaBeanStringPropertyBuilder()
			        .bean(admin_username)
			        .name("paramValue")
			        .build();
			pwd = new JavaBeanStringPropertyBuilder()
			        .bean(admin_pwd)
			        .name("paramValue")
			        .build();
			txt_ident.textProperty().bindBidirectional(ident);
			txt_mdp.textProperty().bindBidirectional(pwd);
		} catch (SQLException | NoSuchMethodException e) {
			throw new PropertyBindingException(e.getMessage());
		}
		
	}
}
