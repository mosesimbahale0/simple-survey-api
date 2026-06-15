package com.skyworld.skyform.service;

import com.skyworld.skyform.entity.Question;
import com.skyworld.skyform.entity.QuestionOption;
import com.skyworld.skyform.entity.QuestionOptionRequest;
import com.skyworld.skyform.repository.QuestionOptionRepository;
import com.skyworld.skyform.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionOptionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionOptionRepository questionOptionRepository;

    private Question validateQuestion(Long surveyId, Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + questionId));

        if (!question.getSurvey().getId().equals(surveyId)) {
            throw new RuntimeException("Question " + questionId + " does not belong to survey " + surveyId);
        }

        if (!"choice".equals(question.getType())) {
            throw new RuntimeException("Question " + questionId + " is not a choice question");
        }

        return question;
    }

    @Transactional
    public QuestionOption addOption(Long surveyId, Long questionId, QuestionOptionRequest request) {
        Question question = validateQuestion(surveyId, questionId);

        QuestionOption option = new QuestionOption();
        option.setValue(request.getValue());
        option.setLabel(request.getLabel());
        option.setQuestion(question);

        return questionOptionRepository.save(option);
    }

    @Transactional
    public QuestionOption updateOption(Long surveyId, Long questionId, Long optionId, QuestionOptionRequest request) {
        validateQuestion(surveyId, questionId);

        QuestionOption option = questionOptionRepository.findById(optionId)
                .orElseThrow(() -> new RuntimeException("Option not found with id: " + optionId));

        if (!option.getQuestion().getId().equals(questionId)) {
            throw new RuntimeException("Option " + optionId + " does not belong to question " + questionId);
        }

        option.setValue(request.getValue());
        option.setLabel(request.getLabel());

        return questionOptionRepository.save(option);
    }

    @Transactional
    public void deleteOption(Long surveyId, Long questionId, Long optionId) {
        validateQuestion(surveyId, questionId);

        boolean exists = questionOptionRepository.existsById(optionId);
        if (!exists) {
            throw new RuntimeException("Option not found with id: " + optionId);
        }

        questionOptionRepository.deleteByIdNative(optionId);
        System.out.println(">>> native delete executed for id=" + optionId);
    }
}