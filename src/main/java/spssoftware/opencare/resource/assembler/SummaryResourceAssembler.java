package spssoftware.opencare.resource.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;
import spssoftware.opencare.controller.OrganisationController;
import spssoftware.opencare.domain.Organisation;
import spssoftware.opencare.resource.OrganisationResource;
import spssoftware.opencare.resource.OrganisationSummaryResource;

@Service
public class SummaryResourceAssembler extends ResourceAssemblerSupport<Organisation, OrganisationSummaryResource> {

    @Autowired
    private ResourceMapper resourceMapper;

    public SummaryResourceAssembler() {
        super(OrganisationController.class, OrganisationSummaryResource.class);
    }

    @Override
    public OrganisationSummaryResource toResource(Organisation entity) {
        OrganisationSummaryResource resource = createResourceWithId(entity.getId(), entity);
        resourceMapper.map(entity, resource);
        return resource;
    }
}
