package org.gooinpro.gooinproparttimerapi.jobmatchings.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobMatchingsListDTO {

    private Long jmno;              // 매칭 번호
    private String jpname;          // 근무지명 (JobPostings에서 가져올 정보)
    private Timestamp jmregdate;    // 매칭 등록일
    private LocalDate jmstartDate;  // 근무 시작일
    private LocalDate jmendDate;    // 근무 종료일
    private Integer jmhourlyRate;   // 시급
    private String jmworkDays;      // 근무요일
    private LocalTime jmworkStartTime;  // 근무 시작시간
    private LocalTime jmworkEndTime;    // 근무 종료시간
}
