package fr.aboucorp.conf.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import api_conf.conf.model.ApiConf;
import fr.aboucorp.conf.PropertyBindingException;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanStringProperty;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.util.StringConverter;
import javafx.scene.control.Alert.AlertType;

public class EntitiesController extends AbstractController implements Initializable{

	@FXML
	private Button btn_prev;

	@FXML
	private Button btn_finish;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(btn_prev, btn_finish);
	}

	@Override
	public void checkInfo() {
		
	}

	@Override
	public List<ApiConf> getAllApiConf() {
		return null;
	}


	@Override
	public void getProps() throws SQLException {
		
	}

	@Override
	public void updateConf() {
		
		
	}
}
