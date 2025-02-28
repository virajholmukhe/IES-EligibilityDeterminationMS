package com.ies.EligibilityDeterminationMS.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EducationDetailsDTO {
    private Integer id;
    @NotNull(message = "{caseNumber.notnull.invalid}")
    private String caseNumber;
    @NotNull(message = "{highestQualification.notnull.invalid}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{highestQualification.pattern.invalid}")
    private String highestQualification;
    @NotNull(message = "{qualificationYear.notnull.invalid}")
    @Pattern(regexp = "^[0-9]*$", message = "{qualificationYear.pattern.invalid}")
    private String qualificationYear;
    @NotNull(message = "{qualificationUniversity.notnull.invalid}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{qualificationUniversity.pattern.invalid}")
    private String qualificationUniversity;
}
