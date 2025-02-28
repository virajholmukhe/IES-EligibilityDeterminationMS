package com.ies.EligibilityDeterminationMS.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class KidDTO {
    private Integer kidId;
    @NotNull(message = "{kidName.notnull.invalid}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{kidName.pattern.invalid}")
    private String kidName;
    @PastOrPresent(message = "{kidDob.pastOrPresent.invalid}")
    private LocalDate kidDob;
    @NotNull(message = "{kidGender.notnull.invalid}")
    @Pattern(regexp = "Male|Female|Other", message = "{kidGender.pattern.invalid}")
    private String kidGender;
    @NotNull(message = "{kidAadhar.notnull.invalid}")
    @Pattern(regexp = "[0-9]{12}", message = "{kidAadhar.pattern.invalid}")
    private String kidAadhar;
}
