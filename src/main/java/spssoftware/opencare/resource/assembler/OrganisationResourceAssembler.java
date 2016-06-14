package spssoftware.opencare.resource.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;
import spssoftware.opencare.controller.OrganisationController;
import spssoftware.opencare.domain.Organisation;
import spssoftware.opencare.resource.OrganisationResource;

@Service
public class OrganisationResourceAssembler extends ResourceAssemblerSupport<Organisation, OrganisationResource> {

    @Autowired
    private ResourceMapper resourceMapper;

    public OrganisationResourceAssembler() {
        super(OrganisationController.class, OrganisationResource.class);
    }

    @Override
    public OrganisationResource toResource(Organisation entity) {
        OrganisationResource resource = createResourceWithId(entity.getId(), entity);
        resourceMapper.map(entity, resource);
        return resource;
    }
}
