package com.zync.network.media.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record Dimension(int width, int height) {
}
