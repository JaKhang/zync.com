package com.zync.network.notification.infrastructure.email;

import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;

public interface EmailTemplateSender {
    void sendEmail(String to, EmailTemplate template, Context context);

}
