package com.zync.network.core.config;



import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.zync.network.core.domain.ZID;

import java.io.IOException;

public class ZIDDeserializer extends JsonDeserializer<ZID> {

    @Override
    public ZID deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return ZID.from(jsonParser.getValueAsString());
    }
}
