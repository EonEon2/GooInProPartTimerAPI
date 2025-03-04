package org.gooinpro.gooinproparttimerapi.worklogs.dto;

import lombok.Data;

import java.sql.Time;

@Data
public class WorkLogsInOutDTO {

    private Time wlstartTime;

    private Time wlendTime;
}
