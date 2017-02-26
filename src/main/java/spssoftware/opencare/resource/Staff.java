package spssoftware.opencare.resource;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Relation(collectionRelation = "staffList")
@JsonFilter("Staff")
@Data
public class Staff extends ResourceSupport {

    private String staffId;
    private String title;
    private String firstName;
    private String middleNames;
    private String surname;
    private LocalDate dateOfBirth;
    private String username;
    private String password;
    private String role;
    private String status;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}