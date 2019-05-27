package test;

import application.FileZipper;
import application.ReportGenerator;
import model.ResultDto;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertNotNull;

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
        String location = generator.generate(list, null, null, null, null);
        File l = new File(location);
        assertNotNull(l);
        l.delete();
    }

    @Test
    public void testZip(){
        ReportGenerator generator = new ReportGenerator();
        FileZipper zipper = new FileZipper();
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
        String location = generator.generate(list,null,null,null,null);
        try {
            zipper.zipFile(location);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File l = new File(location);
        assertNotNull(l);
    }

}
