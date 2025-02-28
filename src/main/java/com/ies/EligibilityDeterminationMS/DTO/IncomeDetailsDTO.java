package com.ies.EligibilityDeterminationMS.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IncomeDetailsDTO {
    private Integer id;
    @NotNull(message = "{caseNumber.notnull.invalid}")
    private String caseNumber;
    private Double salaryIncome;
    private Double businessIncome;
    private Double rentIncome;
    private Double propertyIncome;
}
