package com.survey.app.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "SurveyUser")
public class User {
    @Id
    private String username;
    private String password;
}
