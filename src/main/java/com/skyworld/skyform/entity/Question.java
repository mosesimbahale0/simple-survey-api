package com.skyworld.skyform.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Survey survey;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String required;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "options_multiple")
    private String optionsMultiple;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<QuestionOption> options;

    @Embedded
    private FileProperties fileProperties;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Survey getSurvey() { return survey; }
    public void setSurvey(Survey survey) { this.survey = survey; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getRequired() { return required; }
    public void setRequired(String required) { this.required = required; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getOptionsMultiple() { return optionsMultiple; }
    public void setOptionsMultiple(String optionsMultiple) { this.optionsMultiple = optionsMultiple; }

    public List<QuestionOption> getOptions() { return options; }
    public void setOptions(List<QuestionOption> options) {
        this.options = options;
        if (options != null) {
            for (QuestionOption opt : options) {
                opt.setQuestion(this);
            }
        }
    }

    public FileProperties getFileProperties() { return fileProperties; }
    public void setFileProperties(FileProperties fileProperties) { this.fileProperties = fileProperties; }
}