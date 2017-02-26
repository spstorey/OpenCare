package spssoftware.opencare.resource.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;
import spssoftware.opencare.controller.PatientController;
import spssoftware.opencare.resource.Patient;

@Service
public class PatientAssembler extends ResourceAssemblerSupport<spssoftware.opencare.domain.Patient, Patient> {

    @Autowired
    private ResourceMapper resourceMapper;

    public PatientAssembler() {
        super(PatientController.class, Patient.class);
    }

    @Override
    public Patient toResource(spssoftware.opencare.domain.Patient entity) {

        Patient resource = createResourceWithId(entity.getPatientId(), entity);
        resourceMapper.map(entity, resource);
        return resource;
    }
}
