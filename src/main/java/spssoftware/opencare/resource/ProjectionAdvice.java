package spssoftware.opencare.resource;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class ProjectionAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType, MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {

        SimpleFilterProvider filters = new SimpleFilterProvider();
        filters.addFilter("Organisation", SimpleBeanPropertyFilter.serializeAll());
        bodyContainer.setFilters(filters);

        if (request instanceof ServletServerHttpRequest) { //if instance resource, prefix content.
            String[] fields = ((ServletServerHttpRequest) request).getServletRequest().getParameterValues("fields");

            if (fields != null && fields.length > 0) {

                Set<String> filterFields = Arrays.stream(fields).flatMap(param -> Arrays.stream(param.split(","))).collect(Collectors.toSet());
                filterFields.add("_links");

                filters.addFilter("Organisation", SimpleBeanPropertyFilter.filterOutAllExcept(filterFields));
            }
        }
    }
}
