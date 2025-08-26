package com.examly.springapp.controller;

import com.examly.springapp.model.SurveyEntry;
import com.examly.springapp.model.SurveyResponse;
import com.examly.springapp.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/surveys")
public class SurveyController {

    private static final Logger logger = LoggerFactory.getLogger(SurveyController.class);

    @Autowired
    private SurveyService surveyService;

    @GetMapping("/all")
    public ResponseEntity<List<SurveyEntry>> getAllSurveys() {
        try {
            List<SurveyEntry> surveys = surveyService.getAllSurveys();
            return ResponseEntity.ok(surveys);
        } catch (Exception e) {
            logger.error("Error fetching all surveys", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch surveys");
        }
    }
    
    @GetMapping("/paginated")
    public ResponseEntity<Page<SurveyEntry>> getAllSurveysPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        try {
            Page<SurveyEntry> surveys = surveyService.getAllSurveysPaginated(page, size, sortBy, sortDirection);
            return ResponseEntity.ok(surveys);
        } catch (Exception e) {
            logger.error("Error fetching paginated surveys", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch surveys");
        }
    }
    
    @GetMapping("/filtered")
    public ResponseEntity<Page<SurveyEntry>> getSurveysWithFilters(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String creatorEmail,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        try {
            Page<SurveyEntry> surveys = surveyService.getSurveysWithFilters(
                    title, description, creatorEmail, status, page, size, sortBy, sortDirection);
            return ResponseEntity.ok(surveys);
        } catch (Exception e) {
            logger.error("Error fetching filtered surveys", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch surveys");
        }
    }
    
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<SurveyEntry> getSurveyById(@PathVariable Long id) {
        try {
            return surveyService.getSurveyById(id)
                    .map(survey -> ResponseEntity.ok(survey))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error fetching survey by ID: {}", id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch survey");
        }
    }
    
    @GetMapping("/creator/{email}")
    public ResponseEntity<List<SurveyEntry>> getSurveysByCreator(@PathVariable String email) {
        try {
            List<SurveyEntry> surveys = surveyService.getSurveysByCreator(email);
            return ResponseEntity.ok(surveys);
        } catch (Exception e) {
            logger.error("Error fetching surveys by creator: {}", email, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch surveys");
        }
    }
    
    @GetMapping("/creator/{email}/paginated")
    public ResponseEntity<Page<SurveyEntry>> getSurveysByCreatorPaginated(
            @PathVariable String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        try {
            Page<SurveyEntry> surveys = surveyService.getSurveysByCreatorPaginated(email, page, size, sortBy, sortDirection);
            return ResponseEntity.ok(surveys);
        } catch (Exception e) {
            logger.error("Error fetching paginated surveys by creator: {}", email, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch surveys");
        }
    }
    
    @GetMapping("/status/{status}/paginated")
    public ResponseEntity<Page<SurveyEntry>> getSurveysByStatusPaginated(
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        try {
            Page<SurveyEntry> surveys = surveyService.getSurveysByStatus(status, page, size, sortBy, sortDirection);
            return ResponseEntity.ok(surveys);
        } catch (Exception e) {
            logger.error("Error fetching paginated surveys by status: {}", status, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch surveys");
        }
    }
    
    @GetMapping("/creator/{email}/status/{status}/paginated")
    public ResponseEntity<Page<SurveyEntry>> getSurveysByCreatorAndStatusPaginated(
            @PathVariable String email,
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        try {
            Page<SurveyEntry> surveys = surveyService.getSurveysByCreatorAndStatus(email, status, page, size, sortBy, sortDirection);
            return ResponseEntity.ok(surveys);
        } catch (Exception e) {
            logger.error("Error fetching paginated surveys by creator and status: {} - {}", email, status, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch surveys");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<SurveyEntry> createSurvey(@RequestBody SurveyEntry entry) {
        try {
            // Handle null values
            if (entry.getTitle() == null) {
                entry.setTitle("");
            }
            if (entry.getDescription() == null) {
                entry.setDescription("");
            }
            if (entry.getQuestionsJson() == null) {
                entry.setQuestionsJson("[]");
            }
            if (entry.getResponsesJson() == null) {
                entry.setResponsesJson("[]");
            }
            if (entry.getStatus() == null) {
                entry.setStatus("ACTIVE");
            }

            // Validate questionsJson if it's not empty
            if (entry.getQuestionsJson() != null && !entry.getQuestionsJson().trim().isEmpty()) {
                try {
                    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                    com.fasterxml.jackson.databind.JsonNode questionsNode = mapper.readTree(entry.getQuestionsJson());
                    if (!questionsNode.isArray()) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid questionsJson format");
                    }
                } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid questionsJson JSON");
                }
            }

            SurveyEntry saved = surveyService.createSurvey(entry);
            return ResponseEntity.ok(saved);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create survey");
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SurveyEntry> updateSurvey(@PathVariable Long id, @RequestBody SurveyEntry surveyDetails) {
        try {
            SurveyEntry updated = surveyService.updateSurvey(id, surveyDetails);
            if (updated != null) {
                return ResponseEntity.ok(updated);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update survey");
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteSurvey(@PathVariable Long id) {
        try {
            boolean deleted = surveyService.deleteSurvey(id);
            if (deleted) {
                return ResponseEntity.ok(Map.of("message", "Survey deleted successfully"));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete survey");
        }
    }
    
    @PostMapping("/{id}/respond")
    public ResponseEntity<SurveyResponse> submitResponse(@PathVariable Long id, @RequestBody SurveyResponse response) {
        try {
            response.setSurveyId(id);
            SurveyResponse saved = surveyService.submitResponse(response);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to submit response");
        }
    }
    
    @GetMapping("/{id}/responses")
    public ResponseEntity<List<SurveyResponse>> getSurveyResponses(@PathVariable Long id) {
        try {
            List<SurveyResponse> responses = surveyService.getSurveyResponses(id);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch responses");
        }
    }
    
    @GetMapping("/{id}/responses/paginated")
    public ResponseEntity<Page<SurveyResponse>> getSurveyResponsesPaginated(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        try {
            Page<SurveyResponse> responses = surveyService.getSurveyResponsesPaginated(id, page, size, sortBy, sortDirection);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch responses");
        }
    }
    
    @GetMapping("/responses/respondent/{email}/paginated")
    public ResponseEntity<Page<SurveyResponse>> getResponsesByRespondentPaginated(
            @PathVariable String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        try {
            Page<SurveyResponse> responses = surveyService.getResponsesByRespondentPaginated(email, page, size, sortBy, sortDirection);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch responses");
        }
    }
    
    @GetMapping("/{id}/response-count")
    public ResponseEntity<Map<String, Long>> getSurveyResponseCount(@PathVariable Long id) {
        try {
            long count = surveyService.getSurveyResponseCount(id);
            return ResponseEntity.ok(Map.of("count", count));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch response count");
        }
    }
} 