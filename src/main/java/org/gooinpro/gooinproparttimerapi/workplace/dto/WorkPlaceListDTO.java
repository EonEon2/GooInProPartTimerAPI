package org.gooinpro.gooinproparttimerapi.workplace.dto;

import lombok.Data;

@Data
public class WorkPlaceListDTO {

    private Long wpno;

    private String wroadAddress;

    private String wdetailAddress;

    private String wlati; // 위도

    private String wlong; // 경도

    private boolean wdelete;


}
