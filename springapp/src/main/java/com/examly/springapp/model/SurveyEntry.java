package com.examly.springapp.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "survey_entry")
public class SurveyEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    
    private String description;

    @Column(columnDefinition = "TEXT")
    private String questionsJson;

    @Column(columnDefinition = "TEXT")
    private String responsesJson = "[]";
    
    private String creatorEmail;
    
    private LocalDateTime createdAt;
    
    private String status = "ACTIVE";

    public SurveyEntry() {
        this.createdAt = LocalDateTime.now();
    }

    public SurveyEntry(String title, String description, String questionsJson, String creatorEmail) {
        this.title = title;
        this.description = description;
        this.questionsJson = questionsJson;
        this.creatorEmail = creatorEmail;
        this.responsesJson = "[]";
        this.createdAt = LocalDateTime.now();
        this.status = "ACTIVE";
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getQuestionsJson() { return questionsJson; }
    public String getResponsesJson() { return responsesJson; }
    public String getCreatorEmail() { return creatorEmail; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getStatus() { return status; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setQuestionsJson(String questionsJson) { this.questionsJson = questionsJson; }
    public void setResponsesJson(String responsesJson) { this.responsesJson = responsesJson; }
    public void setCreatorEmail(String creatorEmail) { this.creatorEmail = creatorEmail; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setStatus(String status) { this.status = status; }
} 