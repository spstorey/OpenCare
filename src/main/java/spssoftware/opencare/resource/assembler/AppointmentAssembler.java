package spssoftware.opencare.resource.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;
import spssoftware.opencare.controller.AppointmentController;
import spssoftware.opencare.resource.Appointment;

@Service
public class AppointmentAssembler extends ResourceAssemblerSupport<spssoftware.opencare.domain.Appointment, Appointment> {

    @Autowired
    private ResourceMapper resourceMapper;

    public AppointmentAssembler() {
        super(AppointmentController.class, Appointment.class);
    }

    @Override
    public Appointment toResource(spssoftware.opencare.domain.Appointment entity) {

        Appointment resource = createResourceWithId(entity.getAppointmentId(), entity);
        resourceMapper.map(entity, resource);
        return resource;
    }
}
