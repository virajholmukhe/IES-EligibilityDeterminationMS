package com.ies.EligibilityDeterminationMS.DTO;

import lombok.Data;

@Data
public class SummaryDetails {
    private String caseNumber;
    private String planName;
    private IncomeDetailsDTO incomeDetails;
    private EducationDetailsDTO educationDetails;
    private FamilyDetailsDTO familyDetails;
    private BankDetailsDTO bankDetails;
}
