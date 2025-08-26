package com.examly.springapp.configuration;

import com.examly.springapp.model.SurveyEntry;
import com.examly.springapp.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private SurveyRepository surveyRepository;

    @Override
    public void run(String... args) throws Exception {
        try {
            // Only initialize if no surveys exist
            if (surveyRepository.count() == 0) {
                logger.info("No surveys found. Initializing sample data...");
                initializeSampleData();
                logger.info("Sample data initialized successfully.");
            } else {
                logger.info("Surveys already exist. Skipping sample data initialization.");
            }
        } catch (Exception e) {
            logger.error("Error during data initialization: " + e.getMessage(), e);
            // Don't fail the application startup, just log the error
            logger.warn("Application will continue without sample data initialization.");
        }
    }

    private void initializeSampleData() {
        try {
            // Sample Survey 1: Customer Satisfaction
            SurveyEntry survey1 = new SurveyEntry(
                "Customer Satisfaction Survey",
                "Help us improve our services by providing your valuable feedback",
                "[\"How satisfied are you with our product quality?\", \"How likely are you to recommend us to others?\", \"What aspects of our service could be improved?\", \"How would you rate our customer support?\"]",
                "admin@company.com"
            );
            surveyRepository.save(survey1);
            logger.info("Created Customer Satisfaction Survey");

            // Sample Survey 2: Employee Engagement
            SurveyEntry survey2 = new SurveyEntry(
                "Employee Engagement Survey",
                "Annual survey to understand employee satisfaction and engagement levels",
                "[\"How satisfied are you with your current role?\", \"How would you rate the work-life balance?\", \"Do you feel valued and recognized at work?\", \"What would improve your job satisfaction?\", \"How would you rate the company culture?\"]",
                "hr@company.com"
            );
            surveyRepository.save(survey2);
            logger.info("Created Employee Engagement Survey");

            // Sample Survey 3: Product Feedback
            SurveyEntry survey3 = new SurveyEntry(
                "Product Feedback Survey",
                "Share your thoughts about our latest product features",
                "[\"Which features do you find most useful?\", \"What additional features would you like to see?\", \"How intuitive is the user interface?\", \"What problems have you encountered?\", \"How would you rate the overall product experience?\"]",
                "product@company.com"
            );
            surveyRepository.save(survey3);
            logger.info("Created Product Feedback Survey");
            
        } catch (Exception e) {
            logger.error("Error creating sample surveys: " + e.getMessage(), e);
            throw e;
        }
    }
}

