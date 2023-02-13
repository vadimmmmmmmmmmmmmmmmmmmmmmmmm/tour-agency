package com.razkuuuuuuu.touragency.db.dao;

import com.razkuuuuuuu.touragency.entities.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User> {

	@Override
	public int add(PreparedStatement statement) throws SQLException {
		return statement.executeUpdate();
	}

	@Override
	public List<User> getAll(PreparedStatement statement) throws SQLException {
		ResultSet resultSet = statement.executeQuery();
		List<User> users = new ArrayList<>();
		while (resultSet.next()) {
			User user = parse(resultSet);
			users.add(user);
		}
		resultSet.close();
		return users;
	}

	@Override
	public User getOne(PreparedStatement statement) throws SQLException {
		User user = null;
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			user = parse(resultSet);
		}
		resultSet.close();
		return user;
	}
	@Override
	public int update(PreparedStatement statement) throws SQLException {
		return statement.executeUpdate();
	}

	private User parse(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getInt("id"));
		user.setEmail(resultSet.getString("email"));
		user.setPassword(resultSet.getString("password"));
		user.setName(resultSet.getString("name"));
		user.setSurname(resultSet.getString("surname"));
		user.setStatus(resultSet.getInt("status"));
		user.setLanguage(resultSet.getString("language"));
		return user;
	}

	@Override
	public int getCount(PreparedStatement statement) throws SQLException {
		ResultSet resultSet = statement.executeQuery();
		int count = 0;
		while(resultSet.next()) {
			count = resultSet.getInt(1);
		}
		return count;
	}
}
