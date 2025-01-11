package com.zync.network.core.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.zync.network.core.domain.ZID;


import java.io.IOException;

public class ZIDSerializer extends JsonSerializer<ZID> {

    @Override
    public void serialize(ZID ZID, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(ZID.toString());
    }
}
