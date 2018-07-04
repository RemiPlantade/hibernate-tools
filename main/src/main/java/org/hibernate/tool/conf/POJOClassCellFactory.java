package org.hibernate.tool.conf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.tool.hbm2x.pojo.POJOClass;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class POJOClassCellFactory implements Callback<ListView<POJOClass>, ListCell<POJOClass>> {

	private List<POJOClass> pojo_list;
	private List<POJOClassCellController> rendererControllerList = new ArrayList<POJOClassCellController>();
	
	public POJOClassCellFactory(List<POJOClass> pojos) {
		pojo_list = pojos;
	}

	@Override
	public ListCell<POJOClass> call(ListView<POJOClass> param) {
		try {
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/POJOClassCell.fxml"));
			Node renderer = fxmlLoader.load();
			POJOClassCellController rendererController = (POJOClassCellController) fxmlLoader.getController();
			rendererController.setPOJOList(pojo_list);
			rendererControllerList.add(rendererController);
			return new POJOClassCell(pojo_list,rendererController,renderer);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public List<POJOClassCellController> getRendererControllerList() {
		return rendererControllerList;
	}

}
