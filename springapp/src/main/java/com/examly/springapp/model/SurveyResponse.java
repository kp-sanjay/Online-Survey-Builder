package com.examly.springapp.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "survey_response")
public class SurveyResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long surveyId;
    
    @Column(columnDefinition = "TEXT")
    private String answersJson;
    
    private String respondentEmail;
    
    private LocalDateTime submittedAt;
    
    private String status = "COMPLETED";

    public SurveyResponse() {
        this.submittedAt = LocalDateTime.now();
    }

    public SurveyResponse(Long surveyId, String answersJson, String respondentEmail) {
        this.surveyId = surveyId;
        this.answersJson = answersJson;
        this.respondentEmail = respondentEmail;
        this.submittedAt = LocalDateTime.now();
        this.status = "COMPLETED";
    }

    // Getters
    public Long getId() { return id; }
    public Long getSurveyId() { return surveyId; }
    public String getAnswersJson() { return answersJson; }
    public String getRespondentEmail() { return respondentEmail; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public String getStatus() { return status; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setSurveyId(Long surveyId) { this.surveyId = surveyId; }
    public void setAnswersJson(String answersJson) { this.answersJson = answersJson; }
    public void setRespondentEmail(String respondentEmail) { this.respondentEmail = respondentEmail; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
    public void setStatus(String status) { this.status = status; }
}
