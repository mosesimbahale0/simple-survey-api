package com.skyworld.skyform.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class FileProperties {

    @Column(name = "file_format")
    @JacksonXmlProperty(isAttribute = true, localName = "format")
    private String format;

    @Column(name = "max_file_size")
    @JacksonXmlProperty(isAttribute = true, localName = "max_file_size")
    private Integer maxFileSize;

    @Column(name = "max_file_size_unit")
    @JacksonXmlProperty(isAttribute = true, localName = "max_file_size_unit")
    private String maxFileSizeUnit;

    @Column(name = "file_multiple")
    @JacksonXmlProperty(isAttribute = true, localName = "multiple")
    private String multiple;

    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }

    public Integer getMaxFileSize() { return maxFileSize; }
    public void setMaxFileSize(Integer maxFileSize) { this.maxFileSize = maxFileSize; }

    public String getMaxFileSizeUnit() { return maxFileSizeUnit; }
    public void setMaxFileSizeUnit(String maxFileSizeUnit) { this.maxFileSizeUnit = maxFileSizeUnit; }

    public String getMultiple() { return multiple; }
    public void setMultiple(String multiple) { this.multiple = multiple; }
}