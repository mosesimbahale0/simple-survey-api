package com.skyworld.skyform.repository;

import com.skyworld.skyform.entity.SurveyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {

    Page<SurveyResponse> findBySurveyId(Long surveyId, Pageable pageable);

    Page<SurveyResponse> findBySurveyIdAndEmailAddressContainingIgnoreCase(Long surveyId, String email, Pageable pageable);
}