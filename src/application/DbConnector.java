package application;

import model.ResultDto;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbConnector {

    private static Connection conn;
    public static DbConnector instance = null;
    private static BasicDataSource ds = new BasicDataSource();
    private static String SQLCONECTIONGIS = "jdbc:postgresql://localhost:5432/tax_gis";


    private DbConnector() {
    }

    /**
     * @return
     * @throws SQLException
     */
    public static DbConnector getInstance() throws SQLException {
        if (instance == null) {

            ds.setUrl(SQLCONECTIONGIS);
            ds.setUsername("postgres");
            ds.setPassword("admin");
            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);
            ds.setFastFailValidation(false);

            instance = new DbConnector();
            return instance;
        } else {
            return instance;
        }
    }

    /**
     *
     * Pobieranie danych dla dat od i do i adresu administracyjnego i id użytkownika.
     *
     * @param dateTo
     * @param dateFrom
     * @param voivodeship
     * @param county
     * @param userId
     * @param table
     * @return
     * @throws SQLException
     */
    public List<ResultDto> getCount(Date dateFrom, Date dateTo, String voivodeship, String county, int userId, Table table) throws SQLException {

        List<ResultDto> results = new ArrayList<>();
        conn = ds.getConnection();

        StringBuffer query = new StringBuffer();
        query.append(" SELECT count(*) from ");
        query.append(table.getName());
        query.append(" where ");
        query.append(" ST_Intersects (geom,(select ST_Union(geom) from dzew_pol where  woj  = ? and powiat = ?))");
        query.append(" AND ");
        query.append(" datobs <= ? ");
        query.append(" AND ");
        query.append(" datobs >= ? ");
        query.append(" AND ");
        query.append(" user_id = ? ");

        PreparedStatement st = conn.prepareStatement(query.toString());
        ResultDto dto = new ResultDto();

        try {
            st.setString(1, voivodeship);
            st.setString(2, county);
            st.setDate(3, dateTo);
            st.setDate(4, dateFrom);
            st.setInt(5, userId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                dto.setName(table.getDisplayName());
                dto.setObservation(rs.getString("count"));
                results.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuffer tableMonitoring = new StringBuffer();
        tableMonitoring.append(table);
        tableMonitoring.append("__m");

        StringBuffer queryMonitoring = new StringBuffer();
        queryMonitoring.append(" SELECT count(*) from ");
        queryMonitoring.append(tableMonitoring.toString());
        queryMonitoring.append(" where ");
        queryMonitoring.append(" ST_Intersects (geom,(select ST_Union(geom) from dzew_pol where  woj  = ? and powiat = ?))");
        queryMonitoring.append(" AND ");
        queryMonitoring.append(" datobs <= ? ");
        queryMonitoring.append(" AND ");
        queryMonitoring.append(" datobs >= ? ");
        query.append(" user_id = ? ");

        try {
            st.setString(1, voivodeship);
            st.setString(2, county);
            st.setDate(3, dateTo);
            st.setDate(4, dateFrom);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                dto.setMonitoring(rs.getString("count"));
                results.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        st.close();
        conn.close();
        return results;
    }

    /**
     *
     *  Pobieranie danych dla dat od i do i adresu administracyjnego.
     *
     * @param dateTo
     * @param dateFrom
     * @param voivodeship
     * @param county
     * @param table
     * @return
     * @throws SQLException
     */
    public List<ResultDto> getCount(Date dateFrom, Date dateTo, String voivodeship, String county, Table table) throws SQLException {

        List<ResultDto> results = new ArrayList<>();
        conn = ds.getConnection();

        StringBuffer query = new StringBuffer();
        query.append(" SELECT count(*) from ");
        query.append(table.getName());
        query.append(" where ");
        query.append(" ST_Intersects (geom,(select ST_Union(geom) from dzew_pol where  woj = ? and powiat = ?))");
        query.append(" AND ");
        query.append(" datobs <= ? ");
        query.append(" AND ");
        query.append(" datobs >= ? ");

        PreparedStatement st = conn.prepareStatement(query.toString());
        ResultDto dto = new ResultDto();

        try {
            st.setString(1, voivodeship);
            st.setString(2, county);
            st.setDate(3, dateTo);
            st.setDate(4, dateFrom);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                dto.setObservation(rs.getString("count"));
                results.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuffer tableMonitoring = new StringBuffer();
        tableMonitoring.append(table);
        tableMonitoring.append("__m");

        StringBuffer queryMonitoring = new StringBuffer();
        queryMonitoring.append(" SELECT count(*) from ");
        queryMonitoring.append(tableMonitoring.toString());
        queryMonitoring.append(" where ");
        queryMonitoring.append(" ST_Intersects (geom,(select ST_Union(geom) from dzew_pol where  woj  = ? and powiat = ?))");
        queryMonitoring.append(" AND ");
        queryMonitoring.append(" datobs <= ? ");
        queryMonitoring.append(" AND ");
        queryMonitoring.append(" datobs >= ? ");

        try {
            st.setString(1, voivodeship);
            st.setString(2, county);
            st.setDate(3, dateFrom);
            st.setDate(4, dateTo);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                dto.setMonitoring(rs.getString("count"));
                results.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        st.close();
        conn.close();
        return results;
    }

    /**
     *
     *  Pobieranie danych dla dat od i do i adresu leśniego.
     *
     * @param dateFrom
     * @param dateTo
     * @param table
     * @return
     * @throws SQLException
     */
    public List<ResultDto> getCount(Date dateFrom, Date dateTo, Table table, String terrainAddress) throws SQLException {

        List<ResultDto> results = new ArrayList<>();
        conn = ds.getConnection();

        StringBuffer query = new StringBuffer();
        query.append(" SELECT count(*) from ");
        query.append(table.getName());
        query.append(" where ");
        query.append(" ST_Intersects (geom,(select st_union(geom) from les_pol where adr_les LIKE ?)) ");
        query.append(" AND ");
        query.append(" datobs <= ? ");
        query.append(" AND ");
        query.append(" datobs >= ? ");

        PreparedStatement st = conn.prepareStatement(query.toString());
        ResultDto dto = new ResultDto();

        try {
            st.setString(1, terrainAddress);
            st.setDate(2, dateFrom);
            st.setDate(3, dateTo);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                dto.setObservation(rs.getString("count"));
                results.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuffer tableMonitoring = new StringBuffer();
        tableMonitoring.append(table);
        tableMonitoring.append("__m");

        StringBuffer queryMonitoring = new StringBuffer();
        queryMonitoring.append(" SELECT count(*) from ");
        queryMonitoring.append(tableMonitoring.toString());
        queryMonitoring.append(" where ");
        queryMonitoring.append(" ST_Intersects (geom,(select st_union(geom) from les_pol where adr_les LIKE ?)) ");
        queryMonitoring.append(" AND ");
        queryMonitoring.append(" datobs <= ? ");
        queryMonitoring.append(" AND ");
        queryMonitoring.append(" datobs >= ? ");

        try {
            st.setString(1, terrainAddress);
            st.setDate(3, dateTo);
            st.setDate(4, dateFrom);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                dto.setMonitoring(rs.getString("count"));
                results.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        st.close();
        conn.close();
        return results;
    }

    /**
     *
     *  Pobieranie danych dla dat od i do i adresu leśniego i id użytkownika.
     *
     * @param dateFrom
     * @param dateTo
     * @param table
     * @return
     * @throws SQLException
     */
    public List<ResultDto> getCount(Date dateFrom, Date dateTo, Table table, String terrainAdress, int userId) throws SQLException {

        List<ResultDto> results = new ArrayList<>();
        conn = ds.getConnection();

        StringBuffer query = new StringBuffer();
        query.append(" SELECT count(*) from ");
        query.append(table.getName());
        query.append(" where ");
        query.append(" ST_Intersects (geom,(select st_union(geom) from les_pol where adr_les LIKE ?)) ");
        query.append(" AND ");
        query.append(" datobs <= ? ");
        query.append(" AND ");
        query.append(" datobs >= ? ");
        query.append(" user_id = ? ");

        PreparedStatement st = conn.prepareStatement(query.toString());
        ResultDto dto = new ResultDto();

        try {
            st.setString(1, terrainAdress);
            st.setDate(2, dateFrom);
            st.setDate(3, dateTo);
            st.setInt(5, userId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                dto.setObservation(rs.getString("count"));
                results.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuffer tableMonitoring = new StringBuffer();
        tableMonitoring.append(table);
        tableMonitoring.append("__m");

        StringBuffer queryMonitoring = new StringBuffer();
        queryMonitoring.append(" SELECT count(*) from ");
        queryMonitoring.append(tableMonitoring.toString());
        queryMonitoring.append(" where ");
        queryMonitoring.append(" ST_Intersects (geom,(select st_union(geom) from les_pol where adr_les LIKE ?)) ");
        queryMonitoring.append(" AND ");
        queryMonitoring.append(" datobs <= ? ");
        queryMonitoring.append(" AND ");
        queryMonitoring.append(" datobs >= ? ");
        queryMonitoring.append(" user_id = ? ");

        try {
            st.setString(1, terrainAdress);
            st.setDate(3, dateFrom);
            st.setDate(4, dateTo);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                dto.setMonitoring(rs.getString("count"));
                results.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        st.close();
        conn.close();
        return results;
    }

    /**
     *
     *  Pobieranie danych dla dat od i do.
     *
     * @param dateFrom
     * @param dateTo
     * @param table
     * @return
     * @throws SQLException
     */
    public List<ResultDto> getCount(Date dateFrom, Date dateTo, Table table) throws SQLException {

        List<ResultDto> results = new ArrayList<>();
        conn = ds.getConnection();

        StringBuffer query = new StringBuffer();
        query.append(" SELECT count(*) from ");
        query.append(table.getName());
        query.append(" where ");
        query.append(" ST_Intersects (geom,(select st_union(geom) from les_pol where adr_les LIKE ?)) ");
        query.append(" AND ");
        query.append(" datobs <= ? ");
        query.append(" AND ");
        query.append(" datobs >= ? ");

        PreparedStatement st = conn.prepareStatement(query.toString());
        ResultDto dto = new ResultDto();

        try {
            st.setDate(1, dateFrom);
            st.setDate(2, dateTo);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                dto.setObservation(rs.getString("count"));
                results.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuffer tableMonitoring = new StringBuffer();
        tableMonitoring.append(table);
        tableMonitoring.append("__m");

        StringBuffer queryMonitoring = new StringBuffer();
        queryMonitoring.append(" SELECT count(*) from ");
        queryMonitoring.append(tableMonitoring.toString());
        queryMonitoring.append(" where ");
        queryMonitoring.append(" ST_Intersects (geom,(select st_union(geom) from les_pol where adr_les LIKE ?)) ");
        queryMonitoring.append(" AND ");
        queryMonitoring.append(" datobs <= ? ");
        queryMonitoring.append(" AND ");
        queryMonitoring.append(" datobs >= ? ");

        try {
            st.setDate(1, dateFrom);
            st.setDate(2, dateTo);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                dto.setMonitoring(rs.getString("count"));
                results.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        st.close();
        conn.close();
        return results;
    }


    /**
     *
     *  Pobieranie danych dla dat od.
     *
     * @param dateFrom
     * @param table
     * @return
     * @throws SQLException
     */
    public List<ResultDto> getCount(Date dateFrom, Table table) throws SQLException {

        List<ResultDto> results = new ArrayList<>();
        conn = ds.getConnection();

        StringBuffer query = new StringBuffer();
        query.append(" SELECT count(*) from ");
        query.append(table.getName());
        query.append(" where ");
        query.append(" datobs <= ? ");

        PreparedStatement st = conn.prepareStatement(query.toString());
        ResultDto dto = new ResultDto();

        try {
            st.setDate(1, dateFrom);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                dto.setObservation(rs.getString("count"));
                results.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        st.close();
        conn.close();
        return results;
    }


}


