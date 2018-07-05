package org.hibernate.tool.conf;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.management.BadAttributeValueExpException;

import org.hibernate.tool.hbm2x.pojo.EntityPOJOClass;
import org.hibernate.tool.hbm2x.pojo.POJOClass;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

	private List<EntityPOJOClass> pojo_list;

	private EntityPOJOClass pojo;

	public POJOClassCellController() {}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Callback<ListView<EntityPOJOClass>, ListCell<EntityPOJOClass>> cmbbx_factory = lv -> new ListCell<EntityPOJOClass>() {
			@Override
			protected void updateItem(EntityPOJOClass item, boolean empty) {
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
								ComboBox<EntityPOJOClass> cmb = new ComboBox<EntityPOJOClass>();
								cmb.setItems(FXCollections.observableArrayList(pojo_list));
								cmb.setCellFactory(cmbbx_factory);
								cmb_container.getChildren().add(cmb);
							}
						}

					}
				});
	}

	public void updatePojoFromFields() throws BadAttributeValueExpException{
		if(link_table_chbx.isSelected()) {
			Integer nbLinkedEntities = select_nb_assoc.getSelectionModel().getSelectedItem();
			List<Node> nodes = cmb_container.getChildren();
			if(nbLinkedEntities != null && nodes.size() == nbLinkedEntities) {
				pojo.setUnionEntity(true);
				for (Node node : nodes) {
					if(node instanceof ComboBox) {
						EntityPOJOClass linkedEntity = ((ComboBox<EntityPOJOClass>) node).getSelectionModel().getSelectedItem();
						linkedEntity.getLinkerEntities().add((EntityPOJOClass) pojo);
						pojo.getLinkedEntities().add(linkedEntity);
					}
				}
			}else {
				throw new BadAttributeValueExpException("You must select the linked entity for each union entity");
			}
		}else {
			pojo.setUnionEntity(false);
		}
	}

	private EntityPOJOClass getPOJOClassFromListByName(String pojoName) {
		for (EntityPOJOClass entityPOJOClass : pojo_list) {
			if(entityPOJOClass.getShortName().equals(pojoName)) {
				return entityPOJOClass;
			}
		}
		return null;
	}

	public void setPOJOList(List<EntityPOJOClass> pojo_list) {
		this.pojo_list = pojo_list;
	}

	public void setPojo(EntityPOJOClass item) {
		this.pojo = item;
		pojo_label.setText(item.getShortName());
	}

}
