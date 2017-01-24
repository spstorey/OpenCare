package spssoftware.opencare.resource;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;

@Relation(collectionRelation = "organisations")
@JsonFilter("Organisation")
@Data
public class Organisation extends ResourceSupport {

    private String organisationId;
    private String name;
    private String description;
    private String websiteUrl;
    private String type;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}