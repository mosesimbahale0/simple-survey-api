package com.skyworld.skyform.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.*;
import java.util.List;

@JacksonXmlRootElement(localName = "question")
public class QuestionRequest {

    @JacksonXmlProperty(isAttribute = true, localName = "name")
    private String name;

    @JacksonXmlProperty(isAttribute = true, localName = "type")
    private String type;

    @JacksonXmlProperty(isAttribute = true, localName = "required")
    private String required;

    @JacksonXmlProperty(localName = "text")
    private String text;

    @JacksonXmlProperty(localName = "description")
    private String description;

    @JacksonXmlProperty(localName = "options")
    private OptionsRequest options;

    @JacksonXmlProperty(localName = "file_properties")
    private FilePropertiesRequest fileProperties;

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
    public OptionsRequest getOptions() { return options; }
    public void setOptions(OptionsRequest options) { this.options = options; }
    public FilePropertiesRequest getFileProperties() { return fileProperties; }
    public void setFileProperties(FilePropertiesRequest fileProperties) { this.fileProperties = fileProperties; }

    public static class OptionsRequest {
        @JacksonXmlProperty(isAttribute = true, localName = "multiple")
        private String multiple;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "option")
        private List<OptionRequest> option;

        public String getMultiple() { return multiple; }
        public void setMultiple(String multiple) { this.multiple = multiple; }
        public List<OptionRequest> getOption() { return option; }
        public void setOption(List<OptionRequest> option) { this.option = option; }
    }

    public static class OptionRequest {
        @JacksonXmlProperty(isAttribute = true, localName = "value")
        private String value;

        @JacksonXmlText
        private String label;

        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
        public String getLabel() { return label; }
        public void setLabel(String label) { this.label = label; }
    }

    public static class FilePropertiesRequest {
        @JacksonXmlProperty(isAttribute = true, localName = "format")
        private String format;
        @JacksonXmlProperty(isAttribute = true, localName = "max_file_size")
        private Integer maxFileSize;
        @JacksonXmlProperty(isAttribute = true, localName = "max_file_size_unit")
        private String maxFileSizeUnit;
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
}