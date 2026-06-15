package com.skyworld.skyform.entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JacksonXmlRootElement(localName = "question_response")
@JsonPropertyOrder({"responseId", "fullName", "emailAddress"})
public class QuestionResponseDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JacksonXmlProperty(localName = "response_id")
    private Long responseId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JacksonXmlProperty(localName = "full_name")
    private String fullName;

    @JacksonXmlProperty(localName = "email_address")
    private String emailAddress;

    // dynamic question-name -> answer-text fields, serialized as <gender>..</gender>, <programming_stack>..</programming_stack>, etc.
    private Map<String, String> dynamicAnswers = new LinkedHashMap<>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JacksonXmlProperty(localName = "certificates")
    private CertificatesDTO certificates;

    @JacksonXmlProperty(localName = "date_responded")
    private String dateResponded;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static QuestionResponseDTO fromEntity(SurveyResponse r, boolean includeResponseId) {
        QuestionResponseDTO dto = new QuestionResponseDTO();
        if (includeResponseId) {
            dto.responseId = r.getId();
        }
        dto.fullName = r.getFullName();
        dto.emailAddress = r.getEmailAddress();
        dto.dateResponded = r.getSubmittedAt() != null ? r.getSubmittedAt().format(FORMATTER) : null;

        if (r.getAnswers() != null) {
            for (Answer a : r.getAnswers()) {
                String qName = a.getQuestion().getName();
                if (!"full_name".equals(qName) && !"email_address".equals(qName)) {
                    dto.dynamicAnswers.put(qName, a.getAnswerText());
                }
            }
        }

        if (r.getCertificates() != null && !r.getCertificates().isEmpty()) {
            CertificatesDTO certs = new CertificatesDTO();
            certs.certificate = r.getCertificates().stream()
                    .map(c -> new CertificateRefDTO(c.getId(), c.getOriginalFilename()))
                    .collect(Collectors.toList());
            dto.certificates = certs;
        }

        return dto;
    }

    public Long getResponseId() { return responseId; }
    public void setResponseId(Long responseId) { this.responseId = responseId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    @JsonAnyGetter
    public Map<String, String> getDynamicAnswers() { return dynamicAnswers; }

    public CertificatesDTO getCertificates() { return certificates; }
    public void setCertificates(CertificatesDTO certificates) { this.certificates = certificates; }
    public String getDateResponded() { return dateResponded; }
    public void setDateResponded(String dateResponded) { this.dateResponded = dateResponded; }

    public static class CertificatesDTO {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "certificate")
        private List<CertificateRefDTO> certificate;

        public List<CertificateRefDTO> getCertificate() { return certificate; }
        public void setCertificate(List<CertificateRefDTO> certificate) { this.certificate = certificate; }
    }

    public static class CertificateRefDTO {
        @JacksonXmlProperty(isAttribute = true, localName = "id")
        private Long id;

        @JacksonXmlText
        private String filename;

        public CertificateRefDTO() {}
        public CertificateRefDTO(Long id, String filename) {
            this.id = id;
            this.filename = filename;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getFilename() { return filename; }
        public void setFilename(String filename) { this.filename = filename; }
    }
}