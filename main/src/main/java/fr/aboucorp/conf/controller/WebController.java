package fr.aboucorp.conf.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import api_conf.conf.model.ApiConf;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class WebController extends AbstractController implements Initializable{

	@FXML
	private TextField txt_base_url;

	@FXML
	private Spinner<Integer> spin_http_port;

	@FXML
	private Spinner<Integer> spin_https_port;

	private ApiConf baseUrl;
	private ApiConf httpPort;
	private ApiConf httpsPort; 

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		SpinnerValueFactory<Integer> spin_http_port_facto = //
				new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 65535, 80);
		spin_http_port.setValueFactory(spin_http_port_facto);
		spin_http_port.getEditor().textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.length() > 0) {
					String value = oldValue;
					try {
						spin_http_port.getValueFactory().getConverter().fromString(newValue);
						value = newValue;
					}catch(NumberFormatException e) {}
					spin_http_port.getEditor().textProperty().set(value);
					httpPort.setParamValue(value);
				}
			}
		});
		txt_base_url.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(containsIllegals(newValue)) {
					newValue = oldValue;
				}else {
					baseUrl.setParamValue(newValue);
				}

			}
		});

		SpinnerValueFactory<Integer> spin_https_port_facto = //
				new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 65535, 443);
		spin_https_port.setValueFactory(spin_https_port_facto);
		spin_https_port.getEditor().textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.length() > 0) {
					String value = oldValue;
					try {
						spin_http_port.getValueFactory().getConverter().fromString(newValue);
						value = newValue;
					}catch(NumberFormatException e) {}
					spin_http_port.getEditor().textProperty().set(value);
					httpsPort.setParamValue(value);
				}
			}
		});

	}

	@Override
	public void checkInfo() {
		if(containsIllegals(txt_base_url.getText())) {
			throw new IllegalArgumentException("L'URL contient des caractères invalides");
		}
	}

	private boolean containsIllegals(String toExamine) {
		Pattern pattern = Pattern.compile("[~#@*+%{}<>\\[\\]|\"\\_^]");
		Matcher matcher = pattern.matcher(toExamine);
		return matcher.find();
	}

	@Override
	public void updateConf() {
		baseUrl.setParamValue(txt_base_url.getText());
		httpPort.setParamValue(spin_http_port.getEditor().textProperty().get());
		httpsPort.setParamValue(spin_https_port.getEditor().textProperty().get());
	}

	@Override
	public void getProps() {
		baseUrl =  getMainCtrl().getConfByKey("api.base.url");
		httpPort =   getMainCtrl().getConfByKey("api.port.http");
		httpsPort =   getMainCtrl().getConfByKey("api.port.https");
	}
}
