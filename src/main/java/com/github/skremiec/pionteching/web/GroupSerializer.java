package com.github.skremiec.pionteching.web;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.skremiec.pionteching.onlinegame.Clan;
import com.github.skremiec.pionteching.onlinegame.Group;

import java.io.IOException;

public class GroupSerializer extends JsonSerializer<Group> {
    @Override
    public void serialize(Group group, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        generator.writeStartArray();
        for (Clan clan : group.getClans()) {
            generator.writeObject(clan);
        }
        generator.writeEndArray();
    }
}
