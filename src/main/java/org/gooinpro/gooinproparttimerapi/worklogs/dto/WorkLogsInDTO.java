package org.gooinpro.gooinproparttimerapi.worklogs.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class WorkLogsInDTO {

    private Timestamp wlstartTime; // 출근시간

    private Integer wlworkStatus; // 0:정상,1:지각,2:조퇴,3:결근
}
