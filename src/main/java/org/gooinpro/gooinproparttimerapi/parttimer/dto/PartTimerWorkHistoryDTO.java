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
public class PartTimerWorkHistoryDTO {

    // tbl_jobMatchings
    private Long jmno;              // 매칭 번호
    private Timestamp jmdate;   // 매칭 날짜

    // tbl_workLogs
    private Timestamp wcheckInTime;    // 출근시간
    private Timestamp wcheckOutTime;   // 퇴근시간
    private String wcheckStatus;           // 근무상태
    private Timestamp wbeginRegularTime;  // 정규 출근시간
    private Timestamp wbeginEndTime;      // 정규 퇴근시간
}