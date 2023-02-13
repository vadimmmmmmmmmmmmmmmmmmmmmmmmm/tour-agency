package com.razkuuuuuuu.touragency.db.dao;

import com.razkuuuuuuu.touragency.entities.entity.Hotel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDao implements Dao<Hotel>{
    @Override
    public int add(PreparedStatement statement) throws SQLException {
        return statement.executeUpdate();
    }

    @Override
    public List<Hotel> getAll(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        List<Hotel> list = new ArrayList<>();
        while(resultSet.next()) {
            list.add(parse(resultSet));
        }
        return list;
    }

    @Override
    public Hotel getOne(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        Hotel hotel = null;
        while (resultSet.next()) {
            hotel = parse(resultSet);
        }
        return hotel;
    }

    @Override
    public int update(PreparedStatement statement) throws SQLException {
        return statement.executeUpdate();
    }

    @Override
    public int getCount(PreparedStatement statement) throws SQLException {
        return statement.executeUpdate();
    }

    private Hotel parse(ResultSet resultSet) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setId(resultSet.getInt(1));
        hotel.setName(resultSet.getString(2));
        return hotel;
    }
    public int getLastAddedId(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
        int id = 0;
        while (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        return id;
    }
}
