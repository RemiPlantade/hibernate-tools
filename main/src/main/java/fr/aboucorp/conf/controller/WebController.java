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
		SpinnerValueFactory<Integer> spin_http_port_facto = //
				new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 80);
		spin_http_port.setValueFactory(spin_http_port_facto);

		SpinnerValueFactory<Integer> spin_https_port_facto = //
				new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 443);
		spin_https_port.setValueFactory(spin_https_port_facto);
		super.initialize(btn_prev, btn_next);
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

	private boolean containsIllegals(String toExamine) {
		Pattern pattern = Pattern.compile("[~#@*+%{}<>\\[\\]|\"\\_^]");
		Matcher matcher = pattern.matcher(toExamine);
		return matcher.find();
	}

	@Override
	public void bindProps() throws PropertyBindingException {
		try {
			baseUrl = confDao.getEntityFromParamKey("api.base.url");
			httpPort =  confDao.getEntityFromParamKey("api.port.http");
			httpsPort =  confDao.getEntityFromParamKey("api.port.https");
			JavaBeanStringProperty baseUrlProp = new JavaBeanStringPropertyBuilder()
			        .bean(baseUrl)
			        .name("paramValue")
			        .build();
			JavaBeanIntegerProperty httpPortProp = new JavaBeanIntegerPropertyBuilder()
			        .bean(httpPort)
			        .name("paramValue")
			        .build();
			JavaBeanIntegerProperty httpsPortProp = new JavaBeanIntegerPropertyBuilder()
			        .bean(httpsPort)
			        .name("paramValue")
			        .build();
			txt_base_url.textProperty().bindBidirectional(baseUrlProp);
			spin_http_port.getValueFactory().valueProperty().bindBidirectional(httpPortProp.asObject());
			spin_https_port.getValueFactory().valueProperty().bindBidirectional(httpsPortProp.asObject());
		} catch (SQLException | NoSuchMethodException e) {
			throw new PropertyBindingException(e.getMessage());
		}
		
		
	}
}
