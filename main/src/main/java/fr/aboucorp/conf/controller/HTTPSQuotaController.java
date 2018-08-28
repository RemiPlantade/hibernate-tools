package fr.aboucorp.conf.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.ToggleSwitch;

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
	private ToggleSwitch chkbx_https;

	@FXML 
	private ToggleSwitch chkbx_quota;

	private ApiConf httpsEnabled;
	private ApiConf quotaEnabled;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize();
	}

	@Override
	public void checkInfo() {
	}

	@Override
	public List<ApiConf> getAllApiConf() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getProps() throws SQLException {
		httpsEnabled = confDao.getEntityFromParamKey("server.ssl.enabled");
		quotaEnabled = confDao.getEntityFromParamKey("api.quota.managed");
	}

	@Override
	public void updateConf() {
		httpsEnabled.setParamValue(chkbx_https.isSelected()?"true":"false");
		quotaEnabled.setParamValue(chkbx_quota.isSelected()?"true":"false");
	}
}
