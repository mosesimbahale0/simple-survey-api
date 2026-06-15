package com.skyworld.skyform.service;

import com.skyworld.skyform.entity.*;
import com.skyworld.skyform.repository.QuestionRepository;
import com.skyworld.skyform.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    public QuestionDTO createQuestion(Long surveyId, QuestionRequest request) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Survey not found with id: " + surveyId));

        Question question = new Question();
        question.setSurvey(survey);
        applyRequestToQuestion(question, request);

        Question saved = questionRepository.save(question);
        return QuestionDTO.fromEntity(saved);
    }

    public QuestionsWrapper getQuestionsBySurvey(Long surveyId) {
        if (!surveyRepository.existsById(surveyId)) {
            throw new RuntimeException("Survey not found with id: " + surveyId);
        }

        List<QuestionDTO> dtos = questionRepository.findBySurveyId(surveyId).stream()
                .map(QuestionDTO::fromEntity)
                .collect(Collectors.toList());

        return new QuestionsWrapper(dtos);
    }

    public QuestionDTO updateQuestion(Long surveyId, Long questionId, QuestionRequest request) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + questionId));

        if (!question.getSurvey().getId().equals(surveyId)) {
            throw new RuntimeException("Question " + questionId + " does not belong to survey " + surveyId);
        }

        applyRequestToQuestion(question, request);

        Question saved = questionRepository.save(question);
        return QuestionDTO.fromEntity(saved);
    }

    public void deleteQuestion(Long surveyId, Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + questionId));

        if (!question.getSurvey().getId().equals(surveyId)) {
            throw new RuntimeException("Question " + questionId + " does not belong to survey " + surveyId);
        }

        questionRepository.delete(question);
    }

    private void applyRequestToQuestion(Question question, QuestionRequest request) {
        question.setName(request.getName());
        question.setType(request.getType());
        question.setRequired(request.getRequired());
        question.setText(request.getText());
        question.setDescription(request.getDescription());

        // reset choice options in-place to satisfy orphanRemoval
        if (question.getOptions() == null) {
            question.setOptions(new java.util.ArrayList<>());
        } else {
            question.getOptions().clear();
        }

        if ("choice".equals(request.getType()) && request.getOptions() != null) {
            question.setOptionsMultiple(request.getOptions().getMultiple());
            if (request.getOptions().getOption() != null) {
                for (QuestionRequest.OptionRequest o : request.getOptions().getOption()) {
                    QuestionOption opt = new QuestionOption();
                    opt.setValue(o.getValue());
                    opt.setLabel(o.getLabel());
                    opt.setQuestion(question);
                    question.getOptions().add(opt);
                }
            }
        } else {
            question.setOptionsMultiple(null);
        }

        // file properties
        if ("file".equals(request.getType()) && request.getFileProperties() != null) {
            FileProperties fp = question.getFileProperties() != null ? question.getFileProperties() : new FileProperties();
            QuestionRequest.FilePropertiesRequest fpReq = request.getFileProperties();
            fp.setFormat(fpReq.getFormat());
            fp.setMaxFileSize(fpReq.getMaxFileSize());
            fp.setMaxFileSizeUnit(fpReq.getMaxFileSizeUnit());
            fp.setMultiple(fpReq.getMultiple());
            question.setFileProperties(fp);
        } else {
            question.setFileProperties(null);
        }
    }
}