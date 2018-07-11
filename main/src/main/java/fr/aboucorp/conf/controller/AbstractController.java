package fr.aboucorp.conf.controller;

import java.util.List;

import api_conf.conf.model.ApiConf;

public abstract class AbstractController {
	private MainController mainCtrl;

	
	public MainController getMainCtrl() {
		return mainCtrl;
	}

	public void setMainCtrl(MainController mainCtrl) {
		this.mainCtrl = mainCtrl;
	}
	
	public abstract void checkInfo();
	
	public abstract List<ApiConf> getAllApiConf();
	
	public abstract void updateValues();

}
