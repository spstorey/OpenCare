package spssoftware.opencare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spssoftware.opencare.config.Config;
import spssoftware.opencare.config.Environment;
import spssoftware.opencare.resource.Root;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/")
public class RootController {

    private Config config;

    @Autowired
    public RootController(Config config) {
        this.config = config;
    }

    @RequestMapping(produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE})
    public Root list() {

        Root root = new Root();
        root.setMessage("Welcome to OpenCare. This system is owned by " + config.getOwner() + " and is running in environment " + Environment.getEnvironment());

        root.add(linkTo(methodOn(RootController.class).list()).withSelfRel());
        //root.add(linkTo(methodOn(UserController.class).list()).withRel("users"));
        root.add(linkTo(methodOn(OrganisationController.class).list(null,null,null,null,null)).withRel("organisations"));
        //root.add(linkTo(methodOn(PatientController.class).list(null,null,null)).withRel("patients"));
        return root;
    }
}

