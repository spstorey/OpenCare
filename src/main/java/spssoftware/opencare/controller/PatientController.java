package spssoftware.opencare.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrapper;
import org.springframework.hateoas.core.EmbeddedWrappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import spssoftware.opencare.resource.Patient;
import spssoftware.opencare.resource.assembler.PatientAssembler;
import spssoftware.opencare.service.PatientService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private PatientService patientService;
    private PatientAssembler patientAssembler;

    @Autowired
    public PatientController(PatientService patientService,
                             PatientAssembler patientAssembler) {

        this.patientService = patientService;
        this.patientAssembler = patientAssembler;
    }

    @RequestMapping(
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE})
    public Resources<?> find(@RequestParam(value = "fields", required = false) List<String> fields,
                                        @RequestParam(value = "constraints", required = false)  MultiValueMap<String, String> constraints) {

        List<Patient> results = patientService.find(fields, constraints).stream().map(o -> patientAssembler.toResource(o)).collect(Collectors.toList());

        Link self = linkTo(methodOn(PatientController.class).find(fields, constraints)).withSelfRel();
        if (results.isEmpty()) {
            EmbeddedWrapper embeddedWrapper = new EmbeddedWrappers(false).emptyCollectionOf(Patient.class);
            return new Resources<>(Collections.singletonList(embeddedWrapper), self);
        } else {
            return new Resources<>(results, self);
        }
    }

    @RequestMapping(
            value = "/{id}",
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Patient> get(@PathVariable("id") String id) {

        spssoftware.opencare.domain.Patient entity = patientService.get(id);

        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(patientAssembler.toResource(entity), HttpStatus.OK);
        }
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Patient create(@RequestBody spssoftware.opencare.domain.Patient patient) {

        return patientAssembler.toResource(patientService.create(patient));
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Patient> patch(@PathVariable("id") String id, @RequestBody JSONObject patch) {

        spssoftware.opencare.domain.Patient entity = patientService.patch(id, patch);

        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(patientAssembler.toResource(entity), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable("id") String id) {

        patientService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}