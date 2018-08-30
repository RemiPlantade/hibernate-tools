package fr.aboucorp.conf.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.management.BadAttributeValueExpException;
import javax.transaction.Transactional.TxType;

import org.hibernate.tool.hbm2x.pojo.EntityPOJOClass;
import org.hibernate.tool.hbm2x.pojo.POJOClass;

import api_conf.conf.model.ApiConf;
import fr.aboucorp.conf.ihm.POJOClassCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.util.Callback;

public class POJOClassCellController implements Initializable{

	@FXML
	private Label lbl_entity_name;
	@FXML
	private TextField txt_entity_name;
	@FXML
	private CheckBox chkbx_union;
	@FXML
	private Hyperlink lbl_url_prev;
	@FXML
	private Spinner<Integer> spin_nb_fk;

	private EntityPOJOClass pojo;

	private EntitiesController rootController;
	
	private POJOClassCell cell;

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
		chkbx_union.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				rootController.selectItemInList(cell.getIndex());
				if(newValue) {
					spin_nb_fk.setDisable(false);
					spin_nb_fk.getValueFactory().setValue(0);
				}else {
					spin_nb_fk.setDisable(true);
					spin_nb_fk.getValueFactory().setValue(0);
				}
			}
		});
		SpinnerValueFactory<Integer> valueFactory = //
				new SpinnerValueFactory<Integer>() {
			@Override
			public void decrement(int steps) {
				Integer current = this.getValue();
				Integer newValue = current - steps >= 0 ? current - steps : 0;
				this.setValue(newValue);
				rootController.removeForeignEntity(steps,pojo);
			}

			@Override
			public void increment(int steps) {
				Integer current = this.getValue();
				this.setValue(current + steps);
				rootController.addForeignEntity(steps,pojo);
			}
		};
		spin_nb_fk.setValueFactory(valueFactory);
		
		txt_entity_name.textProperty().addListener((observable, oldValue, newValue) -> {
			String url = rootController.getBaseUrl().getParamValue() +
					":" + (rootController.getHttpsEnabled().getParamValue().equals("yes") ? rootController.getHttpsPort().getParamValue() : rootController.getHttpPort().getParamValue()) +
					"/"  + newValue;
			lbl_url_prev.setText(url);
		});
	}

//	public void updatePojoFromFields() throws BadAttributeValueExpException{
//		if(link_table_chbx.isSelected()) {
//			Integer nbLinkedEntities = select_nb_assoc.getSelectionModel().getSelectedItem();
//			List<Node> nodes = cmb_container.getChildren();
//			if(nbLinkedEntities != null && nodes.size() == nbLinkedEntities) {
//				pojo.setUnionEntity(true);
//				for (Node node : nodes) {
//					if(node instanceof ComboBox) {
//						EntityPOJOClass linkedEntity = ((ComboBox<EntityPOJOClass>) node).getSelectionModel().getSelectedItem();
//						linkedEntity.getLinkerEntities().add((EntityPOJOClass) pojo);
//						pojo.getLinkedEntities().add(linkedEntity);
//					}
//				}
//			}else {
//				throw new BadAttributeValueExpException("You must select the linked entity for each union entity");
//			}
//		}else {
//			pojo.setUnionEntity(false);
//		}
//	}

	public void setRootController(EntitiesController rootController) {
		this.rootController = rootController;
	}

	public void setPojo(EntityPOJOClass pojo) {
		this.pojo = pojo;
		setEntityInfos();

	}

	private void setEntityInfos() {
		lbl_entity_name.setText(pojo.getShortName());
		txt_entity_name.setPromptText(pojo.getShortName());
		String url = rootController.getBaseUrl().getParamValue() +
				":" + (rootController.getHttpsEnabled().getParamValue().equals("yes") ? rootController.getHttpsPort().getParamValue() : rootController.getHttpPort().getParamValue()) +
				"/" + pojo.getShortName();
		lbl_url_prev.setText(url);
	}

	public void setCell(POJOClassCell pojoClassCell) {
		this.cell = pojoClassCell;
	}

}
