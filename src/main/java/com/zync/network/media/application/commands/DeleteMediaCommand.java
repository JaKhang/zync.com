package com.zync.network.media.application.commands;

import com.zync.network.core.domain.ZID;

import java.util.List;

public record DeleteMediaCommand(List<ZID> ids) {
}
