package com.zync.network.media.application.events;

import com.zync.network.core.domain.ZID;
import com.zync.network.media.domain.Dimension;
import com.zync.network.media.domain.Variant;
import org.springframework.http.MediaType;

import java.util.List;

public record ImageUploadedEvent(ZID id, ZID uploadBy, List<Variant> variants, long size, String reference, Dimension dimension, MediaType mediaType) {
}
