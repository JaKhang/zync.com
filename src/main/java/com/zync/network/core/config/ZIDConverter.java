package com.zync.network.core.config;

import com.zync.network.core.domain.ZID;
import org.springframework.core.convert.converter.Converter;

public class ZIDConverter implements Converter<String, ZID> {
    @Override
    public ZID convert(String source) {
        return ZID.from(source);
    }
}
