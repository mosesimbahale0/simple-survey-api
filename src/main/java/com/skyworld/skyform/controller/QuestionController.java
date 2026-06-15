package com.skyworld.skyform.controller;

import com.skyworld.skyform.entity.*;
import com.skyworld.skyform.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/surveys/{surveyId}/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<QuestionDTO> createQuestion(
            @PathVariable Long surveyId,
            @RequestBody QuestionRequest request) {
        QuestionDTO created = questionService.createQuestion(surveyId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<QuestionsWrapper> getQuestions(@PathVariable Long surveyId) {
        QuestionsWrapper questions = questionService.getQuestionsBySurvey(surveyId);
        return ResponseEntity.ok(questions);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<QuestionDTO> updateQuestion(
            @PathVariable Long surveyId,
            @PathVariable Long id,
            @RequestBody QuestionRequest request) {
        QuestionDTO updated = questionService.updateQuestion(surveyId, id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(
            @PathVariable Long surveyId,
            @PathVariable Long id) {
        questionService.deleteQuestion(surveyId, id);
        return ResponseEntity.noContent().build();
    }
}