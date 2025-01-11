package com.zync.network.notification.infrastructure.email;

import lombok.Getter;

@Getter
public enum EmailTemplate {
    VERIFY("verification", "usernameOrEmail.verify.subject"),
    REST_PASSWORD("reset-password", "email.reset-password.subject");

     EmailTemplate(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }

    private final String template;
    private final String subject;
}
