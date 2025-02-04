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
public class PartTimerRegiserDTO {
    private String pname;           // 이름
    private Timestamp pbirth;       // 생년월일
    private boolean pgender;        // 성별
    private String proadAddress;    // 도로명주소
    private String pdetailAddress;  // 상세주소
}
