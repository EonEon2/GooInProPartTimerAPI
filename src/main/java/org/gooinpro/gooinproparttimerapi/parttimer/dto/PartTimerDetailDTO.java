package org.gooinpro.gooinproparttimerapi.parttimer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartTimerDetailDTO {
    private Long pno;
    private String pemail;
    private String pname;
    private Date pbirth;
    private boolean pgender;
    private String proadAddress;
    private String pdetailAddress;
    private Timestamp pregdate;
}
