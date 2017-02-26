package spssoftware.opencare.resource;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;

@Relation(collectionRelation = "clinics")
@JsonFilter("Clinic")
@Data
public class Clinic extends ResourceSupport {

    private String clinicId;
    private String organisationId;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer appointmentDuration;
    private Integer capacity;
    private String status;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}