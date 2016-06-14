package spssoftware.opencare.resource;

import org.springframework.hateoas.ResourceSupport;

public class Root extends ResourceSupport {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
