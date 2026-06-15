package com.skyworld.skyform.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import jakarta.persistence.*;

@Entity
@Table(name = "question_options")
public class QuestionOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JacksonXmlProperty(isAttribute = true, localName = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Question question;

    @Column(nullable = false)
    @JacksonXmlProperty(isAttribute = true, localName = "value")
    private String value;

    @Column(nullable = false)
    @JacksonXmlText
    private String label;

    @Transient
    @JacksonXmlProperty(isAttribute = true, localName = "multiple")
    private String multiple; // not persisted per-option; see note below

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
}