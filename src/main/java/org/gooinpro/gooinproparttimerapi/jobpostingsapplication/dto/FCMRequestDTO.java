package org.gooinpro.gooinproparttimerapi.jobpostingsapplication.dto;

import lombok.Data;

import java.util.List;

@Data
public class FCMRequestDTO {

    private List<String> token; //토큰의 배열

    private String title;   //알림 제목

    private String content; //알림 내용
}
