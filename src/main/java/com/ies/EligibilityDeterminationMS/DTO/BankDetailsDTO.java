package com.ies.EligibilityDeterminationMS.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BankDetailsDTO {
    private Integer id;

    @NotNull(message = "{bankName.notnull.invalid}")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "{bankName.pattern.invalid}")
    private String bankName;

    @NotNull(message = "{accountNumber.notnull.invalid}")
    @Pattern(regexp = "^[0-9]+$", message = "{accountNumber.pattern.invalid}")
    private String accountNumber;

    @NotNull(message = "{ifscCode.notnull.invalid}")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "{ifscCode.pattern.invalid}")
    private String ifscCode;

    @NotNull(message = "{branchName.notnull.invalid}")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "{branchName.pattern.invalid}")
    private String branchName;

    @NotNull(message = "{caseNumber.notnull.invalid}")
    private String caseNumber;
}
