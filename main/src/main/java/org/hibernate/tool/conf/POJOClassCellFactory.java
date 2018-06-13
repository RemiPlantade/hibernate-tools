package org.hibernate.tool.conf;

import java.util.List;

import org.hibernate.tool.hbm2x.pojo.POJOClass;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class POJOClassCellFactory implements Callback<ListView<POJOClass>, ListCell<POJOClass>> {

	private List<POJOClass> pojo_list;
	public POJOClassCellFactory(List<POJOClass> pojos) {
		pojo_list = pojos;
	}

	@Override
	public ListCell<POJOClass> call(ListView<POJOClass> param) {
		return new POJOClassCell(pojo_list);
	}

}
