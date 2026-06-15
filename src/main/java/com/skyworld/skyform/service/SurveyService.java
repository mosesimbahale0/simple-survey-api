package com.skyworld.skyform.service;

import com.skyworld.skyform.entity.Survey;
import com.skyworld.skyform.repository.SurveyRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {

    private final SurveyRepository repository;

    public SurveyService(SurveyRepository repository) {
        this.repository = repository;
    }

    public Survey create(Survey survey) {
        return repository.save(survey);
    }

    public List<Survey> findAll() {
        return repository.findAll();
    }

    public Optional<Survey> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Survey update(Long id, Survey updated) {
        Survey existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found with id: " + id));
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}