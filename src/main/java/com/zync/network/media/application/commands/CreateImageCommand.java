package com.zync.network.media.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Request;
import com.zync.network.media.domain.Dimension;
import com.zync.network.media.domain.Variant;
import org.springframework.http.MediaType;

import java.util.List;

public record CreateImageCommand(ZID id, ZID uploadBy, List<Variant> variants, long size, String reference, boolean uploaded, Dimension dimension, MediaType mediaType) implements Request<ZID> {
}
