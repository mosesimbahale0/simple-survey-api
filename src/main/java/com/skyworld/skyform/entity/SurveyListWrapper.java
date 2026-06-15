package com.skyworld.skyform.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "surveys")
public class SurveyListWrapper {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "survey")
    private List<Survey> surveys;

    public List<Survey> getSurveys() { return surveys; }
    public void setSurveys(List<Survey> surveys) { this.surveys = surveys; }
}