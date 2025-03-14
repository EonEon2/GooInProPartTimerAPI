package org.gooinpro.gooinproparttimerapi.upload.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_partTimerImage")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pino;

    @Column(name = "pifilename")
    private String filename;

    @Column(name = "pno")
    private Long partTimerId;
}