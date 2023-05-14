package com.github.skremiec.pionteching.web;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.skremiec.pionteching.atmservice.ServiceRequest;

import java.io.IOException;

public class ServiceRequestDeserializer extends JsonDeserializer<ServiceRequest> {
    @Override
    public ServiceRequest deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        final JsonNode jsonNode = context.readTree(parser);

        return new ServiceRequest(
            jsonNode.get("region").asInt(),
            ServiceRequest.Type.valueOf(jsonNode.get("requestType").asText()),
            jsonNode.get("atmId").asInt()
        );
    }
}
