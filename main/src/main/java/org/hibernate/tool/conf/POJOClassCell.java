package org.hibernate.tool.conf;

import java.io.IOException;
import java.util.List;

import org.hibernate.tool.hbm2x.pojo.POJOClass;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

public class POJOClassCell extends ListCell<POJOClass> {

	private POJOClassCellController rendererController;
	private Node renderer;

	public POJOClassCell(List<POJOClass> pojos) {
		
	}

	public POJOClassCell(List<POJOClass> pojo_list, POJOClassCellController rendererController, Node renderer) {
		this(null);
		this.rendererController = rendererController;
		this.renderer = renderer;
	}

	@Override
	public void updateItem(POJOClass item, boolean empty) 
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

}
