package application;

import model.LayerDto;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbLayerConnector {

    private static Connection conn;
    public static DbLayerConnector instance = null;
    private static BasicDataSource ds = new BasicDataSource();
    private static String SQLCONECTIONMETA = "jdbc:postgresql://localhost:5432/tax_meta";

    /**
     * @return
     * @throws SQLException
     */
    public static DbLayerConnector getLayerInstance() throws SQLException {
        if (instance == null) {

            ds.setUrl(SQLCONECTIONMETA);
            ds.setUsername("postgres");
            ds.setPassword("admin");
            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);
            ds.setFastFailValidation(false);

            instance = new DbLayerConnector();
            return instance;
        } else {
            return instance;
        }
    }

    public List<LayerDto> getLayerData() throws SQLException {

        List<LayerDto> results = new ArrayList<>();
        conn = ds.getConnection();

        StringBuffer query = new StringBuffer();
        query.append(" SELECT layer_type from ");
        query.append(" layer ");

        PreparedStatement st = conn.prepareStatement(query.toString());
        LayerDto dto = new LayerDto();

        try{
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                dto.setLayerType(rs.getString("layer_type"));
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
