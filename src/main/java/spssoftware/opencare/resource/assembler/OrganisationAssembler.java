package spssoftware.opencare.resource.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;
import spssoftware.opencare.controller.OrganisationController;
import spssoftware.opencare.resource.Organisation;

@Service
public class OrganisationAssembler extends ResourceAssemblerSupport<spssoftware.opencare.domain.Organisation, Organisation> {

    @Autowired
    private ResourceMapper resourceMapper;

    public OrganisationAssembler() {
        super(OrganisationController.class, Organisation.class);
    }

    @Override
    public Organisation toResource(spssoftware.opencare.domain.Organisation entity) {

        Organisation resource = createResourceWithId(entity.getOrganisationId(), entity);
        resourceMapper.map(entity, resource);
        return resource;
    }
}
