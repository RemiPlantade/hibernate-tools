package fr.aboucorp.conf.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.ToggleSwitch;

import api_conf.conf.model.ApiConf;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class HTTPSQuotaController  extends AbstractController implements Initializable {

	@FXML 
	private ToggleSwitch chkbx_https;

	@FXML 
	private ToggleSwitch chkbx_quota;

	private ApiConf httpsEnabled;
	private ApiConf quotaEnabled;


	@Override
	public void initialize(URL location, ResourceBundle resources) {}

	@Override
	public void checkInfo() {
	}

	@Override
	public void updateConf() {
		httpsEnabled.setParamValue(chkbx_https.isSelected()?"true":"false");
		quotaEnabled.setParamValue(chkbx_quota.isSelected()?"true":"false");
	}

	@Override
	public void getProps() {
		httpsEnabled =  getMainCtrl().getConfByKey("server.ssl.enabled");
		quotaEnabled =  getMainCtrl().getConfByKey("api.quota.managed");
	}
}
