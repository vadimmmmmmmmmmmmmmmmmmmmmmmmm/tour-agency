package com.razkuuuuuuu.touragency.db.dao;

import com.razkuuuuuuu.touragency.entities.entity.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDao implements Dao<City>{
    @Override
    public int add(PreparedStatement statement) throws SQLException {
        return statement.executeUpdate();
    }

    @Override
    public List<City> getAll(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        List<City> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(parse(resultSet));
        }
        return list;
    }
    private City parse(ResultSet resultSet) throws SQLException {
        City city = new City();
        city.setId(resultSet.getInt(1));
        city.setName(resultSet.getString(2));
        return city;
    }

    @Override
    public City getOne(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        City city = new City();
        while (resultSet.next()) {
            city.setId(resultSet.getInt(1));
        }
        return city;
    }

    @Override
    public int update(PreparedStatement statement) throws SQLException {
        return statement.executeUpdate();
    }

    @Override
    public int getCount(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        int count=0;
        while (resultSet.next()) {
            count = resultSet.getInt(1);
        }
        resultSet.close();
        return count;
    }

    public int getId(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        int id=0;
        while (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        resultSet.close();
        return id;
    }
}
