package com.ies.EligibilityDeterminationMS.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CitizenApplicationDTO {

    private String caseNumber;
    private String planId;
    private String planName;

    @NotNull(message = "{citizenName.notnull.invalid}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{citizenName.pattern.invalid}")
    private String citizenName;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "{citizenEmail.pattern.invalid}")
    private String citizenEmail;

    @NotNull(message = "{citizenPhone.notnull.invalid}")
    @Pattern(regexp = "^[0-9]{10}$", message = "{citizenPhone.pattern.invalid}")
    private String citizenPhone;

    @Pattern(regexp = "Male|Female|Other", message = "{citizenGender.pattern.invalid}")
    private String citizenGender;

    @NotNull(message = "{citizenDob.notnull.invalid}")
    @Past(message = "{citizenDob.future.invalid}")
    private LocalDate citizenDob;

    @NotNull(message = "{citizenAadhar.notnull.invalid}")
    @Pattern(regexp = "^[0-9]{12}$", message = "{citizenAadhar.pattern.invalid}")
    private String citizenAadhar;

    private LocalDate createdDate;
    private LocalDate updatedDate;
    private String createdBy;
}
