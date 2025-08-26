package com.examly.springapp;

import com.examly.springapp.model.SurveyEntry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SurveyAppApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // 1. Test valid survey creation
    @Test
    public void testCreateSurveySuccessfully() throws Exception {
        SurveyEntry entry = new SurveyEntry();
        entry.setTitle("Customer Experience");
        entry.setQuestionsJson("[\"Was our service good?\"]");
        entry.setResponsesJson("[]");

        mockMvc.perform(post("/api/surveys/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entry)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Customer Experience"))
                .andExpect(jsonPath("$.id").exists());
    }

    // 2. Test creation with empty title
    @Test
    public void testCreateSurveyEmptyTitle() throws Exception {
        SurveyEntry entry = new SurveyEntry();
        entry.setTitle("");
        entry.setQuestionsJson("[\"Rate our team\"]");
        entry.setResponsesJson("[]");

        mockMvc.perform(post("/api/surveys/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entry)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(""));
    }

    // 3. Test creation with missing fields
    @Test
    public void testCreateSurveyMissingFields() throws Exception {
        SurveyEntry entry = new SurveyEntry();
        entry.setTitle("Incomplete Survey");

        mockMvc.perform(post("/api/surveys/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entry)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Incomplete Survey"));
    }

    // 4. Test if all surveys can be retrieved
    @Test
    public void testGetAllSurveysReturnsArray() throws Exception {
        mockMvc.perform(get("/api/surveys/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    // 5. Test multiple survey entries reflected in getAll
    @Test
    public void testCreateAndFetchMultipleSurveys() throws Exception {
        SurveyEntry entry1 = new SurveyEntry();
        entry1.setTitle("Survey A");
        entry1.setQuestionsJson("[\"Q1\"]");
        entry1.setResponsesJson("[]");

        SurveyEntry entry2 = new SurveyEntry();
        entry2.setTitle("Survey B");
        entry2.setQuestionsJson("[\"Q2\"]");
        entry2.setResponsesJson("[]");

        mockMvc.perform(post("/api/surveys/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entry1)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/surveys/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entry2)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/surveys/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))));
    }

    // 6. Test invalid method on /create endpoint
    @Test
    public void testInvalidHttpMethod() throws Exception {
        mockMvc.perform(get("/api/surveys/create"))
                .andExpect(status().isMethodNotAllowed());
    }

    // 7. Test content type is JSON in GET
    @Test
    public void testContentTypeJsonOnGetAll() throws Exception {
        mockMvc.perform(get("/api/surveys/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    // 8. Test bad request with malformed JSON body
    @Test
    public void testCreateSurveyWithMalformedJson() throws Exception {
        String badJson = "{\"title\":\"Bad JSON\",\"questionsJson\":\"[\"unclosed]\"";

        mockMvc.perform(post("/api/surveys/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(badJson))
                .andExpect(status().isBadRequest());
    }
}
