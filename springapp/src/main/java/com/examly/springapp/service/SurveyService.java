package com.examly.springapp.service;

import com.examly.springapp.model.SurveyEntry;
import com.examly.springapp.model.SurveyResponse;
import com.examly.springapp.repository.SurveyRepository;
import com.examly.springapp.repository.SurveyResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;
    
    @Autowired
    private SurveyResponseRepository responseRepository;

    public List<SurveyEntry> getAllSurveys() {
        return surveyRepository.findAll();
    }
    
    public Page<SurveyEntry> getAllSurveysPaginated(int page, int size, String sortBy, String sortDirection) {
        Sort sort = createSort(sortBy, sortDirection);
        Pageable pageable = PageRequest.of(page, size, sort);
        return surveyRepository.findAll(pageable);
    }
    
    public Page<SurveyEntry> getSurveysWithFilters(
            String title, 
            String description, 
            String creatorEmail, 
            String status,
            int page, 
            int size, 
            String sortBy, 
            String sortDirection) {
        Sort sort = createSort(sortBy, sortDirection);
        Pageable pageable = PageRequest.of(page, size, sort);
        return surveyRepository.findSurveysWithFilters(title, description, creatorEmail, status, pageable);
    }
    
    public Optional<SurveyEntry> getSurveyById(Long id) {
        return surveyRepository.findById(id);
    }
    
    public List<SurveyEntry> getSurveysByCreator(String creatorEmail) {
        return surveyRepository.findByCreatorEmail(creatorEmail);
    }
    
    public Page<SurveyEntry> getSurveysByCreatorPaginated(String creatorEmail, int page, int size, String sortBy, String sortDirection) {
        Sort sort = createSort(sortBy, sortDirection);
        Pageable pageable = PageRequest.of(page, size, sort);
        return surveyRepository.findByCreatorEmail(creatorEmail, pageable);
    }
    
    public Page<SurveyEntry> getSurveysByStatus(String status, int page, int size, String sortBy, String sortDirection) {
        Sort sort = createSort(sortBy, sortDirection);
        Pageable pageable = PageRequest.of(page, size, sort);
        return surveyRepository.findByStatus(status, pageable);
    }
    
    public Page<SurveyEntry> getSurveysByCreatorAndStatus(String creatorEmail, String status, int page, int size, String sortBy, String sortDirection) {
        Sort sort = createSort(sortBy, sortDirection);
        Pageable pageable = PageRequest.of(page, size, sort);
        return surveyRepository.findByCreatorEmailAndStatus(creatorEmail, status, pageable);
    }

    public SurveyEntry createSurvey(SurveyEntry survey) {
        return surveyRepository.save(survey);
    }
    
    public SurveyEntry updateSurvey(Long id, SurveyEntry surveyDetails) {
        Optional<SurveyEntry> survey = surveyRepository.findById(id);
        if (survey.isPresent()) {
            SurveyEntry existingSurvey = survey.get();
            existingSurvey.setTitle(surveyDetails.getTitle());
            existingSurvey.setDescription(surveyDetails.getDescription());
            existingSurvey.setQuestionsJson(surveyDetails.getQuestionsJson());
            existingSurvey.setStatus(surveyDetails.getStatus());
            return surveyRepository.save(existingSurvey);
        }
        return null;
    }
    
    public boolean deleteSurvey(Long id) {
        if (surveyRepository.existsById(id)) {
            // Delete all responses first
            List<SurveyResponse> responses = responseRepository.findBySurveyId(id);
            responseRepository.deleteAll(responses);
            
            // Delete the survey
            surveyRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public SurveyResponse submitResponse(SurveyResponse response) {
        return responseRepository.save(response);
    }
    
    public List<SurveyResponse> getSurveyResponses(Long surveyId) {
        return responseRepository.findBySurveyId(surveyId);
    }
    
    public Page<SurveyResponse> getSurveyResponsesPaginated(Long surveyId, int page, int size, String sortBy, String sortDirection) {
        Sort sort = createSort(sortBy, sortDirection);
        Pageable pageable = PageRequest.of(page, size, sort);
        return responseRepository.findBySurveyId(surveyId, pageable);
    }
    
    public Page<SurveyResponse> getResponsesByRespondentPaginated(String respondentEmail, int page, int size, String sortBy, String sortDirection) {
        Sort sort = createSort(sortBy, sortDirection);
        Pageable pageable = PageRequest.of(page, size, sort);
        return responseRepository.findByRespondentEmail(respondentEmail, pageable);
    }
    
    public long getSurveyResponseCount(Long surveyId) {
        return responseRepository.countBySurveyId(surveyId);
    }
    
    private Sort createSort(String sortBy, String sortDirection) {
        if (sortBy == null || sortBy.trim().isEmpty()) {
            sortBy = "id";
        }
        
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortDirection != null && sortDirection.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        
        return Sort.by(direction, sortBy);
    }
} 