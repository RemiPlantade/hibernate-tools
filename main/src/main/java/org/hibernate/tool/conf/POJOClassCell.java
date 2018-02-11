package org.hibernate.tool.conf;

import org.hibernate.tool.hbm2x.pojo.POJOClass;

import javafx.scene.control.ListCell;

public class POJOClassCell extends ListCell<POJOClass> {
	
	
	@Override
	public void updateItem(POJOClass item, boolean empty) 
	{
		super.updateItem(item, empty);

		int index = this.getIndex();
		String name = null;

		// Format name
		if (item == null || empty) 
		{
		} 
		else 
		{
		}
		this.setText(name);
		setGraphic(null);
	}
}
