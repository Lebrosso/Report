package application;

import model.ResultDto;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class DataService {

    private DbConnector conn;

    {
        try {
            conn = DbConnector.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ResultDto> getCount(Date dateTo, Date dateFrom, String voivodeship, String county, int userId, Table table) {
        try {
            return conn.getCount(dateTo, dateFrom, voivodeship, county, userId, table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ResultDto> getCount(Date dateTo, Date dateFrom, String voivodeship, String county, Table table) {
        try {
           return conn.getCount(dateTo, dateFrom, voivodeship, county, table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ResultDto> getCount(Date dateFrom, Date dateTo, Table table, String terrainAddress,int id) {
        try {
            return conn.getCount(dateTo, dateFrom, table, terrainAddress,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ResultDto> getCount(Date dateFrom, Date dateTo, Table table, String terrainAddress) {
        try {
            return conn.getCount(dateTo, dateFrom, table, terrainAddress);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ResultDto> getCount(Date dateFrom, Date dateTo, Table table) {
        try {
            return conn.getCount(dateTo, dateFrom, table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ResultDto> getCount(Date dateFrom , Table table) {
        try {
            return conn.getCount(dateFrom, table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
