package org.gooinpro.gooinproparttimerapi.employer.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tbl_employer")
public class EmployerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eno;   //PK

    @Column(nullable = false)
    private String eemail;   //email

    @Column(nullable = false)
    private String epw; //password

    @Column(nullable = false)
    private String ename;   //name

    private Date ebirth;    //birth

    private boolean egender;    //gender(true = male, false = female)

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean edelete = false;    //delflag(true = deleted)

    @Column(columnDefinition = "timestamp default now()", nullable = false)
    private Timestamp eregdate; //등록 시간

    @Column(columnDefinition = "VARCHAR(100)")
    private String etoken;    //FCM Token
}
