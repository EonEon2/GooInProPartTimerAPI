package org.gooinpro.gooinproparttimerapi.salary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryMonthlyDTO {
    private int year;
    private int month;
    private int totalHours;
    private int totalSalary;
    private String jpname;
}
