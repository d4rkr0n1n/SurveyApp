package com.survey.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.survey.app.dto.User;

public interface  SurveyAppRepository extends JpaRepository<User, String>{
    
}
