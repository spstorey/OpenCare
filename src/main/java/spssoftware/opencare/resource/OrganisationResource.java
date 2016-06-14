package spssoftware.opencare.resource;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class OrganisationResource extends ResourceSupport {

    private String name;
    private String description;
    private String websiteUrl;
    private String organisationType;
}