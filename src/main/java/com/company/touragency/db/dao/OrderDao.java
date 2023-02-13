package com.razkuuuuuuu.touragency.db.dao;

import com.razkuuuuuuu.touragency.entities.entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao implements Dao<Order>{
    @Override
    public int add(PreparedStatement statement) throws SQLException {
        return statement.executeUpdate();
    }
    @Override
    public List<Order> getAll(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        List<Order> orderList = new ArrayList<>();
        while(resultSet.next()) {
            Order order = parse(resultSet);
            orderList.add(order);
        }
        return orderList;
    }

    private Order parse(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getInt("order_id"));
        order.setTourId(resultSet.getInt("tour_id"));
        order.setTourType(resultSet.getString("tour_type"));
        order.setTourImage(resultSet.getString("tour_image"));
        order.setTourTitle(resultSet.getString("tour_title"));
        order.setUserId(resultSet.getInt("user_id"));
        order.setUserFullName(resultSet.getString("user_full_name"));
        order.setTicketCount(resultSet.getInt("ticket_count"));
        order.setTotalPrice(resultSet.getBigDecimal("total_price"));
        order.setStatus(resultSet.getString("state"));
        return order;
    }

    @Override
    public Order getOne(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        Order order = null;
        while(resultSet.next()) {
            order = parse(resultSet);
        }
        if (order==null) throw new SQLException();

        return order;
    }

    @Override
    public int update(PreparedStatement statement) throws SQLException {
        return statement.executeUpdate();
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

    public int getId(PreparedStatement statement) throws SQLException{
        ResultSet resultSet = statement.executeQuery();
        int id=0;
        while (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        resultSet.close();
        return id;
    }
}
