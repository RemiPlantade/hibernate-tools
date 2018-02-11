package org.hibernate.tool.conf;

import org.hibernate.tool.hbm2x.pojo.POJOClass;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class POJOClassCellFactory implements Callback<ListView<POJOClass>, ListCell<POJOClass>> {

	@Override
	public ListCell<POJOClass> call(ListView<POJOClass> param) {
		// TODO Auto-generated method stub
		return new POJOClassCell();
	}

}
