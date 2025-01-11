package com.zync.network.user.domain.user;

import com.zync.network.core.domain.ZID;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;

import java.util.HashSet;
import java.util.Set;

public record PrivacySettings(
        @Column(name = "is_private")
        boolean isPrivate,

        @Column(name = "is_hide_activity")
        boolean hideActivity
) {


    public static PrivacySettings defaultSettings() {
        return new PrivacySettings(false, false);
    }
}