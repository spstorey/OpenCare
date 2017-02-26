package spssoftware.opencare.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spssoftware.opencare.resource.Root;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/")
public class RootController {

    @RequestMapping(produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE})
    public Root root() {

        Root root = new Root();
        root.setMessage("Welcome to OpenCare. This system is owned by SPS Software Ltd.");

        root.add(linkTo(methodOn(RootController.class).root()).withSelfRel());
        root.add(linkTo(methodOn(StaffController.class).find(null, null)).withRel("staff"));
        root.add(linkTo(methodOn(OrganisationController.class).find(null,null)).withRel("organisations"));
        root.add(linkTo(methodOn(ClinicController.class).find(null,null)).withRel("clinics"));
        root.add(linkTo(methodOn(AppointmentController.class).find(null,null)).withRel("appointments"));
        root.add(linkTo(methodOn(PatientController.class).find(null,null)).withRel("patients"));
        return root;
    }
}

