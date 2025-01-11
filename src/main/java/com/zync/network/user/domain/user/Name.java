package com.zync.network.user.domain.user;


import jakarta.persistence.Embeddable;

@Embeddable
public record Name(
        String firstName,
        String middleName,
        String lastName
) {

}