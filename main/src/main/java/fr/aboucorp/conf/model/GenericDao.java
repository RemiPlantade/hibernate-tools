package fr.aboucorp.conf.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class GenericDao<T> {
	private ConnectionSource connection;
	private Dao<T,String> dao;

	public GenericDao(Class<?> clazz) throws SQLException {
		connection = new JdbcConnectionSource("jdbc:sqlite:src/main/resources/sample.db");
		dao = (Dao<T, String>) DaoManager.createDao(connection, clazz);
	}

	public void closeConnection() throws IOException {
		connection.close();
	}

	public List<T> getEntitiesFromAttr(String attrName, String value) throws SQLException {
		QueryBuilder<T, String> queryBuilder = dao.queryBuilder();
		// the 'password' field must be equal to "qwerty"
		queryBuilder.where().eq(attrName, value);
		// prepare our sql statement
		PreparedQuery<T> preparedQuery = queryBuilder.prepare();
		// query for all accounts that have "qwerty" as a password
		return dao.query(preparedQuery);
	}
	
	public T getOneEntityFromAttr(String attrName, String value) throws SQLException {
		QueryBuilder<T, String> queryBuilder = dao.queryBuilder();
		// the 'password' field must be equal to "qwerty"
		queryBuilder.limit(1L).where().eq(attrName, value);
		// prepare our sql statement
		PreparedQuery<T> preparedQuery = queryBuilder.prepare();
		// query for all accounts that have "qwerty" as a password
		return dao.queryForFirst(preparedQuery);
	}
	
	public T getEntityFromParamKey(String value) throws SQLException {
		QueryBuilder<T, String> queryBuilder = dao.queryBuilder();
		// the 'password' field must be equal to "qwerty"
		queryBuilder.where().eq("param_key", value);
		// prepare our sql statement
		PreparedQuery<T> preparedQuery = queryBuilder.prepare();
		// query for all accounts that have "qwerty" as a password
		return dao.queryForFirst(preparedQuery);
	}
	
	public boolean updateEntity(T object) throws SQLException {
		return dao.update(object) == 1;
	}
}
