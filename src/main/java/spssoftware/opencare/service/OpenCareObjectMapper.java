package spssoftware.opencare.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OpenCareObjectMapper {

    private final ObjectMapper objectMapper;

    @Autowired
    public OpenCareObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T patchObject(JSONObject patchJson, T original) {
        try {
            JSONObject makeJson = JSONObject.parseObject(objectMapper.writeValueAsString(original));
            makeJson.putAll(patchJson);
            makeJson.remove("links");

            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return (T) objectMapper.readValue(makeJson.toString(), original.getClass());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}