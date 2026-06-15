package com.skyworld.skyform.controller;

import com.skyworld.skyform.entity.QuestionResponseDTO;
import com.skyworld.skyform.entity.QuestionResponsesWrapper;
import com.skyworld.skyform.service.SurveyResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/surveys/{surveyId}/responses")
public class SurveyResponseController {

    @Autowired
    private SurveyResponseService surveyResponseService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<QuestionResponseDTO> submitResponse(
            @PathVariable Long surveyId,
            HttpServletRequest request) throws IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        Map<String, String> formFields = new HashMap<>();
        Map<String, List<MultipartFile>> files = new HashMap<>();

        Iterator<String> paramNames = multipartRequest.getParameterNames().asIterator();
        while (paramNames.hasNext()) {
            String name = paramNames.next();
            formFields.put(name, multipartRequest.getParameter(name));
        }

        Map<String, List<MultipartFile>> fileMap = multipartRequest.getMultiFileMap();
        for (Map.Entry<String, List<MultipartFile>> entry : fileMap.entrySet()) {
            files.put(entry.getKey(), entry.getValue());
        }

        QuestionResponseDTO result = surveyResponseService.submitResponse(surveyId, formFields, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<QuestionResponsesWrapper> getResponses(
            @PathVariable Long surveyId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String email) {

        QuestionResponsesWrapper wrapper = surveyResponseService.getResponses(surveyId, page, pageSize, email);
        return ResponseEntity.ok(wrapper);
    }
}