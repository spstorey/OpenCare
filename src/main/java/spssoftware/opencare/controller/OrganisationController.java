package spssoftware.opencare.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spssoftware.opencare.domain.Organisation;
import spssoftware.opencare.resource.OrganisationResource;
import spssoftware.opencare.resource.OrganisationSummaryResource;
import spssoftware.opencare.resource.assembler.OrganisationResourceAssembler;
import spssoftware.opencare.resource.assembler.SummaryResourceAssembler;
import spssoftware.opencare.service.OrganisationService;

import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/organisations")
public class OrganisationController {

    private OrganisationService organisationService;
    private OrganisationResourceAssembler organisationResourceAssembler;
    private SummaryResourceAssembler organisationSummaryResourceAssembler;

    @Autowired
    public OrganisationController(OrganisationService organisationService,
                                  OrganisationResourceAssembler organisationResourceAssembler,
                                  SummaryResourceAssembler organisationSummaryResourceAssembler) {
        this.organisationService = organisationService;
        this.organisationResourceAssembler = organisationResourceAssembler;
        this.organisationSummaryResourceAssembler = organisationSummaryResourceAssembler;
    }

    @RequestMapping(
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE})
    public Resources<OrganisationSummaryResource> list(@RequestParam(value = "name", required = false) String name,
                                                       @RequestParam(value = "town", required = false) String town,
                                                       @RequestParam(value = "county", required = false) String county,
                                                       @RequestParam(value = "postCode", required = false) String postCode,
                                                       @RequestParam(value = "country", required = false) String country,
                                                       @RequestParam(value = "type", required = false) String type) {
        return new Resources<>(
                organisationService.list(name, town, county, country, postCode, type)
                        .stream()
                        .map(o -> organisationSummaryResourceAssembler.toResource(o)).collect(Collectors.toList()),
                linkTo(methodOn(OrganisationController.class).list(name, town, county, postCode, country, type)).withSelfRel()
        );
    }

    @RequestMapping(
            value = "/{id}",
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE})
    public OrganisationResource get(@PathVariable("id") String id) {

        Organisation entity = organisationService.get(id);
        return organisationResourceAssembler.toResource(entity);
    }


    @RequestMapping(
            method = RequestMethod.POST,
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrganisationResource add(@RequestBody Organisation organisation) {

        String id = organisationService.add(organisation);

        return get(id);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrganisationResource update(@PathVariable("id") String id, @RequestBody Organisation organisation) {

        organisation.setId(id);
        organisationService.update(organisation);

        return get(organisation.getId());
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrganisationResource patch(@PathVariable("id") String id, @RequestBody JSONObject patch) {

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