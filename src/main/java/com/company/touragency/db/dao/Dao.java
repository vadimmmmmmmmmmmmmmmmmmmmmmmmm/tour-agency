package com.razkuuuuuuu.touragency.db.dao;

import java.sql.*;
import java.util.List;

public interface Dao<T> {
    int add(PreparedStatement statement) throws SQLException;
    List<T> getAll(PreparedStatement statement) throws SQLException;

    T getOne(PreparedStatement statement) throws SQLException;

    int update(PreparedStatement statement) throws SQLException;

    int getCount(PreparedStatement statement) throws SQLException;
}