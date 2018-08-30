package fr.aboucorp.conf.ihm;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.tool.hbm2x.pojo.EntityPOJOClass;
import org.hibernate.tool.hbm2x.pojo.POJOClass;

import fr.aboucorp.conf.controller.EntitiesController;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class POJOClassCellFactory implements Callback<ListView<EntityPOJOClass>, ListCell<EntityPOJOClass>> {

	private EntitiesController entitiesController;

	public POJOClassCellFactory(EntitiesController entitiesController) {
		this.entitiesController = entitiesController;
	}

	@Override
	public ListCell<EntityPOJOClass> call(ListView<EntityPOJOClass> param) {
		POJOClassCell cell = new POJOClassCell(entitiesController);
		return cell;
	}


	
}
