package com.ies.EligibilityDeterminationMS.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CorrespondenceInfoDTO {

    private String planName;
    private String planStatus;
    private LocalDate planStartDate;
    private LocalDate planEndDate;
    private Double benefitAmount;
    private String denialReason;
    private LocalDate generationDate;
}
