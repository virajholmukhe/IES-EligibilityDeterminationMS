package com.ies.EligibilityDeterminationMS.service;

import com.ies.EligibilityDeterminationMS.DTO.EligibilityDetailsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EligibilityDeterminationService {
    public List<EligibilityDetailsDTO> determineEligibility(String caseNumber, String jwt);

}
