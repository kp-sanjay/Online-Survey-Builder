package com.examly.springapp.repository;

import com.examly.springapp.model.SurveyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {
    List<SurveyResponse> findBySurveyId(Long surveyId);
    Page<SurveyResponse> findBySurveyId(Long surveyId, Pageable pageable);
    List<SurveyResponse> findByRespondentEmail(String respondentEmail);
    Page<SurveyResponse> findByRespondentEmail(String respondentEmail, Pageable pageable);
    long countBySurveyId(Long surveyId);
}
