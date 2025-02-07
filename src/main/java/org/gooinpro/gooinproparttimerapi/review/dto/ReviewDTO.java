package org.gooinpro.gooinproparttimerapi.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long rno;
    private Long pno;
    private Long eno;
    private int rstart;
    private String rcontent;

}
