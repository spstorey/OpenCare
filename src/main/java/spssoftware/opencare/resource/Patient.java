package spssoftware.opencare.resource;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Relation(collectionRelation = "patients")
@JsonFilter("Patient")
@Data
public class Patient extends ResourceSupport {

    private String patientId;
    private String title;
    private String firstName;
    private String middleNames;
    private String surname;
    private LocalDate dateOfBirth;
    private LocalDate dateOfDeath;
    private String nhsNumber;
    private String nationalInsuranceNumber;
    private String houseNameNumber;
    private String street;
    private String locality;
    private String city;
    private String county;
    private String country;
    private String postcode;
    private String homePhone;
    private String workPhone;
    private String mobilePhone;
    private String email;
    private String nextOfKinName;
    private String nextOfKinPhone;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}