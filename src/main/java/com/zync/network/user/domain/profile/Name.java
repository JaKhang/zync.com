package com.zync.network.user.domain.profile;


import jakarta.persistence.Embeddable;

@Embeddable
public record Name(
        String firstName,
        String middleName,
        String lastName
) {

}
