package org.gooinpro.gooinproparttimerapi.upload.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadImageDTO {

    private Long imageId;
    private String filename;
    private Long partTimerId;
    private String imageUrl; // 클라이언트에서 이미지를 표시하기 위한 URL
}
