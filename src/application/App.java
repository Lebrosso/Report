package application;

import model.ResultDto;

import java.sql.Date;
import java.util.List;

public class App {
    public static void main (String[]args){
       // new ReportGenerator().generate();
        Table tab = new Table();
        tab.setDisplayName("Lelek");
        tab.setName("lelek");
        //1541462400
        //1556582400
        List<ResultDto> results = new DataService().getCount(new Date(1541462400),new Date(1556582400),"2","2",tab);
    }
}
