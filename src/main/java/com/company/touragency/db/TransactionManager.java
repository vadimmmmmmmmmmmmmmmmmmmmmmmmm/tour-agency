package com.razkuuuuuuu.touragency.db;

import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;


public class TransactionManager {

	private static final Logger log = Logger.getLogger(TransactionManager.class);
	
	private static TransactionManager instance;

	public static synchronized TransactionManager getInstance() {
		if (instance == null)
			instance = new TransactionManager();
		return instance;
	}

	private TransactionManager() {}


	public Connection getConnection() throws SQLException {
		try {
			InitialContext context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/db");
			Connection connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			return connection;
		} catch (SQLException exception) {
			log.error("database access error", exception);
			throw new SQLException("database access error");
		} catch (NamingException exception) {
			log.error("naming exception", exception);
			throw new SQLException("naming exception");
		}
	}

	public PreparedStatement getPreparedStatement(Connection connection, String sqlQuery) throws SQLException {
		try {
			return connection.prepareStatement(sqlQuery);
		} catch (SQLException exception) {
			log.error("database access error", exception);
			throw new SQLException("database access error");
		}
	}

	public Statement getStatement(Connection connection) throws SQLException {
		try {
			return connection.createStatement();
		} catch (SQLException exception) {
			log.error("database access error", exception);
			throw new SQLException("database access error");
		}
	}

	public void close(Connection connection) throws SQLException {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException exception) {
				log.error("database access error", exception);
				throw new SQLException("database access error");
			}
		}
	}

	public void close(Statement statement) throws SQLException {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException exception) {
				log.error("database access error", exception);
				throw new SQLException("database access error");
			}
		}
	}

	public void close(ResultSet resultSet) throws SQLException {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException exception) {
				log.error("database access error", exception);
				throw new SQLException("database access error");
			}
		}
	}

	public void commit(Connection connection) throws SQLException {
		try {
			connection.commit();
		} catch (SQLException exception) {
			rollback(connection);
			log.error("database access error", exception);
			throw new SQLException("database access error");
		}
	}

	public void rollback(Connection connection) throws SQLException {
		try {
			connection.rollback();
		} catch (SQLException exception) {
			log.error("database access error", exception);
			throw new SQLException("database access error");
		}
	}
}
