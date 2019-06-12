package test;

import application.ReportGenerator;
import application.ReportService;
import application.Table;
import model.GeneralDataDto;
import model.ResultDto;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ReportTest {

    @Test
    public void testReport() {
        ReportGenerator generator = new ReportGenerator();
        List<ResultDto> list = new ArrayList<>();
        ResultDto dto = new ResultDto();
        dto.setMonitoring("monitoring1");
        dto.setObservation("observation2");
        dto.setName("name3");
        ResultDto dto1 = new ResultDto();
        dto1.setMonitoring("monitoring4");
        dto1.setObservation("observation5");
        dto1.setName("name6");
        list.add(dto);
        list.add(dto1);
        String location = generator.generate(list,new GeneralDataDto());
        File l = new File(location);
        assertNotNull(l);
        l.delete();
    }

    @Test
    public void testService() throws IOException {
        ReportService service = new ReportService();
        GeneralDataDto dto = new GeneralDataDto();
        Table table = new Table();
        table.setName("lelek");
        table.setDisplayName("Lelek");
        service.getCount(new Date(1541462400),table,dto);
    }

    @Test
    public void testLayers() throws SQLException {
        ReportService service = new ReportService();
        assertTrue(service.getLayers().size() > 0);
    }
}
