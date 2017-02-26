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
import spssoftware.opencare.resource.Clinic;
import spssoftware.opencare.resource.assembler.ClinicAssembler;
import spssoftware.opencare.service.ClinicService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/clinics")
public class ClinicController {

    private ClinicService clinicService;
    private ClinicAssembler clinicAssembler;

    @Autowired
    public ClinicController(ClinicService clinicService,
                            ClinicAssembler clinicAssembler) {

        this.clinicService = clinicService;
        this.clinicAssembler = clinicAssembler;
    }

    @RequestMapping(
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE})
    public Resources<?> find(@RequestParam(value = "fields", required = false) List<String> fields,
                             @RequestParam(required = false)  MultiValueMap<String, String> constraints) {

        List<Clinic> results = clinicService.find(fields, constraints).stream().map(o -> clinicAssembler.toResource(o)).collect(Collectors.toList());

        Link self = linkTo(methodOn(ClinicController.class).find(fields, constraints)).withSelfRel();
        if (results.isEmpty()) {
            EmbeddedWrapper embeddedWrapper = new EmbeddedWrappers(false).emptyCollectionOf(Clinic.class);
            return new Resources<>(Collections.singletonList(embeddedWrapper), self);
        } else {
            return new Resources<>(results, self);
        }
    }

    @RequestMapping(
            value = "/{id}",
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Clinic> get(@PathVariable("id") String id) {

        spssoftware.opencare.domain.Clinic entity = clinicService.get(id);

        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(clinicAssembler.toResource(entity), HttpStatus.OK);
        }
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Clinic create(@RequestBody spssoftware.opencare.domain.Clinic clinic) {

        return clinicAssembler.toResource(clinicService.create(clinic));
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Clinic> patch(@PathVariable("id") String id, @RequestBody JSONObject patch) {

        spssoftware.opencare.domain.Clinic entity = clinicService.patch(id, patch);

        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(clinicAssembler.toResource(entity), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable("id") String id) {

        clinicService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}