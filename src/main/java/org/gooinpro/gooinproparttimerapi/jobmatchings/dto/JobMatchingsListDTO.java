package org.gooinpro.gooinproparttimerapi.jobmatchings.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobMatchingsListDTO {
    private Long jmno;
    private String jpname;
    private Timestamp jmregdate;
    private Timestamp jmstartDate;
    private Timestamp jmendDate;
    private Integer jmhourlyRate;
    private String jmworkDays;
    private Timestamp jmworkStartTime;
    private Timestamp jmworkEndTime;

    private String wroadAddress;
    private String wdetailAddress;
}
