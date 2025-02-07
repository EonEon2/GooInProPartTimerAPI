package org.gooinpro.gooinproparttimerapi.salary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryJobDTO {
    private String jpname;
    private int totalHours;
    private int totalSalary;
    private Timestamp startDate;
    private Timestamp endDate;
}