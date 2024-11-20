package com.zync.network.notification.application.commands;

import com.zync.network.core.mediator.NotificationHandler;
import com.zync.network.notification.infrastructure.email.EmailTemplate;
import com.zync.network.notification.infrastructure.email.EmailTemplateSender;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.util.Locale;

import static lombok.AccessLevel.*;

@Component
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RequiredArgsConstructor
@Log4j2
public class SendVerifyEmailCommandHandler implements NotificationHandler<SendVerifyEmailCommand> {
    EmailTemplateSender emailSender;

    @Override
    public void handle(SendVerifyEmailCommand notification) {
        Locale locale = LocaleContextHolder.getLocale();
        Context context = new Context(locale);
        context.setVariable("code",notification.code());
        context.setVariable("age",notification.age());
        emailSender.sendEmail(notification.email(), EmailTemplate.VERIFY, context);
    }
}
