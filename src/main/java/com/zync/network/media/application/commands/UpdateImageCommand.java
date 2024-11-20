package com.zync.network.media.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Notification;
import com.zync.network.media.domain.Dimension;
import com.zync.network.media.domain.Variant;
import org.springframework.http.MediaType;

import java.util.List;

public record UpdateImageCommand(ZID id, ZID uploadBy, List<Variant> variants, long size, String reference, boolean uploaded, Dimension dimension, MediaType mediaType) implements Notification {
}
