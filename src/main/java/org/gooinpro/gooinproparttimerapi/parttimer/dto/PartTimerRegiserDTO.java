package org.gooinpro.gooinproparttimerapi.parttimer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String pname;

    @NotNull(message = "생년월일은 필수 입력 항목입니다.")
    private Timestamp pbirth;

    @NotNull(message = "성별은 필수 입력 항목입니다.")
    private boolean pgender;

    @NotBlank(message = "도로명주소는 필수 입력 항목입니다.")
    private String proadAddress;

    private String pdetailAddress;
}