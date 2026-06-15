package com.skyworld.skyform.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.stream.Collectors;

@JacksonXmlRootElement(localName = "question")
public class QuestionDTO {

    @JacksonXmlProperty(isAttribute = true, localName = "id")
    private Long id;

    @JacksonXmlProperty(isAttribute = true, localName = "name")
    private String name;

    @JacksonXmlProperty(isAttribute = true, localName = "type")
    private String type;

    @JacksonXmlProperty(isAttribute = true, localName = "required")
    private String required;

    @JacksonXmlProperty(localName = "text")
    private String text;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JacksonXmlProperty(localName = "description")
    private String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JacksonXmlProperty(localName = "options")
    private OptionsDTO options;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JacksonXmlProperty(localName = "file_properties")
    private FilePropertiesDTO fileProperties;

    public static QuestionDTO fromEntity(Question q) {
        QuestionDTO dto = new QuestionDTO();
        dto.id = q.getId();
        dto.name = q.getName();
        dto.type = q.getType();
        dto.required = q.getRequired();
        dto.text = q.getText();
        dto.description = q.getDescription();

        if ("choice".equals(q.getType()) && q.getOptions() != null) {
            OptionsDTO opts = new OptionsDTO();
            opts.multiple = q.getOptionsMultiple();
            opts.option = q.getOptions().stream()
                    .map(o -> new OptionDTO(o.getValue(), o.getLabel()))
                    .collect(Collectors.toList());
            dto.options = opts;
        }

        if ("file".equals(q.getType()) && q.getFileProperties() != null) {
            FileProperties fp = q.getFileProperties();
            FilePropertiesDTO fpDto = new FilePropertiesDTO();
            fpDto.format = fp.getFormat();
            fpDto.maxFileSize = fp.getMaxFileSize();
            fpDto.maxFileSizeUnit = fp.getMaxFileSizeUnit();
            fpDto.multiple = fp.getMultiple();
            dto.fileProperties = fpDto;
        }

        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public OptionsDTO getOptions() { return options; }
    public void setOptions(OptionsDTO options) { this.options = options; }
    public FilePropertiesDTO getFileProperties() { return fileProperties; }
    public void setFileProperties(FilePropertiesDTO fileProperties) { this.fileProperties = fileProperties; }

    public static class OptionsDTO {
        @JacksonXmlProperty(isAttribute = true, localName = "multiple")
        private String multiple;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "option")
        private List<OptionDTO> option;

        public String getMultiple() { return multiple; }
        public void setMultiple(String multiple) { this.multiple = multiple; }
        public List<OptionDTO> getOption() { return option; }
        public void setOption(List<OptionDTO> option) { this.option = option; }
    }

    public static class OptionDTO {
        @JacksonXmlProperty(isAttribute = true, localName = "value")
        private String value;

        @JacksonXmlText
        private String label;

        public OptionDTO() {}
        public OptionDTO(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
        public String getLabel() { return label; }
        public void setLabel(String label) { this.label = label; }
    }

    public static class FilePropertiesDTO {
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