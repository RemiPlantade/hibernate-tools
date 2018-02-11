package org.hibernate.tool.conf;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.tool.hbm2x.pojo.POJOClass;

import javafx.fxml.Initializable;

public class POJOClassCellController implements Initializable{
	
	private List<POJOClass> pojo_list;

	public POJOClassCellController() {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}

	public void setPOJOList(List<POJOClass> pojos) {
		this.pojo_list = pojos;
	}

}
