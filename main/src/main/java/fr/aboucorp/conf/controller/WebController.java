package fr.aboucorp.conf.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import api_conf.conf.model.ApiConf;
import api_conf.conf.model.ApiParamCat;
import api_conf.conf.model.ApiParamType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class WebController extends AbstractController implements Initializable{

	@FXML
	private TextField txt_base_url;

	@FXML
	private Spinner<Integer> spin_http_port;

	@FXML
	private Spinner<Integer> spin_https_port;

	@FXML
	private Button btn_prev;

	@FXML
	private Button btn_next;

	private ApiConf baseUrl;
	private ApiConf httpPort;
	private ApiConf httpsPort;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initConf();
		SpinnerValueFactory<Integer> spin_http_port_facto = //
				new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 80);
		spin_http_port.setValueFactory(spin_http_port_facto);

		SpinnerValueFactory<Integer> spin_https_port_facto = //
				new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 443);
		spin_https_port.setValueFactory(spin_https_port_facto);

		btn_prev.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				getMainCtrl().onPreviousPage();
			}
		});

		btn_next.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				try {
					checkInfo();
					updateValues();
					getMainCtrl().onNextPage();
				}catch(Exception e) {
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
		baseUrl = new ApiConf();
		baseUrl.setModifiable(true);
		baseUrl.setDescription("API URL Prefix");
		baseUrl.setParamCategory(ApiParamCat.GENERAL);
		baseUrl.setParamKey("api.url");
		baseUrl.setParamName("API URL Prefix");
		baseUrl.setAdded(true);
		baseUrl.setParamType(ApiParamType.TEXT);

		httpPort = new ApiConf();
		httpPort.setModifiable(true);
		httpPort.setDescription("HTTP port");
		httpPort.setParamCategory(ApiParamCat.GENERAL);
		httpPort.setParamKey("api.port.http");
		httpPort.setParamName("HTTP port");
		httpPort.setAdded(true);
		httpPort.setParamType(ApiParamType.NUMBER);

		httpsPort = new ApiConf();
		httpsPort.setModifiable(true);
		httpsPort.setDescription("HTTPS port");
		httpsPort.setParamCategory(ApiParamCat.GENERAL);
		httpsPort.setParamKey("api.port.https");
		httpsPort.setParamName("HTTPS port");
		httpsPort.setAdded(true);
		httpsPort.setParamType(ApiParamType.NUMBER);
	}

	@Override
	public void checkInfo() {
		if(containsIllegals(txt_base_url.getText())) {
			throw new IllegalArgumentException("L'URL contient des caractères invalides");
		}

	}

	@Override
	public List<ApiConf> getAllApiConf() {
		return new ArrayList<>(Arrays.asList(baseUrl,httpPort,httpsPort));
	}

	@Override
	public void updateValues() {
		baseUrl.setParamValue(txt_base_url.getText());	
		httpPort.setParamValue(spin_http_port.getValue()+"");
		httpsPort.setParamValue(spin_https_port.getValue()+"");
	}

	private boolean containsIllegals(String toExamine) {
		Pattern pattern = Pattern.compile("[~#@*+%{}<>\\[\\]|\"\\_^]");
		Matcher matcher = pattern.matcher(toExamine);
		return matcher.find();
	}
}
