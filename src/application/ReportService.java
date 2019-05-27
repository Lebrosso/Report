package application;

import model.GeneralDataDto;

import java.sql.Date;

public class ReportService {

    DataService data = new DataService();
    ReportGenerator gen = new ReportGenerator();

    public void getCount(Date dateTo, Date dateFrom, String voivodeship, String county, int userId, Table table, GeneralDataDto dto) {
        gen.generate(data.getCount(dateTo,dateFrom,voivodeship,county,userId,table),dto);
    }

    public void getCount(Date dateTo, Date dateFrom, String voivodeship, String county, Table table, GeneralDataDto dto) {
        gen.generate(data.getCount(dateTo,dateFrom,voivodeship,county,table),dto);
    }

    public void getCount(Date dateFrom, Date dateTo, Table table, String terrainAddress, int id, GeneralDataDto dto) {
        gen.generate(data.getCount(dateTo,dateFrom,table),dto);
    }

    public void getCount(Date dateFrom, Date dateTo, Table table, String terrainAddress, GeneralDataDto dto) {
        gen.generate(data.getCount(dateTo,dateFrom,table),dto);
    }

    public void getCount(Date dateFrom, Date dateTo, Table table, GeneralDataDto dto) {
        gen.generate(data.getCount(dateTo,dateFrom,table),dto);
    }

    public void getCount(Date dateFrom, Table table, GeneralDataDto dto) {
        gen.generate(data.getCount(dateFrom, table),dto);
    }

}
