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
import spssoftware.opencare.resource.Appointment;
import spssoftware.opencare.resource.assembler.AppointmentAssembler;
import spssoftware.opencare.service.AppointmentService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private AppointmentService appointmentService;
    private AppointmentAssembler appointmentAssembler;

    @Autowired
    public AppointmentController(AppointmentService appointmentService,
                                 AppointmentAssembler appointmentAssembler) {

        this.appointmentService = appointmentService;
        this.appointmentAssembler = appointmentAssembler;
    }

    @RequestMapping(
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE})
    public Resources<?> find(@RequestParam(value = "fields", required = false) List<String> fields,
                             @RequestParam(required = false)  MultiValueMap<String, String> constraints) {

        List<Appointment> results = appointmentService.find(fields, constraints).stream().map(o -> appointmentAssembler.toResource(o)).collect(Collectors.toList());

        Link self = linkTo(methodOn(AppointmentController.class).find(fields, constraints)).withSelfRel();
        if (results.isEmpty()) {
            EmbeddedWrapper embeddedWrapper = new EmbeddedWrappers(false).emptyCollectionOf(Appointment.class);
            return new Resources<>(Collections.singletonList(embeddedWrapper), self);
        } else {
            return new Resources<>(results, self);
        }
    }

    @RequestMapping(
            value = "/{id}",
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Appointment> get(@PathVariable("id") String id) {

        spssoftware.opencare.domain.Appointment entity = appointmentService.get(id);

        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(appointmentAssembler.toResource(entity), HttpStatus.OK);
        }
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Appointment create(@RequestBody spssoftware.opencare.domain.Appointment appointment) {

        return appointmentAssembler.toResource(appointmentService.create(appointment));
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Appointment> patch(@PathVariable("id") String id, @RequestBody JSONObject patch) {

        spssoftware.opencare.domain.Appointment entity = appointmentService.patch(id, patch);

        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(appointmentAssembler.toResource(entity), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable("id") String id) {

        appointmentService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}