package com.zync.network.media.application.events;

import com.zync.network.core.domain.ZID;

import java.util.List;

public record MediaProcessingFailedEvent(List<ZID> ids) {
}
