package com.skyworld.skyform.service;

import com.skyworld.skyform.entity.*;
import com.skyworld.skyform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SurveyResponseService {

    @Autowired
    private SurveyResponseRepository surveyResponseRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CertificateRepository certificateRepository;

    @Value("${app.upload.dir:./uploads/certificates}")
    private String uploadDir;

    /**
     * formFields: map of question name -> submitted value (for text/choice/email questions)
     * files: map of question name -> list of uploaded files (for file questions)
     */
    public QuestionResponseDTO submitResponse(Long surveyId, Map<String, String> formFields, Map<String, List<MultipartFile>> files) throws IOException {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Survey not found with id: " + surveyId));

        List<Question> questions = questionRepository.findBySurveyId(surveyId);
        Map<String, Question> questionsByName = questions.stream()
                .collect(Collectors.toMap(Question::getName, q -> q));

        SurveyResponse response = new SurveyResponse();
        response.setSurvey(survey);

        // full_name and email_address are mandatory top-level fields
        response.setFullName(formFields.get("full_name"));
        String email = formFields.get("email_address");
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email_address is required");
        }
        response.setEmailAddress(email);

        List<Answer> answers = new ArrayList<>();
        for (Map.Entry<String, String> entry : formFields.entrySet()) {
            String qName = entry.getKey();
            Question q = questionsByName.get(qName);
            if (q == null) continue; // ignore unknown fields

            Answer answer = new Answer();
            answer.setResponse(response);
            answer.setQuestion(q);
            answer.setAnswerText(entry.getValue());
            answers.add(answer);
        }
        response.setAnswers(answers);

        // Handle file uploads
        List<Certificate> certificates = new ArrayList<>();
        if (files != null) {
            for (Map.Entry<String, List<MultipartFile>> entry : files.entrySet()) {
                String qName = entry.getKey();
                Question q = questionsByName.get(qName);
                if (q == null || !"file".equals(q.getType())) continue;

                for (MultipartFile file : entry.getValue()) {
                    if (file.isEmpty()) continue;
                    Certificate cert = storeFile(file, response, q);
                    certificates.add(cert);
                }
            }
        }
        response.setCertificates(certificates);

        SurveyResponse saved = surveyResponseRepository.save(response);
        return QuestionResponseDTO.fromEntity(saved, false);
    }

    private Certificate storeFile(MultipartFile file, SurveyResponse response, Question question) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String original = file.getOriginalFilename();
        String storedName = UUID.randomUUID() + "_" + (original != null ? original : "file");
        Path target = uploadPath.resolve(storedName);
        file.transferTo(target);

        Certificate cert = new Certificate();
        cert.setResponse(response);
        cert.setQuestion(question);
        cert.setOriginalFilename(original != null ? original : storedName);
        cert.setStoredFilename(storedName);
        cert.setFilePath(target.toString());
        cert.setContentType(file.getContentType());
        cert.setFileSize(file.getSize());
        return cert;
    }

    public QuestionResponsesWrapper getResponses(Long surveyId, int page, int pageSize, String email) {
        if (!surveyRepository.existsById(surveyId)) {
            throw new RuntimeException("Survey not found with id: " + surveyId);
        }

        int pageIndex = Math.max(page - 1, 0); // page param is 1-based
        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);

        Page<SurveyResponse> resultPage;
        if (email != null && !email.isBlank()) {
            resultPage = surveyResponseRepository.findBySurveyIdAndEmailAddressContainingIgnoreCase(surveyId, email, pageRequest);
        } else {
            resultPage = surveyResponseRepository.findBySurveyId(surveyId, pageRequest);
        }

        List<QuestionResponseDTO> dtos = resultPage.getContent().stream()
                .map(r -> QuestionResponseDTO.fromEntity(r, true))
                .collect(Collectors.toList());

        int lastPage = Math.max(resultPage.getTotalPages(), 1);

        return new QuestionResponsesWrapper(page, lastPage, pageSize, resultPage.getTotalElements(), dtos);
    }

    public Certificate getCertificate(Long certificateId) {
        return certificateRepository.findById(certificateId)
                .orElseThrow(() -> new RuntimeException("Certificate not found with id: " + certificateId));
    }
}