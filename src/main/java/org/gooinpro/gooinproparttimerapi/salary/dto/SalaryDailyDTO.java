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
public class SalaryDailyDTO {
    private Timestamp workDate;  // 근무일
    private int hours;           // 근무 시간
    private int salary;          // 일급
    private String jpname;       // 근무지 이름
    private Long jmno;           // 근무지 매칭 번호
}
