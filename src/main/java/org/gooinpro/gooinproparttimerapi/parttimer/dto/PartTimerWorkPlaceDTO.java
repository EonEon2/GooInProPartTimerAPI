package org.gooinpro.gooinproparttimerapi.parttimer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartTimerWorkPlaceDTO {

    // tbl_jobMatchings
    private Long jmno;              // 매칭 번호
    private String jmstatus;        // 매칭 상태
    private Timestamp jmdate;   // 매칭 날짜

    // tbl_jobPostings
    private Integer jphourlyWage;   // 시급
    private String jpname;          // 근무지명
}