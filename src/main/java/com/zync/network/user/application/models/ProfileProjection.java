package com.zync.network.user.application.models;

import org.springframework.beans.factory.annotation.Value;

public interface ProfileProjection {
    String displayName();
    String bio();
    String birthday();
    String avatarUrl();
}
