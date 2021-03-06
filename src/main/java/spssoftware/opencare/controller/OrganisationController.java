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
import spssoftware.opencare.resource.Organisation;
import spssoftware.opencare.resource.assembler.OrganisationAssembler;
import spssoftware.opencare.service.OrganisationService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/organisations")
public class OrganisationController {

    private OrganisationService organisationService;
    private OrganisationAssembler organisationAssembler;

    @Autowired
    public OrganisationController(OrganisationService organisationService,
                                  OrganisationAssembler organisationAssembler) {

        this.organisationService = organisationService;
        this.organisationAssembler = organisationAssembler;
    }

    @RequestMapping(
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE})
    public Resources<?> find(@RequestParam(value = "fields", required = false) List<String> fields,
                             @RequestParam(required = false)  MultiValueMap<String, String> constraints) {

        List<Organisation> results = organisationService.find(fields, constraints).stream().map(o -> organisationAssembler.toResource(o)).collect(Collectors.toList());

        Link self = linkTo(methodOn(OrganisationController.class).find(fields, constraints)).withSelfRel();
        if (results.isEmpty()) {
            EmbeddedWrapper embeddedWrapper = new EmbeddedWrappers(false).emptyCollectionOf(Organisation.class);
            return new Resources<>(Collections.singletonList(embeddedWrapper), self);
        } else {
            return new Resources<>(results, self);
        }
    }

    @RequestMapping(
            value = "/{id}",
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Organisation> get(@PathVariable("id") String id) {

        spssoftware.opencare.domain.Organisation entity = organisationService.get(id);

        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(organisationAssembler.toResource(entity), HttpStatus.OK);
        }
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Organisation create(@RequestBody spssoftware.opencare.domain.Organisation organisation) {

        return organisationAssembler.toResource(organisationService.create(organisation));
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Organisation> patch(@PathVariable("id") String id, @RequestBody JSONObject patch) {

        spssoftware.opencare.domain.Organisation entity = organisationService.patch(id, patch);

        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(organisationAssembler.toResource(entity), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable("id") String id) {

        organisationService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}