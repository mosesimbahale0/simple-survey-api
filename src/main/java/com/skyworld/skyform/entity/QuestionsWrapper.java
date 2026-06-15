package com.skyworld.skyform.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "questions")
public class QuestionsWrapper {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "question")
    private List<QuestionDTO> question;

    public QuestionsWrapper() {}

    public QuestionsWrapper(List<QuestionDTO> question) {
        this.question = question;
    }

    public List<QuestionDTO> getQuestion() { return question; }
    public void setQuestion(List<QuestionDTO> question) { this.question = question; }
}