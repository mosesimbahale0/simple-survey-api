package com.skyworld.skyform.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

@JacksonXmlRootElement(localName = "option")
public class QuestionOptionResponseDTO {

    @JacksonXmlProperty(isAttribute = true, localName = "id")
    private Long id;

    @JacksonXmlProperty(isAttribute = true, localName = "value")
    private String value;

    @JacksonXmlText
    private String label;

    public static QuestionOptionResponseDTO fromEntity(QuestionOption o) {
        QuestionOptionResponseDTO dto = new QuestionOptionResponseDTO();
        dto.id = o.getId();
        dto.value = o.getValue();
        dto.label = o.getLabel();
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
}