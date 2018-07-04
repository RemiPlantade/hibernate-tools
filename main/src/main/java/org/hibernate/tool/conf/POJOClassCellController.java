package org.hibernate.tool.conf;

import java.net.URL;
import java.util.Arrays;
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
import javafx.scene.layout.FlowPane;
import javafx.util.Callback;

public class POJOClassCellController implements Initializable{

	@FXML
	private Label pojo_label;
	@FXML
	private CheckBox link_table_chbx;
	@FXML
	private FlowPane cmb_container;
	@FXML
	private ComboBox<Integer> select_nb_assoc;

	private List<POJOClass> pojo_list;

	public POJOClassCellController() {}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Callback<ListView<POJOClass>, ListCell<POJOClass>> cmbbx_factory = lv -> new ListCell<POJOClass>() {
			@Override
			protected void updateItem(POJOClass item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getShortName());
			}

		};

		link_table_chbx.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue) {
					select_nb_assoc.setDisable(false);

					select_nb_assoc.setItems(FXCollections.observableArrayList(Arrays.asList(null,1,2,3,4,5,6,7,8,9,10)));
				}else {
					select_nb_assoc.setDisable(true);
					select_nb_assoc.setItems(null);
					cmb_container.getChildren().clear();
				}
			}
		});
		select_nb_assoc.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<Integer>() {
					public void changed(ObservableValue<? extends Integer> ov,Integer old_val, Integer new_val) {
						if(new_val != null) {
							cmb_container.getChildren().clear();
							for (int i = 0 ; i < new_val;i++) {
								ComboBox<POJOClass> cmb = new ComboBox<POJOClass>();
								cmb.setItems(FXCollections.observableArrayList(pojo_list));
								cmb.setCellFactory(cmbbx_factory);
								cmb_container.getChildren().add(cmb);
							}
						}

					}
				});

	}

	public void setPOJOList(List<POJOClass> pojos) {
		this.pojo_list = pojos;
	}

	public void setPojo(POJOClass item) {
		pojo_label.setText(item.getShortName());

	}

}
