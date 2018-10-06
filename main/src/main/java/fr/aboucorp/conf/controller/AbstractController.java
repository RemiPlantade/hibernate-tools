package fr.aboucorp.conf.controller;

public abstract class AbstractController {
	
	private String id;
	
	private MainController mainCtrl;
	
	public void initialize() {}

	
	public abstract void checkInfo() throws IllegalArgumentException;
	
	public abstract void updateConf();	
	
	public MainController getMainCtrl() {
		return mainCtrl;
	}
	
	public void setMainCtrl(MainController mainCtrl) {
		this.mainCtrl = mainCtrl;
		getProps();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public abstract void getProps();
}
