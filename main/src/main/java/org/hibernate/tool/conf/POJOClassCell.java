package org.hibernate.tool.conf;

import java.io.IOException;
import java.util.List;

import org.hibernate.tool.hbm2x.pojo.POJOClass;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

public class POJOClassCell extends ListCell<POJOClass> {

	private List<POJOClass> pojo_list;
	private POJOClassCellController rendererController;
	private Node renderer;

	public POJOClassCell(List<POJOClass> pojos) {
		pojo_list = pojos;
		try {
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/POJOClassCell.fxml"));
			renderer = fxmlLoader.load();
			rendererController = (POJOClassCellController) fxmlLoader.getController();
			rendererController.setPOJOList(pojos);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public POJOClassCell() {
		this(null);
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
                    text = String.valueOf(item.getDeclarationName());
                    setGraphic(null);
                    setText(text);
                }
            }
        }
	}

}
