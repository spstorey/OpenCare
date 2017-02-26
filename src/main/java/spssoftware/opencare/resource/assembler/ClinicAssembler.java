package spssoftware.opencare.resource.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;
import spssoftware.opencare.controller.ClinicController;
import spssoftware.opencare.resource.Clinic;

@Service
public class ClinicAssembler extends ResourceAssemblerSupport<spssoftware.opencare.domain.Clinic, Clinic> {

    @Autowired
    private ResourceMapper resourceMapper;

    public ClinicAssembler() {
        super(ClinicController.class, Clinic.class);
    }

    @Override
    public Clinic toResource(spssoftware.opencare.domain.Clinic entity) {

        Clinic resource = createResourceWithId(entity.getClinicId(), entity);
        resourceMapper.map(entity, resource);
        return resource;
    }
}
