package fr.aboucorp.conf.controller;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import api_conf.conf.model.ApiConf;
import fr.aboucorp.conf.PropertyBindingException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

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
	
	@FXML
	private Label lbl_file_path;
	
	private String temp_keystore_path;
	
	private ApiConf pwdKeyStore;
	private ApiConf keystoreFilePath;
	private ApiConf keyStoreType;
	private ApiConf keyAlias;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(btn_prev, btn_next);
		cmb_type_keystore.itemsProperty().set(FXCollections.observableArrayList(
		        "JKS",
		        "JCEKS",
		        "PKCS12",
		        "PKCS11",
		        "DKS",
		        "WINDOWS-MY",
		        "BKS"
		    ));
		cmb_type_keystore.getSelectionModel().select(2);
		btn_keystore_file.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Select Keystore file");
				File file = fileChooser.showOpenDialog(getMainCtrl().getPrimaryStage());
				 if (file != null) {
					 temp_keystore_path = file.getAbsolutePath();
					 lbl_file_path.setText(temp_keystore_path);
				 }else {
					 temp_keystore_path = null;
				 }
			}
		});

	}

	@Override
	public void checkInfo()  throws IllegalArgumentException{
		if(temp_keystore_path == null) {
			throw new IllegalArgumentException("You must choose a keystore file");
		}else if (alias_keystore.getText().length() <= 0) {
			throw new IllegalArgumentException("You must enter a SSL key alias");
		}
	}

	@Override
	public List<ApiConf> getAllApiConf() {
		return new ArrayList<>(Arrays.asList(pwdKeyStore,keystoreFilePath,keyStoreType,keyAlias));
	}

	@Override
	public void getProps() throws SQLException {
		pwdKeyStore = confDao.getEntityFromParamKey("server.ssl.key-store-password");
		keystoreFilePath = confDao.getEntityFromParamKey("server.ssl.key-store");
		keyStoreType = confDao.getEntityFromParamKey("server.ssl.key-store-type");
		keyAlias = confDao.getEntityFromParamKey("server.ssl.key-alias");
	}

	@Override
	public void updateConf() {
		keystoreFilePath.setParamValue(temp_keystore_path);
		pwdKeyStore.setParamValue(mdp_keystore.getText());
		keyAlias.setParamValue(alias_keystore.getText());
		keyStoreType.setParamValue(cmb_type_keystore.getSelectionModel().getSelectedItem());
		
	}
}
