package com.zync.network.core.domain;

import java.time.temporal.ChronoUnit;

public record TimePayload(int value, ChronoUnit unit) {
}
