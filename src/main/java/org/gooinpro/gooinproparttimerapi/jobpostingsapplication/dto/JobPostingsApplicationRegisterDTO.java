package org.gooinpro.gooinproparttimerapi.jobpostingsapplication.dto;

import lombok.Data;

@Data
public class JobPostingsApplicationRegisterDTO {

    private Long jpano;

    private String jpacontent;

    private int jpahourlyRate;

    private boolean jpadelete;

    private Long pno;

    private Long jpno;
}
