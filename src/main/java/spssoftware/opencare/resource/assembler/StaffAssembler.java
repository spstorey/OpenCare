package spssoftware.opencare.resource.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;
import spssoftware.opencare.controller.OrganisationController;
import spssoftware.opencare.controller.StaffController;
import spssoftware.opencare.resource.Organisation;
import spssoftware.opencare.resource.Staff;

@Service
public class StaffAssembler extends ResourceAssemblerSupport<spssoftware.opencare.domain.Staff, Staff> {

    @Autowired
    private ResourceMapper resourceMapper;

    public StaffAssembler() {
        super(StaffController.class, Staff.class);
    }

    @Override
    public Staff toResource(spssoftware.opencare.domain.Staff entity) {
        Staff resource = createResourceWithId(entity.getStaffId(), entity);
        resourceMapper.map(entity, resource);
        return resource;
    }
}
