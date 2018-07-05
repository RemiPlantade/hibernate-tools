package org.hibernate.tool.conf;

import java.io.IOException;
import java.util.List;

import org.hibernate.tool.hbm2x.pojo.EntityPOJOClass;
import org.hibernate.tool.hbm2x.pojo.POJOClass;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

public class POJOClassCell extends ListCell<EntityPOJOClass> {

	private POJOClassCellController rendererController;
	private Node renderer;
	
	public POJOClassCell(List<EntityPOJOClass> pojo_list) {
		try {
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/POJOClassCell.fxml"));
			renderer = fxmlLoader.load();
			rendererController = (POJOClassCellController) fxmlLoader.getController();
			rendererController.setPOJOList(pojo_list);
			//rendererController.setPojo(getItem());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}


	@Override
	public void updateItem(EntityPOJOClass item, boolean empty) 
	{
		super.updateItem(item, empty);
        String text = null;
        if (!empty) {
            if (item == null) {
                text = "No Table Found";
                setGraphic(null);
                setText(text);
            } else {
                if (renderer != null) {
                    rendererController.setPojo(item);
                    setGraphic(renderer);
                    setText(text);
                } else {
                    text = String.valueOf(item.getShortName());
                    setGraphic(null);
                    setText(text);
                }
            }
        }
	}
	public POJOClassCellController getRendererController() {
		return rendererController;
	}
}
