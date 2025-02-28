package com.ies.EligibilityDeterminationMS.repository;

import com.ies.EligibilityDeterminationMS.entity.EligibilityDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EligibilityDetailsRepository extends JpaRepository<EligibilityDetailsEntity, Integer> {

    List<EligibilityDetailsEntity> findByCaseNumber(String caseNumber);
}
