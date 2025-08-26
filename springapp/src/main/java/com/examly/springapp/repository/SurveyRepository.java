package com.examly.springapp.repository;

import com.examly.springapp.model.SurveyEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<SurveyEntry, Long>, JpaSpecificationExecutor<SurveyEntry> {
    List<SurveyEntry> findByCreatorEmail(String creatorEmail);
    
    Page<SurveyEntry> findByCreatorEmail(String creatorEmail, Pageable pageable);
    
    Page<SurveyEntry> findByStatus(String status, Pageable pageable);
    
    Page<SurveyEntry> findByCreatorEmailAndStatus(String creatorEmail, String status, Pageable pageable);
    
    @Query("SELECT s FROM SurveyEntry s WHERE " +
           "(:title IS NULL OR LOWER(s.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:description IS NULL OR LOWER(s.description) LIKE LOWER(CONCAT('%', :description, '%'))) AND " +
           "(:creatorEmail IS NULL OR s.creatorEmail = :creatorEmail) AND " +
           "(:status IS NULL OR s.status = :status)")
    Page<SurveyEntry> findSurveysWithFilters(
            @Param("title") String title,
            @Param("description") String description,
            @Param("creatorEmail") String creatorEmail,
            @Param("status") String status,
            Pageable pageable);
} 