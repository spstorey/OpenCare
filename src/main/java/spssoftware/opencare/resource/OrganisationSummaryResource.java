package spssoftware.opencare.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import spssoftware.opencare.controller.OrganisationController;
import spssoftware.opencare.domain.Organisation;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Relation(collectionRelation = "organisations")
public class OrganisationSummaryResource extends ResourceSupport {

}