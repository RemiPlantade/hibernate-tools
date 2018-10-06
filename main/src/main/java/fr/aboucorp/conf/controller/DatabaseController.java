package fr.aboucorp.conf.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

import api_conf.conf.model.ApiConf;
import api_conf.conf.model.hibernate.Dialect;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

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
	
	private ApiConf h_bdName ;
	private ApiConf h_bdIdent;
	private ApiConf h_jdbcDriver ;
	private ApiConf h_bdPwd;
	private ApiConf h_bdURL ;
	private ApiConf h_bdType;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
		cmd_type_bd.getSelectionModel().select(6);
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
		
		Connection conn = null;
        try {
            Class.forName(txt_jdbc.getText());
            conn = DriverManager.getConnection(txt_database_url.getText()+"?useSSL=false", txt_ident_bd.getText(), txt_mdp_bd.getText());
            if (conn != null) {
            	Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("EDatabase connection");
    			alert.setHeaderText("Database connection established");
    			alert.showAndWait();
            }else {
            }
        } catch (ClassNotFoundException ex) {
        	throw new IllegalArgumentException("Could not find database driver class");
        } catch (SQLException ex) {
        	throw new IllegalArgumentException("An error occurred. Maybe user/password is invalid");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                	throw new IllegalArgumentException(ex.getMessage());
                }
            }
        }
	}

	@Override
	public void updateConf() {
		h_bdIdent.setParamValue(txt_ident_bd.getText());
		h_bdName.setParamValue(txt_name_bd.getText());
		h_bdPwd.setParamValue(txt_mdp_bd.getText());
		h_bdType.setParamValue(cmd_type_bd.getSelectionModel().getSelectedItem().getPath());
		h_bdURL.setParamValue(txt_database_url.getText());
		h_jdbcDriver.setParamValue(txt_jdbc.getText());
	}

	@Override
	public void getProps() {
		h_bdName = getMainCtrl().getConfByKey("hibernate.default_catalog");
		h_bdIdent = getMainCtrl().getConfByKey("hibernate.connection.username");
		h_jdbcDriver = getMainCtrl().getConfByKey("hibernate.connection.driver_class");
		h_bdPwd = getMainCtrl().getConfByKey("hibernate.connection.password");
		h_bdURL =  getMainCtrl().getConfByKey("hibernate.connection.url");
		h_bdType = getMainCtrl().getConfByKey("hibernate.dialect");
	}
}
