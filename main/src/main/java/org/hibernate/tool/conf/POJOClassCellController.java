package org.hibernate.tool.conf;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.tool.hbm2x.pojo.POJOClass;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class POJOClassCellController implements Initializable{
	
	@FXML
	private Label pojo_label;
	@FXML
	private CheckBox link_table_chbx;
	@FXML
	private ComboBox<POJOClass> main_table_cmb;
	@FXML
	private ComboBox<POJOClass> second_table_cmb;
	
	private List<POJOClass> pojo_list;

	public POJOClassCellController() {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Callback<ListView<POJOClass>, ListCell<POJOClass>> cmbbx_factory = lv -> new ListCell<POJOClass>() {

		    @Override
		    protected void updateItem(POJOClass item, boolean empty) {
		        super.updateItem(item, empty);
		        setText(empty ? "" : item.getDeclarationName());
		    }

		};
		main_table_cmb.setCellFactory(cmbbx_factory);
		second_table_cmb.setCellFactory(cmbbx_factory);

		link_table_chbx.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		       if(newValue) {
		    	   main_table_cmb.setDisable(false);
		    	   second_table_cmb.setDisable(false);
		    	   main_table_cmb.setItems(FXCollections.observableArrayList(pojo_list));
		    	   second_table_cmb.setItems(FXCollections.observableArrayList(pojo_list));
		       }else {
		    	   main_table_cmb.setItems(null);
		    	   main_table_cmb.setDisable(true);
		    	   second_table_cmb.setItems(null);
		    	   second_table_cmb.setDisable(true);
		       }
		    }
		});
		
		
	}

	public void setPOJOList(List<POJOClass> pojos) {
		this.pojo_list = pojos;
	}

	public void setPojo(POJOClass item) {
		pojo_label.setText(item.getDeclarationName());
		
	}

}
