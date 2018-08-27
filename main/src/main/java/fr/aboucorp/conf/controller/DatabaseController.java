package fr.aboucorp.conf.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import api_conf.conf.model.ApiConf;
import api_conf.conf.model.hibernate.Dialect;
import fr.aboucorp.conf.PropertyBindingException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class DatabaseController extends AbstractController implements Initializable{
	
	@FXML
	private TextField txt_name_bd;
	@FXML
	private TextField txt_ident_bd;
	@FXML
	private TextField txt_jdbc;
	@FXML 
	private ComboBox<Dialect> cmd_type_bd;
	@FXML
	private PasswordField txt_mdp_bd;
	@FXML
	private TextField txt_database_url;
	@FXML
	private Button btn_prev;
	@FXML
	private Button btn_next;
	
	private ApiConf bdName;
	private ApiConf bdIdent;
	private ApiConf jdbcDriver;
	private ApiConf bdPwd;
	private ApiConf bdURL;
	private ApiConf bdType;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(btn_prev, btn_next);
		cmd_type_bd.setItems(FXCollections.observableArrayList(Arrays.asList(
				new Dialect().setName("DB2").setPath("org.hibernate.dialect.DB2Dialect"),
				new Dialect().setName("DB2 AS/400").setPath("org.hibernate.dialect.DB2400Dialect"),
				new Dialect().setName("DB2 OS390").setPath("org.hibernate.dialect.DB2390Dialect"),
				new Dialect().setName("PostgreSQL").setPath("org.hibernate.dialect.PostgreSQLDialect"),
				new Dialect().setName("MySQL").setPath("org.hibernate.dialect.MySQLDialect"),
				new Dialect().setName("MySQL with InnoDB").setPath("org.hibernate.dialect.MySQLInnoDBDialect"),
				new Dialect().setName("MySQL with MyISAM").setPath("org.hibernate.dialect.MySQLMyISAMDialect"),
				new Dialect().setName("Oracle 8").setPath("org.hibernate.dialect.OracleDialect"),
				new Dialect().setName("Oracle 9i/10g").setPath("org.hibernate.dialect.Oracle9Dialect"),
				new Dialect().setName("Sybase").setPath("org.hibernate.dialect.SybaseDialect"),
				new Dialect().setName("Sybase Anywhere").setPath("org.hibernate.dialect.SybaseAnywhereDialect"),
				new Dialect().setName("Microsoft SQL Server").setPath("org.hibernate.dialect.SQLServerDialect"),
				new Dialect().setName("SAP DB").setPath("org.hibernate.dialect.SAPDBDialect"),
				new Dialect().setName("Informix").setPath("org.hibernate.dialect.InformixDialect"),
				new Dialect().setName("HypersonicSQL").setPath("org.hibernate.dialect.HSQLDialect"),
				new Dialect().setName("Ingres").setPath("org.hibernate.dialect.IngresDialect"),
				new Dialect().setName("Progress").setPath("org.hibernate.dialect.ProgressDialect"),
				new Dialect().setName("Mckoi SQL").setPath("org.hibernate.dialect.MckoiDialect"),
				new Dialect().setName("Interbase").setPath("org.hibernate.dialect.InterbaseDialect"),
				new Dialect().setName("Pointbase").setPath("org.hibernate.dialect.PointbaseDialect"),
				new Dialect().setName("FrontBase").setPath("org.hibernate.dialect.FrontbaseDialect"),
				new Dialect().setName("Firebird").setPath("org.hibernate.dialect.FirebirdDialect")) 
				));
		cmd_type_bd.getSelectionModel().select(4);
	}

	@Override
	public void checkInfo() throws IllegalArgumentException {
		if(txt_name_bd.getText().length() <= 0) {
			throw new IllegalArgumentException("Database name cannot be empty");
		}else if(txt_ident_bd.getText().length() <= 0) {
			throw new IllegalArgumentException("Database login cannot be empty");
		} else if(txt_jdbc.getText().length() <= 0) {
			throw new IllegalArgumentException("Jdbc driver class cannot be empty");
		}		
	}

	@Override
	public List<ApiConf> getAllApiConf() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getProps() throws SQLException {
		bdName = confDao.getEntityFromParamKey("spring.api.driver-class-name");
		bdIdent = confDao.getEntityFromParamKey("spring.api.username");
		jdbcDriver = confDao.getEntityFromParamKey("spring.api.driver-class-name");
		bdPwd = confDao.getEntityFromParamKey("spring.api.password");
		bdURL =  confDao.getEntityFromParamKey("spring.api.url");
		bdType = confDao.getEntityFromParamKey("spring.api.jpa.properties.hibernate.dialect");
	}

	@Override
	public void updateConf() {
		bdName.setParamValue(txt_name_bd.getText());
		bdIdent.setParamValue(txt_ident_bd.getText());
		bdURL.setParamValue(txt_database_url.getText());
		jdbcDriver.setParamValue(txt_jdbc.getText());
		bdPwd.setParamValue(txt_mdp_bd.getText());
		bdType.setParamValue(cmd_type_bd.getSelectionModel().getSelectedItem().getPath());
	}
}
