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
import spssoftware.opencare.resource.Staff;
import spssoftware.opencare.resource.assembler.StaffAssembler;
import spssoftware.opencare.service.StaffService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/staff")
public class StaffController {

    private StaffService staffService;
    private StaffAssembler staffAssembler;

    @Autowired
    public StaffController(StaffService StaffService,
                           StaffAssembler StaffAssembler) {

        this.staffService = StaffService;
        this.staffAssembler = StaffAssembler;
    }

    @RequestMapping(
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE})
    public Resources<?> find(@RequestParam(value = "fields", required = false) List<String> fields,
                             @RequestParam(required = false) MultiValueMap<String, String> constraints) {


        List<Staff> staff = staffService.find(fields, constraints).stream().map(o -> staffAssembler.toResource(o)).collect(Collectors.toList());

        Link self = linkTo(methodOn(StaffController.class).find(fields, constraints)).withSelfRel();
        if (staff.isEmpty()) {
            EmbeddedWrapper embeddedWrapper = new EmbeddedWrappers(false).emptyCollectionOf(Staff.class);
            return new Resources<>(Collections.singletonList(embeddedWrapper), self);
        } else {
            return new Resources<>(staff, self);
        }
    }

    @RequestMapping(
            value = "/{id}",
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE})
    public Staff get(@PathVariable("id") String id) {

        spssoftware.opencare.domain.Staff entity = staffService.get(id);
        return staffAssembler.toResource(entity);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Staff add(@RequestBody spssoftware.opencare.domain.Staff staff) {

        return staffAssembler.toResource(staffService.create(staff));
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Staff patch(@PathVariable("id") String id, @RequestBody JSONObject patch) {

        staffService.patch(id, patch);

        return get(id);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable("id") String id) {

        staffService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}