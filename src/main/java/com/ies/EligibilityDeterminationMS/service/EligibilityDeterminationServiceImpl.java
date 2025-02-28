package com.ies.EligibilityDeterminationMS.service;

import com.ies.EligibilityDeterminationMS.DTO.CitizenApplicationDTO;
import com.ies.EligibilityDeterminationMS.DTO.EligibilityDetailsDTO;
import com.ies.EligibilityDeterminationMS.DTO.KidDTO;
import com.ies.EligibilityDeterminationMS.DTO.SummaryDetails;
import com.ies.EligibilityDeterminationMS.entity.EligibilityDetailsEntity;
import com.ies.EligibilityDeterminationMS.repository.EligibilityDetailsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class EligibilityDeterminationServiceImpl implements EligibilityDeterminationService{

    @Autowired
    EligibilityDetailsRepository eligibilityDetailsRepository;

    @Override
    public List<EligibilityDetailsDTO> determineEligibility(String caseNumber, String jwt) {

        String url1 = "http://localhost:8004/data-collection/get-summary-details/"+caseNumber;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwt);
        HttpEntity<String> entity1 = new HttpEntity<>(headers);
        RestTemplate restTemplate1 = new RestTemplate();
        SummaryDetails summaryDetails = restTemplate1.exchange(url1, HttpMethod.GET, entity1, SummaryDetails.class).getBody();
//        System.out.println(summaryDetails.toString());

        String url2 = "http://localhost:8003/application-registration/get-application/"+caseNumber;
        RestTemplate restTemplate2 = new RestTemplate();
        CitizenApplicationDTO citizenApplicationDTO = restTemplate2.exchange(url2, HttpMethod.GET, entity1, CitizenApplicationDTO.class).getBody();
//        System.out.println(citizenApplicationDTO.toString());

//        assert summaryDetails != null;
        String planName = summaryDetails.getPlanName().toUpperCase();

        List<EligibilityDetailsDTO> eligibilityDetailsDTOList = new ArrayList<>();
        List<EligibilityDetailsEntity> eligibilityDetailsEntityListPresent = eligibilityDetailsRepository.findByCaseNumber(caseNumber);
        boolean flgNoChange = false;
        for (EligibilityDetailsEntity e : eligibilityDetailsEntityListPresent) {
            if(e != null && e.getPlanName().equals(planName) && e.getPlanStatus().equals("APPROVED")
                    && e.getPlanEndDate().isAfter(LocalDate.now())){
                flgNoChange = true;
                break;
            }
        }
        if(flgNoChange){
            for (EligibilityDetailsEntity e : eligibilityDetailsEntityListPresent) {
                EligibilityDetailsDTO eligibilityDetailsDTOResponse = new EligibilityDetailsDTO();
                BeanUtils.copyProperties(e, eligibilityDetailsDTOResponse);
                eligibilityDetailsDTOList.add(eligibilityDetailsDTOResponse);
            }
            return eligibilityDetailsDTOList;
        }

        EligibilityDetailsEntity eligibilityDetailsEntity = new EligibilityDetailsEntity();
        eligibilityDetailsEntity.setPlanName(planName);
        eligibilityDetailsEntity.setCaseNumber(caseNumber);
        switch (planName) {
            case "SNAP" -> {
                if (summaryDetails.getIncomeDetails().getSalaryIncome() < 300000) {
                    eligibilityDetailsEntity.setPlanStatus("APPROVED");
                    eligibilityDetailsEntity.setDenialReason("NA");
                } else {
                    eligibilityDetailsEntity.setPlanStatus("REJECTED");
                    eligibilityDetailsEntity.setBenefitAmount(0D);
                    eligibilityDetailsEntity.setDenialReason("Salary Income is more than 3 lac per anum");
                }
            }
            case "CCAP" -> {
                boolean isValidAge = false;
                for (KidDTO kidDTO : summaryDetails.getFamilyDetails().getKids()) {
                    int age = Period.between(kidDTO.getKidDob(), LocalDate.now()).getYears();
                    if (age <= 16) {
                        isValidAge = true;
                        break;
                    }
                }
                if (summaryDetails.getIncomeDetails().getSalaryIncome() < 300000
                        && !summaryDetails.getFamilyDetails().getKids().isEmpty()
                        && isValidAge) {
                    eligibilityDetailsEntity.setPlanStatus("APPROVED");
                    eligibilityDetailsEntity.setDenialReason("NA");
                } else {
                    eligibilityDetailsEntity.setPlanStatus("REJECTED");
                    eligibilityDetailsEntity.setBenefitAmount(0D);
                    if(summaryDetails.getIncomeDetails().getSalaryIncome() < 300000){
                        eligibilityDetailsEntity.setDenialReason("Income is more than 300000");
                    }else if(summaryDetails.getFamilyDetails().getKids().isEmpty()) {
                        eligibilityDetailsEntity.setDenialReason("No Kids");
                    }else if(!isValidAge){
                        eligibilityDetailsEntity.setDenialReason("Kids age is more than 16");
                    }
                }
            }
            case "MEDICAID" -> {
                if (summaryDetails.getIncomeDetails().getSalaryIncome() < 300000
                        && (summaryDetails.getIncomeDetails().getRentIncome() + summaryDetails.getIncomeDetails().getPropertyIncome()) == 0) {
                    eligibilityDetailsEntity.setPlanStatus("APPROVED");
                    eligibilityDetailsEntity.setDenialReason("NA");
                } else {
                    eligibilityDetailsEntity.setPlanStatus("REJECTED");
                    eligibilityDetailsEntity.setBenefitAmount(0D);
                    if(summaryDetails.getIncomeDetails().getSalaryIncome() < 300000) {
                        eligibilityDetailsEntity.setDenialReason("Income is more than 300000");
                    }else if((summaryDetails.getIncomeDetails().getRentIncome() + summaryDetails.getIncomeDetails().getPropertyIncome()) >= 0){
                        eligibilityDetailsEntity.setDenialReason("Rent+Property Income is not less than 0");
                    }
                }
            }
            case "MEDICARE" -> {
                int citizenAge = Period.between(citizenApplicationDTO.getCitizenDob(), LocalDate.now()).getYears();
                if (citizenAge >= 65) {
                    eligibilityDetailsEntity.setPlanStatus("APPROVED");
                    eligibilityDetailsEntity.setDenialReason("NA");
                } else {
                    eligibilityDetailsEntity.setPlanStatus("REJECTED");
                    eligibilityDetailsEntity.setBenefitAmount(0D);
                    eligibilityDetailsEntity.setDenialReason("Age is less than 65");
                }
            }
            case "RIW" -> {
                if (summaryDetails.getIncomeDetails().getSalaryIncome() == 0
                        && summaryDetails.getEducationDetails().getQualificationYear() != null) {
                    eligibilityDetailsEntity.setPlanStatus("APPROVED");
                    eligibilityDetailsEntity.setDenialReason("NA");
                } else {
                    eligibilityDetailsEntity.setPlanStatus("REJECTED");
                    eligibilityDetailsEntity.setBenefitAmount(0D);
                    if(summaryDetails.getIncomeDetails().getSalaryIncome() != 0) {
                        eligibilityDetailsEntity.setDenialReason("Salary Income is not 0");
                    }else if(summaryDetails.getEducationDetails().getQualificationYear() == null){
                        eligibilityDetailsEntity.setDenialReason("Qualification Year is not provided");
                    }
                }
            }
        }
        if(eligibilityDetailsEntity.getPlanStatus().equals("APPROVED")){
            eligibilityDetailsEntity.setPlanStartDate(LocalDate.now());
            eligibilityDetailsEntity.setPlanEndDate(LocalDate.now().plusMonths(6));
            eligibilityDetailsEntity.setBenefitAmount(50000D);
        }

        EligibilityDetailsDTO eligibilityDetailsDTO = new EligibilityDetailsDTO();
        eligibilityDetailsRepository.save(eligibilityDetailsEntity);

        List<EligibilityDetailsEntity> eligibilityDetailsEntityList = eligibilityDetailsRepository.findByCaseNumber(caseNumber);
        for (EligibilityDetailsEntity e : eligibilityDetailsEntityList) {
            EligibilityDetailsDTO eligibilityDetailsDTOResponse = new EligibilityDetailsDTO();
            BeanUtils.copyProperties(e, eligibilityDetailsDTOResponse);
            eligibilityDetailsDTOList.add(eligibilityDetailsDTOResponse);
        }

        String url = "http://localhost:8005/correspondence/generate-correspondence";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url, eligibilityDetailsDTO, Void.class);
        return eligibilityDetailsDTOList;
    }

}
