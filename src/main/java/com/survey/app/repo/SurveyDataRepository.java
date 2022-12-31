package com.survey.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.survey.app.dto.SurveyData;

public interface  SurveyDataRepository extends JpaRepository<SurveyData, String>{
    
}
