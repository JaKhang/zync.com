package com.zync.network.notification.infrastructure.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Component
public class DefaultEmailTemplateSender implements EmailTemplateSender{
    JavaMailSender mailSender;
    TemplateEngine templateEngine;
    MessageSource messageSource;

    @SneakyThrows
    @Override
    public void sendEmail(String to, EmailTemplate template, Context context) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        // Process the template with the given context
        String htmlContent = templateEngine.process(template.getTemplate(), context);

        String subject = messageSource.getMessage("email.verification.subject", new Object[]{}, context.getLocale());
        log.info(htmlContent);
        // Set usernameOrEmail properties
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // Set true for HTML content

        // Send the usernameOrEmail
        mailSender.send(mimeMessage);
    }
}
