package com.skyworld.skyform.controller;

import com.skyworld.skyform.entity.QuestionOption;
import com.skyworld.skyform.entity.QuestionOptionRequest;
import com.skyworld.skyform.entity.QuestionOptionResponseDTO;
import com.skyworld.skyform.service.QuestionOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/surveys/{surveyId}/questions/{questionId}/options")
public class QuestionOptionController {

    @Autowired
    private QuestionOptionService questionOptionService;

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<QuestionOptionResponseDTO> addOption(
            @PathVariable Long surveyId,
            @PathVariable Long questionId,
            @RequestBody QuestionOptionRequest request) {
        QuestionOption option = questionOptionService.addOption(surveyId, questionId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(QuestionOptionResponseDTO.fromEntity(option));
    }

    @PutMapping(value = "/{optionId}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<QuestionOptionResponseDTO> updateOption(
            @PathVariable Long surveyId,
            @PathVariable Long questionId,
            @PathVariable Long optionId,
            @RequestBody QuestionOptionRequest request) {
        QuestionOption option = questionOptionService.updateOption(surveyId, questionId, optionId, request);
        return ResponseEntity.ok(QuestionOptionResponseDTO.fromEntity(option));
    }

    @DeleteMapping("/{optionId}")
    public ResponseEntity<Void> deleteOption(
            @PathVariable Long surveyId,
            @PathVariable Long questionId,
            @PathVariable Long optionId) {
        questionOptionService.deleteOption(surveyId, questionId, optionId);
        return ResponseEntity.noContent().build();
    }
}