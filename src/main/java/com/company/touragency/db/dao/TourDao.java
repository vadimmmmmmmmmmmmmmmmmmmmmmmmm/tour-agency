package com.razkuuuuuuu.touragency.db.dao;

import com.razkuuuuuuu.touragency.entities.entity.Tour;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TourDao implements Dao<Tour>{
    @Override
    public int add(PreparedStatement statement) throws SQLException {
        return statement.executeUpdate();
    }

    @Override
    public List<Tour> getAll(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        List<Tour> tours = new ArrayList<>();
        while (resultSet.next()) {
            Tour tour = parse(resultSet);
            tours.add(tour);
        }
        resultSet.close();
        return tours;
    }

    @Override
    public Tour getOne(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        Tour tour = null;
        while (resultSet.next()) {
            tour = parse(resultSet);
        }
        return tour;
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

    private Tour parse(ResultSet resultSet) throws SQLException{
        Tour tour = new Tour();
        tour.setId(resultSet.getInt(1));
        tour.setOnFire(resultSet.getBoolean(2));
        tour.setImageFile(resultSet.getString(3));
        tour.setTitle(resultSet.getString(4));
        tour.setDescription(resultSet.getString(5));
        tour.setType(resultSet.getString(6));
        tour.setTicketPrice(resultSet.getBigDecimal(7));
        tour.setTicketCount(resultSet.getInt(8));
        tour.setRegisteredTicketsCount(resultSet.getInt(9));
        tour.setPaidTicketsCount(resultSet.getInt(10));
        tour.setDepartureTakeoffDate(resultSet.getDate(11));
        tour.setDepartureTakeoffTime(resultSet.getTime(12));
        tour.setReturnTakeoffDate(resultSet.getDate(13));
        tour.setReturnTakeoffTime(resultSet.getTime(14));
        tour.setCityName(resultSet.getString(15));
        tour.setHotelName(resultSet.getString(16));
        tour.setHotelRating(resultSet.getInt(17));
        tour.setDiscountAmount(resultSet.getInt(18));
        tour.setDiscountPerEveryTicketsCount(resultSet.getInt(19));
        tour.setMaxDiscount(resultSet.getInt(20));
        tour.setTourRating(resultSet.getInt(21));
        return tour;
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

    public List<Tour> getAllExtended(PreparedStatement statement) throws SQLException{
        ResultSet resultSet = statement.executeQuery();
        List<Tour> tours = new ArrayList<>();
        while (resultSet.next()) {
            Tour tour = extendedParse(resultSet);
            tours.add(tour);
        }
        resultSet.close();
        return tours;
    }

    private Tour extendedParse(ResultSet resultSet) throws SQLException{
        Tour tour = new Tour();
        tour.setId(resultSet.getInt(1));
        tour.setOnFire(resultSet.getBoolean(2));
        tour.setImageFile(resultSet.getString(3));
        tour.setEnTitle(resultSet.getString(4));
        tour.setEnDescription(resultSet.getString(5));
        tour.setUaTitle(resultSet.getString(6));
        tour.setUaDescription(resultSet.getString(7));
        tour.setRuTitle(resultSet.getString(8));
        tour.setRuDescription(resultSet.getString(9));
        tour.setType(resultSet.getString(10));
        tour.setTicketPrice(resultSet.getBigDecimal(11));
        tour.setTicketCount(resultSet.getInt(12));
        tour.setRegisteredTicketsCount(resultSet.getInt(13));
        tour.setPaidTicketsCount(resultSet.getInt(14));
        tour.setDepartureTakeoffDate(resultSet.getDate(15));
        tour.setDepartureTakeoffTime(resultSet.getTime(16));
        tour.setReturnTakeoffDate(resultSet.getDate(17));
        tour.setReturnTakeoffTime(resultSet.getTime(18));
        tour.setCityName(resultSet.getString(19));
        tour.setHotelName(resultSet.getString(20));
        tour.setHotelRating(resultSet.getInt(21));
        tour.setDiscountAmount(resultSet.getInt(22));
        tour.setDiscountPerEveryTicketsCount(resultSet.getInt(23));
        tour.setMaxDiscount(resultSet.getInt(24));
        tour.setTourRating(resultSet.getInt(25));
        return tour;
    }

    public Tour getOneExtended(PreparedStatement statement) throws SQLException{
        ResultSet resultSet = statement.executeQuery();
        Tour tour = null;
        while (resultSet.next()) {
            tour = extendedParse(resultSet);
        }
        return tour;
    }
}
