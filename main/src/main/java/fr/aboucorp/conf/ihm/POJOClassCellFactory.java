package fr.aboucorp.conf.ihm;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.tool.hbm2x.pojo.EntityPOJOClass;
import org.hibernate.tool.hbm2x.pojo.POJOClass;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class POJOClassCellFactory implements Callback<ListView<EntityPOJOClass>, ListCell<EntityPOJOClass>> {

	private List<EntityPOJOClass> pojo_list;
	private List<POJOClassCell> cells = new ArrayList<>();

	public POJOClassCellFactory(List<EntityPOJOClass> pojos) {
		pojo_list = pojos;
	}

	@Override
	public ListCell<EntityPOJOClass> call(ListView<EntityPOJOClass> param) {
		POJOClassCell cell = new POJOClassCell(pojo_list);
		cells.add(cell);
		return cell;
	}

	public List<POJOClassCell> getCells() {
		return cells;
	}

}
