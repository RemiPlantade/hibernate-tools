package fr.aboucorp.conf.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.management.BadAttributeValueExpException;
import javax.transaction.Transactional.TxType;

import api_conf.conf.model.ApiConf;
import api_conf.conf.model.ApiParamCat;
import api_conf.conf.model.ApiParamType;
import javafx.beans.property.SimpleStringProperty;
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
	private Button btn_prev;

	@FXML
	private Button btn_next;
	
	private ApiConf admin_username;
	private ApiConf admin_pwd;
	
	private JavaBeanStringProperty ident = null;
	private JavaBeanStringProperty pwd = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initConf();
		try {
			ident = new JavaBeanStringPropertyBuilder()
			        .bean(admin_username)
			        .name("paramValue")
			        .build();
			pwd = new JavaBeanStringPropertyBuilder()
			        .bean(admin_pwd)
			        .name("paramValue")
			        .build();
			txt_ident.textProperty().bindBidirectional(ident);
			txt_ident.textProperty().bindBidirectional(pwd);
			
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		btn_prev.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				getMainCtrl().onPreviousPage();
			}
		});

		btn_next.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				try {
					checkInfo();
//					updateValues();
					System.out.println("Yololo" + admin_username.getParamValue());
					getMainCtrl().onNextPage();
				}catch(IllegalArgumentException e) {
					ident.set("");
					pwd.set("");
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Dialog");
					alert.setHeaderText("Erreur de saisie");
					alert.setContentText(e.getMessage());
					alert.showAndWait();
				}
			}
		});
	}

	private void initConf() {
		admin_username = new ApiConf();
		admin_username.setModifiable(true);
		admin_username.setDescription("Identifiant administrateur de l'API");
		admin_username.setParamCategory(ApiParamCat.GENERAL);
		admin_username.setParamKey("api.admin.username");
		admin_username.setParamName("Identifiant admin");
		admin_username.setAdded(true);
		admin_username.setParamType(ApiParamType.TEXT);
		
		admin_pwd = new ApiConf();
		admin_pwd.setModifiable(true);
		admin_pwd.setDescription("Mot de passe administrateur de l'API");
		admin_pwd.setParamCategory(ApiParamCat.GENERAL);
		admin_pwd.setParamKey("api.admin.password");
		admin_pwd.setParamName("Mot de passe admin");
		admin_pwd.setAdded(true);
		admin_pwd.setParamType(ApiParamType.TEXT);
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
	public void updateValues() {
		admin_username.setParamValue(txt_ident.getText());
		admin_pwd.setParamValue(txt_mdp.getText());
	}
}
