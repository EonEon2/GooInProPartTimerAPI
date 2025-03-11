package org.gooinpro.gooinproparttimerapi.parttimerdocumentimage.dto;


import lombok.Data;

import java.util.List;

@Data
public class PartTimerDocumentImageRegisterDTO {

    private List<String> pdifilename;

    private Long pno;
}
