package application;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import model.GeneralDataDto;
import model.ResultDto;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReportGenerator {

    private String fileLocation = "";
    private Date generated = new Date();
    String timeGenerated =new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(generated);

    public String generate(List<ResultDto> records, GeneralDataDto dto) {

        WritableWorkbook reportWorkBook = null;
        try {
            fileLocation = "Raport" + new Date().getTime() + ".xlsx";
            reportWorkBook = Workbook.createWorkbook(new File(fileLocation));
            WritableSheet excelSheet = reportWorkBook.createSheet("Zakładka 1", 0);

            Label top = new Label(10, 0, "Ogólny raport monitoringowy");
            excelSheet.addCell(top);
            top = new Label(10, 1, "Okres obserwacji do:");
            excelSheet.addCell(top);
            top = new Label(10, 2, "Okres monitoringu do:");
            excelSheet.addCell(top);
            top = new Label(10, 3, "Wykonał/a:");
            excelSheet.addCell(top);
            top = new Label(10, 4, "Adres:");
            excelSheet.addCell(top);
            top = new Label(10, 5, "Wygenerowano: " + timeGenerated);
            excelSheet.addCell(top);

            Label label1 = new Label(0, 6, "lp.");
            excelSheet.addCell(label1);
            label1 = new Label(1, 6, "Nazwa");
            excelSheet.addCell(label1);
            label1 = new Label(2, 6, "Obserwacje");
            excelSheet.addCell(label1);
            label1 = new Label(3, 6, "Monitoring");
            excelSheet.addCell(label1);

            int row = 1;
            int gap = 6;
            int footer = 0;

            for (int i = 0; i < records.size(); i++) {
                label1 = new Label(0, i + gap, Integer.toString(row));
                excelSheet.addCell(label1);
                label1 = new Label(1, i + gap, records.get(i).getName());
                excelSheet.addCell(label1);
                label1 = new Label(2, i + gap, records.get(i).getObservation());
                excelSheet.addCell(label1);
                label1 = new Label(3, i + gap, records.get(i).getMonitoring());
                excelSheet.addCell(label1);
                row++;
                footer=i+gap;
            }

            label1 = new Label(0, footer+1, "Ogółem");
            excelSheet.addCell(label1);
            label1 = new Label(0, footer+2, "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            excelSheet.addCell(label1);
            label1 = new Label(5, footer+3, "obserwacje:   monitoring:");
            excelSheet.addCell(label1);
            reportWorkBook.write();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {

            if (reportWorkBook != null) {
                try {
                    reportWorkBook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return fileLocation;
    }
}

