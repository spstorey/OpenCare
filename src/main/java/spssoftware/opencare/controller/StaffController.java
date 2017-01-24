package spssoftware.opencare.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import spssoftware.opencare.resource.Staff;
import spssoftware.opencare.resource.assembler.StaffAssembler;
import spssoftware.opencare.service.StaffService;

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
    public Resources<Staff> find(@RequestParam(value = "fields", required = false) List<String> fields,
                                 @RequestParam(value = "constraints", required = false) MultiValueMap<String, String> constraints) {
        return new Resources<>(
                staffService.find(fields, constraints)
                        .stream()
                        .map(o -> staffAssembler.toResource(o)).collect(Collectors.toList()),
                linkTo(methodOn(StaffController.class).find(fields, constraints)).withSelfRel()
        );
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
    public Staff add(@RequestBody spssoftware.opencare.domain.Staff Staff) {

        return staffAssembler.toResource(staffService.save(Staff));
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Staff update(@PathVariable("id") String id, @RequestBody spssoftware.opencare.domain.Staff Staff) {

        Staff.setStaffId(id);
        return staffAssembler.toResource(staffService.save(Staff));
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