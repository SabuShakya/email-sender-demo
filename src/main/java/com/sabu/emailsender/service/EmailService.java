package com.sabu.emailsender.service;

import com.sabu.emailsender.property.EmailProperties;
import com.sabu.emailsender.property.Mail;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class EmailService {

    private final JavaMailSender emailSender;
    private final EmailProperties emailProperties;
    // For freemarker
    private final Configuration freemarkerConfig;

    public EmailService(JavaMailSender emailSender,
                        EmailProperties emailProperties,
                       Configuration freemarkerConfig) {
        this.emailSender = emailSender;
        this.emailProperties = emailProperties;
        this.freemarkerConfig = freemarkerConfig;
    }

    public void sendSimpleMessage(
            String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailProperties.getUsername());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setText(text);
        emailSender.send(message);

    }

    public void sendMessageWithAttachment(
            String to, String subject, String text, String pathToAttachment) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(emailProperties.getUsername());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        FileSystemResource file
                = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment("Invoice", file);
        emailSender.send(message);
    }

    public void sendSimpleMessage(Mail mail,Map model) throws MessagingException, IOException, TemplateException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.addAttachment("logo.png", new ClassPathResource("apple.jpeg"));

        Template t = freemarkerConfig.getTemplate("email-template.ftl");

        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());

        emailSender.send(message);
    }

}
