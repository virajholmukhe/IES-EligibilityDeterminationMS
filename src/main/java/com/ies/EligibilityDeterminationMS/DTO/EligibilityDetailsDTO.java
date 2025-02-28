package com.ies.EligibilityDeterminationMS.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EligibilityDetailsDTO {

    private Integer id;
    private String planStatus;
    private LocalDate planStartDate;
    private LocalDate planEndDate;
    private Double benefitAmount;
    private String denialReason;
    private String caseNumber;
    private String planName;
}
