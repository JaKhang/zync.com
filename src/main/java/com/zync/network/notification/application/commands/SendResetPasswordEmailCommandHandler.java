package com.zync.network.notification.application.commands;

import com.zync.network.core.exceptions.MethodNotImplementException;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.NotificationHandler;
import com.zync.network.notification.infrastructure.email.EmailTemplate;
import com.zync.network.notification.infrastructure.email.EmailTemplateSender;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.i18n.LocaleContextHolder;
import org.thymeleaf.context.Context;

import java.util.Locale;

@Handler
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SendResetPasswordEmailCommandHandler implements NotificationHandler<SendResetPasswordEmailCommand> {
    EmailTemplateSender emailSender;


    @Override
    public void handle(SendResetPasswordEmailCommand notification) {

        Locale locale = LocaleContextHolder.getLocale();
        Context context = new Context(locale);
        context.setVariable("code",notification.code());
        context.setVariable("age",notification.age());
        emailSender.sendEmail(notification.email(), EmailTemplate.REST_PASSWORD, context);
    }
}
