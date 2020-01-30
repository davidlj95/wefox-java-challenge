package com.api.sample.restful.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class JSONService {

    private final ObjectMapper objectMapper;

    public Object fromBytes(byte[] jsonBytes) throws IOException {
        return objectMapper.readTree(jsonBytes);
    }

    public byte[] toBytes(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsBytes(obj);
    }
}
