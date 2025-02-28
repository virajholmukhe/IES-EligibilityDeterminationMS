package com.ies.EligibilityDeterminationMS.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class FamilyDetailsDTO {
    private Integer id;
    @NotNull(message = "{caseNumber.notnull.invalid}")
    private String caseNumber;
    private List<KidDTO> kids;
}
