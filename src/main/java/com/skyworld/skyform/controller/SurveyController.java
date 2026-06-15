package com.skyworld.skyform.controller;

import com.skyworld.skyform.entity.Survey;
import com.skyworld.skyform.entity.SurveyListWrapper;
import com.skyworld.skyform.service.SurveyService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    private final SurveyService service;

    public SurveyController(SurveyService service) {
        this.service = service;
    }

    // POST /api/surveys
    @PostMapping(
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public ResponseEntity<Survey> create(@RequestBody Survey survey) {
        return ResponseEntity.ok(service.create(survey));
    }

    // GET /api/surveys
    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<SurveyListWrapper> getAll() {
        return ResponseEntity.ok(new SurveyListWrapper(service.findAll()));
    }

    // GET /api/surveys/{id}
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Survey> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/surveys/{id}
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public ResponseEntity<Survey> update(@PathVariable Long id, @RequestBody Survey survey) {
        return ResponseEntity.ok(service.update(id, survey));
    }

    // DELETE /api/surveys/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}