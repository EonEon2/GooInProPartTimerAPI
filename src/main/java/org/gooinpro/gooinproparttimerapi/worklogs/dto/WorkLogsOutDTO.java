package org.gooinpro.gooinproparttimerapi.worklogs.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class WorkLogsOutDTO {

    private Timestamp wlendTime;

    private Integer wlworkStatus;
}
