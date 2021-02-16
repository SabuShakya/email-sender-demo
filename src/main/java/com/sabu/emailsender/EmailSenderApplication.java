package com.sabu.emailsender;

import com.sabu.emailsender.property.Mail;
import com.sabu.emailsender.service.EmailService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class EmailSenderApplication implements CommandLineRunner {

    private final EmailService emailService;
    static String filepath = System.getProperty("user.dir") + "/src/main/resources/SampleExcel.xlsx";

    public EmailSenderApplication(EmailService emailService) {
        this.emailService = emailService;
    }

    public static void main(String[] args) {
        SpringApplication.run(EmailSenderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        emailService.sendSimpleMessage(
//                "sabushakya1@gmail.com",
//                "Hello Test",
//                "Hi Simple message");
//        emailService.sendMessageWithAttachment(
//                "sabushakya1@gmail.com",
//                "Hello Test",
//                "Hi Simple message",
//                filepath
//        );
        Mail mail = new Mail();
        mail.setFrom("etest4367@gmail.com");
        mail.setTo("sabushakya1@gmail.com");
        mail.setSubject("Sending Email with Freemarker HTML Template Example");

        Map model = new HashMap();
        model.put("name", "test.com");
        model.put("location", "Nepal");
        model.put("signature", "https://memorynotfound.com");

        emailService.sendSimpleMessage(mail,model);
    }
}
