package spssoftware.opencare.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import spssoftware.opencare.resource.Organisation;
import spssoftware.opencare.resource.assembler.OrganisationAssembler;
import spssoftware.opencare.service.OrganisationService;

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
    public Resources<Organisation> find(@RequestParam(value = "fields", required = false) List<String> fields,
                                        @RequestParam(value = "constraints", required = false)  MultiValueMap<String, String> constraints) {
        return new Resources<>(
                organisationService.find(fields, constraints)
                        .stream()
                        .map(o -> organisationAssembler.toResource(o)).collect(Collectors.toList())
//                ,
//                linkTo(methodOn(OrganisationController.class).find(fields, constraints)).withSelfRel()
        );
    }

    @RequestMapping(
            value = "/{id}",
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE})
    public Organisation get(@PathVariable("id") String id) {

        spssoftware.opencare.domain.Organisation entity = organisationService.get(id);
        return organisationAssembler.toResource(entity);
    }


    @RequestMapping(
            method = RequestMethod.POST,
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Organisation add(@RequestBody spssoftware.opencare.domain.Organisation organisation) {

        return organisationAssembler.toResource(organisationService.save(organisation));
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Organisation update(@PathVariable("id") String id, @RequestBody spssoftware.opencare.domain.Organisation organisation) {

        organisation.setOrganisationId(id);
        return organisationAssembler.toResource(organisationService.save(organisation));
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Organisation patch(@PathVariable("id") String id, @RequestBody JSONObject patch) {

        organisationService.patch(id, patch);

        return get(id);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable("id") String id) {

        organisationService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}