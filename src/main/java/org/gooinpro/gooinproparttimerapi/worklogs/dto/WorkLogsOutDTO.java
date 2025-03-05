package org.gooinpro.gooinproparttimerapi.worklogs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkLogsOutDTO {

    private Timestamp wlendTime;

    private Integer wlworkStatus;
}
