package spssoftware.opencare.resource;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "appointments")
@JsonFilter("Appointment")
@Data
public class Appointment extends ResourceSupport {

    private String appointmentId;
    private String clinicId;
    private String patientId;
    private String startTime;
    private String endTime;
    private String createdDate;
    private String modifiedDate;
}