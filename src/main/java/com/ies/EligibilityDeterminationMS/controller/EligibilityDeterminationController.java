package com.ies.EligibilityDeterminationMS.controller;

import com.ies.EligibilityDeterminationMS.DTO.EligibilityDetailsDTO;
import com.ies.EligibilityDeterminationMS.service.EligibilityDeterminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/eligibility-determination")
public class EligibilityDeterminationController {

    @Autowired
    private EligibilityDeterminationService eligibilityDeterminationService;

    @GetMapping(value = "/test")
    public String test() {
        return "Eligibility Determination MS is working";
    }

    @GetMapping(value = "/get-eligibility/{caseNumber}")
    public ResponseEntity<List<EligibilityDetailsDTO>> getEligibility(
            @PathVariable String caseNumber,
            @RequestHeader("Authorization") String jwt
    ){
        System.out.println("Case Number: " + caseNumber);
        return ResponseEntity.ok(eligibilityDeterminationService.determineEligibility(caseNumber, jwt));
    }
}
