package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralDataDto {
   private String observationPeriod;
   private String monitoringPeriod;
   private String address;
   private String doneBy;
}
