package application;

import model.GeneralDataDto;
import model.LayerDto;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ReportService {

    DbConnector conn;
    DbLayerConnector layerConn;

    {
        try {
            conn = DbConnector.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    DataService data = new DataService();
    ReportGenerator gen = new ReportGenerator();
    FileZipper zipper =  new FileZipper();

    public void getCount(Date dateTo, Date dateFrom, String voivodeship, String county, int userId, Table table, GeneralDataDto dto) throws IOException {
        zipper.zipFile(gen.generate(data.getCount(dateTo,dateFrom,voivodeship,county,userId,table),dto));
    }

    public void getCount(Date dateTo, Date dateFrom, String voivodeship, String county, Table table, GeneralDataDto dto) throws IOException {
        zipper.zipFile(gen.generate(data.getCount(dateTo,dateFrom,voivodeship,county,table),dto));
    }

    public void getCount(Date dateFrom, Date dateTo, Table table, String terrainAddress, int id, GeneralDataDto dto) throws IOException {
        zipper.zipFile(gen.generate(data.getCount(dateTo,dateFrom,table,terrainAddress,id),dto));
    }

    public void getCount(Date dateFrom, Date dateTo, Table table, String terrainAddress, GeneralDataDto dto) throws IOException {
        zipper.zipFile(gen.generate(data.getCount(dateTo,dateFrom,table,terrainAddress),dto));
    }

    public void getCount(Date dateFrom, Date dateTo, Table table, GeneralDataDto dto) throws IOException {
        zipper.zipFile(gen.generate(data.getCount(dateTo,dateFrom,table),dto));
    }

    public void getCount(Date dateFrom, Table table, GeneralDataDto dto) throws IOException {
        zipper.zipFile(gen.generate(data.getCount(dateFrom, table),dto));
    }

    public List<LayerDto> getLayers() throws  SQLException {
        layerConn = DbLayerConnector.getLayerInstance();
        return layerConn.getLayerData();
    }
}
