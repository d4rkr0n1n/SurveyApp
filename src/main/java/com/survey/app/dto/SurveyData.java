package com.survey.app.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "SurveyData")
public class SurveyData {
    @Id
    private String name;
    private String date;
    private String address;
    private String country;
    private String pin;
}
