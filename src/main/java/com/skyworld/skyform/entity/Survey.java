package com.skyworld.skyform.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;

@Entity
@Table(name = "surveys")
@JacksonXmlRootElement(localName = "survey")
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JacksonXmlProperty(isAttribute = true, localName = "id")
    private Long id;

    @Column(nullable = false)
    @JacksonXmlProperty(localName = "name")
    private String name;

    @Column(columnDefinition = "TEXT")
    @JacksonXmlProperty(localName = "description")
    private String description;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}