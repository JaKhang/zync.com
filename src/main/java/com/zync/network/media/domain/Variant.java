package com.zync.network.media.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record Variant(String reference, Dimension dimension){
}
