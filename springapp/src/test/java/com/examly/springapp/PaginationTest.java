package com.examly.springapp;

import com.examly.springapp.model.SurveyEntry;
import com.examly.springapp.service.SurveyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class PaginationTest {

    @Autowired
    private SurveyService surveyService;

    @Test
    public void testPaginationAndSorting() {
        // Test pagination with default parameters
        Page<SurveyEntry> surveys = surveyService.getAllSurveysPaginated(0, 10, "id", "asc");
        
        assertNotNull(surveys);
        assertNotNull(surveys.getContent());
        assertTrue(surveys.getSize() <= 10);
        assertTrue(surveys.getNumber() >= 0);
        assertTrue(surveys.getTotalElements() >= 0);
        assertTrue(surveys.getTotalPages() >= 0);
    }

    @Test
    public void testFilteredSurveys() {
        // Test filtered surveys with pagination
        Page<SurveyEntry> surveys = surveyService.getSurveysWithFilters(
                null, null, null, null, 0, 5, "title", "asc");
        
        assertNotNull(surveys);
        assertNotNull(surveys.getContent());
        assertTrue(surveys.getSize() <= 5);
    }

    @Test
    public void testSorting() {
        // Test sorting in ascending order
        Page<SurveyEntry> ascSurveys = surveyService.getAllSurveysPaginated(0, 10, "title", "asc");
        
        // Test sorting in descending order
        Page<SurveyEntry> descSurveys = surveyService.getAllSurveysPaginated(0, 10, "title", "desc");
        
        assertNotNull(ascSurveys);
        assertNotNull(descSurveys);
    }
}
