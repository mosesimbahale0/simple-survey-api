package com.skyworld.skyform.controller;

import com.skyworld.skyform.entity.Certificate;
import com.skyworld.skyform.service.SurveyResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    @Autowired
    private SurveyResponseService surveyResponseService;

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadCertificate(@PathVariable Long id) {
        Certificate cert = surveyResponseService.getCertificate(id);
        Resource resource = new FileSystemResource(cert.getFilePath());

        MediaType mediaType = cert.getContentType() != null
                ? MediaType.parseMediaType(cert.getContentType())
                : MediaType.APPLICATION_OCTET_STREAM;

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + cert.getOriginalFilename() + "\"")
                .body(resource);
    }
}