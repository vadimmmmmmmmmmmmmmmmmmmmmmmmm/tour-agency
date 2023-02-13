package com.razkuuuuuuu.touragency.db.service;

import com.razkuuuuuuu.touragency.db.dao.UserDao;
import com.razkuuuuuuu.touragency.db.TransactionManager;
import com.razkuuuuuuu.touragency.entities.entity.User;
import com.razkuuuuuuu.touragency.exception.AppException;
import jakarta.servlet.ServletContext;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.razkuuuuuuu.touragency.constants.ErrorMessages.DATABASE_ERROR;
import static com.razkuuuuuuu.touragency.constants.ContextAttributes.*;

public class UserService {

	private static final Logger log = Logger.getLogger(UserService.class);

	private final ServletContext servletContext;
	private final TransactionManager transactionManager;

	public UserService(ServletContext servletContext) {
		this.servletContext = servletContext;
		this.transactionManager = (TransactionManager) servletContext.getAttribute(TRANSACTION_MANAGER);
	}

	public User getUserByEmail(String email) throws AppException {
		//language=MySQL
		String query = "SELECT id, " +
				"       email, " +
				"       password, " +
				"       name, " +
				"       surname, " +
				"       status, " +
				"       language " +
				"FROM   user " +
				"WHERE  user.email=?";

		try (Connection connection = transactionManager.getConnection();
			 PreparedStatement statement = transactionManager.getPreparedStatement(connection, query)){
			statement.setString(1, email);
			UserDao dao = (UserDao) servletContext.getAttribute(USER_DAO);
			return dao.getOne(statement);
		} catch (SQLException exception) {
			log.error(exception.getMessage(), exception);
			throw new AppException(DATABASE_ERROR);
		}
	}

	public Boolean getUserBannedState(String email) throws AppException {
		//language=MySQL
		String query = "SELECT COUNT(*) FROM user WHERE user.email=? and user.status=-1";
		try (Connection connection = transactionManager.getConnection();
			 PreparedStatement statement = transactionManager.getPreparedStatement(connection,query)){
			statement.setString(1, email);
			UserDao dao = (UserDao) servletContext.getAttribute(USER_DAO);
			return dao.getCount(statement)==1;
		} catch (SQLException exception) {
			log.error(exception.getMessage(), exception.getCause());
			throw new AppException(DATABASE_ERROR);
		}
	}

	public boolean addToDB(User user) throws AppException {
		//language=MySQL
		String query = "INSERT INTO USER (user.name, " +
				"                  user.surname, " +
				"                  user.status, " +
				"                  user.language, " +
				"                  user.email, " +
				"                  user.password) " +
				"VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection connection = transactionManager.getConnection();
			 PreparedStatement statement = transactionManager.getPreparedStatement(connection, query)){
			statement.setString(1, user.getName());
			statement.setString(2, user.getSurname());
			statement.setInt(3, user.getStatus());
			statement.setString(4, user.getLanguage());
			statement.setString(5, user.getEmail());
			statement.setString(6, user.getPassword());

			UserDao dao = (UserDao) servletContext.getAttribute(USER_DAO);
			dao.add(statement);
			transactionManager.commit(connection);
		} catch (SQLException exception) {
			log.error(exception.getMessage(), exception);
			throw new AppException(DATABASE_ERROR);
		}
		return true;
	}

	public List<User> getAll() throws AppException {
		//language=MySQL
		String query = "SELECT id, email, password, name, surname, status, language FROM user ORDER BY status DESC";
		try (Connection connection = transactionManager.getConnection();
			 PreparedStatement statement = transactionManager.getPreparedStatement(connection,query)){
			UserDao dao = (UserDao) servletContext.getAttribute(USER_DAO);
			return dao.getAll(statement);
		} catch (SQLException exception) {
			log.error(exception.getMessage(), exception);
			throw new AppException(DATABASE_ERROR);
		}
	}

	public boolean setUserBannedState(int userId, int state) throws AppException{
		//language=MySQL
		String query = "UPDATE user SET user.status=? WHERE user.id=?";
		try (Connection connection = transactionManager.getConnection();
			 PreparedStatement statement = transactionManager.getPreparedStatement(connection, query)){
			statement.setInt(1, state);
			statement.setInt(2, userId);

			UserDao dao = (UserDao) servletContext.getAttribute(USER_DAO);
			boolean result = dao.update(statement)==1;
			if (result) {
				transactionManager.commit(connection);
			} else {
				transactionManager.rollback(connection);
			}
			return result;
		} catch (SQLException exception) {
			log.error(exception.getMessage(), exception);
			throw new AppException(DATABASE_ERROR);
		}
	}
}
