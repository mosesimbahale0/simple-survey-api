package com.skyworld.skyform.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.*;
import java.util.List;

@JacksonXmlRootElement(localName = "question_responses")
public class QuestionResponsesWrapper {

    @JacksonXmlProperty(isAttribute = true, localName = "current_page")
    private int currentPage;

    @JacksonXmlProperty(isAttribute = true, localName = "last_page")
    private int lastPage;

    @JacksonXmlProperty(isAttribute = true, localName = "page_size")
    private int pageSize;

    @JacksonXmlProperty(isAttribute = true, localName = "total_count")
    private long totalCount;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "question_response")
    private List<QuestionResponseDTO> questionResponse;

    public QuestionResponsesWrapper() {}

    public QuestionResponsesWrapper(int currentPage, int lastPage, int pageSize, long totalCount, List<QuestionResponseDTO> questionResponse) {
        this.currentPage = currentPage;
        this.lastPage = lastPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.questionResponse = questionResponse;
    }

    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }
    public int getLastPage() { return lastPage; }
    public void setLastPage(int lastPage) { this.lastPage = lastPage; }
    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }
    public long getTotalCount() { return totalCount; }
    public void setTotalCount(long totalCount) { this.totalCount = totalCount; }
    public List<QuestionResponseDTO> getQuestionResponse() { return questionResponse; }
    public void setQuestionResponse(List<QuestionResponseDTO> questionResponse) { this.questionResponse = questionResponse; }
}