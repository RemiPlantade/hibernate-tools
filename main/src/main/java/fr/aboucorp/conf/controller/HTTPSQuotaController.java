package fr.aboucorp.conf.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import api_conf.conf.model.ApiConf;
import fr.aboucorp.conf.PropertyBindingException;
import javafx.beans.property.adapter.JavaBeanBooleanProperty;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;
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
	
	private ApiConf httpsEnabled;
	private ApiConf quotaEnabled;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(btn_prev, btn_next);
		
		btn_prev.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				getMainCtrl().onPreviousPage();
			}
		});

		btn_next.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				try {
					checkInfo();
					getMainCtrl().onNextPage();
				}catch(Exception e) {
					
				}
			}
		});
	}

	@Override
	public void checkInfo() {}

	@Override
	public List<ApiConf> getAllApiConf() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void bindProps() throws PropertyBindingException {
		try {
			httpsEnabled = confDao.getEntityFromParamKey("server.ssl.enabled");
			quotaEnabled = confDao.getEntityFromParamKey("api.quota.managed");
			JavaBeanBooleanProperty quotaEnabledProp = new JavaBeanBooleanPropertyBuilder()
			        .bean(quotaEnabled)
			        .name("paramValue")
			        .build(); 
			JavaBeanBooleanProperty httpsEnabledProp = new JavaBeanBooleanPropertyBuilder()
			        .bean(httpsEnabled)
			        .name("paramValue")
			        .build();
		} catch (SQLException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			throw new PropertyBindingException(e.getMessage());
		}	
	}
}
