package org.gooinpro.gooinproparttimerapi.worklogs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Time;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkLogsDTO {
    private Long wlno;
    private Long jmno;          // JobMatching 번호
    private Timestamp wlstartTime;
    private Timestamp wlendTime;
    private Integer wlworkStatus;
    private Timestamp wlregdate;
    private Time wlchangedStartTime;
    private Time wlchangedEndTime;
    private boolean wldelete;
    private Long eno;           // Employer 번호
    private Long pno;           // PartTimer 번호
    private String jpname;      // 근무지명 (JobPosting에서 가져올 정보)
    private Integer jmhourlyRate;
}
