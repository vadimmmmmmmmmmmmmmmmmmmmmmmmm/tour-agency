package com.razkuuuuuuu.touragency.db.dao;

import com.razkuuuuuuu.touragency.entities.entity.TourLocalisationInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TourLocalisationInfoDao implements Dao<TourLocalisationInfo>{
    @Override
    public int add(PreparedStatement statement) throws SQLException {
        return statement.executeUpdate();
    }

    @Override
    public List<TourLocalisationInfo> getAll(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        List<TourLocalisationInfo> list = new ArrayList<>();
        while(resultSet.next()) {
            list.add(parse(resultSet));
        }
        return list;
    }

    private TourLocalisationInfo parse(ResultSet resultSet) throws SQLException {
        TourLocalisationInfo info = new TourLocalisationInfo();
        info.setLocale(resultSet.getString(1));
        info.setTitle(resultSet.getString(2));
        info.setDescription(resultSet.getString(3));
        return info;
    }

    @Override
    public TourLocalisationInfo getOne(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        TourLocalisationInfo info = new TourLocalisationInfo();
        while(resultSet.next()) {
            info = parse(resultSet);
        }
        return info;
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
}
