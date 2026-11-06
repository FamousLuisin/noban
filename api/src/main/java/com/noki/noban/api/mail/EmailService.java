package com.noki.noban.api.mail;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service 
public class EmailService {

    private JavaMailSender mailSender;
    private String fromEmail;
    private String logoAttachmentPath;
    private Logger logger = LoggerFactory.getLogger(EmailService.class);

    public EmailService(
        JavaMailSender mailSender, 
        @Value("${spring.mail.username}") String fromEmail,
        @Value("${email.attachment.path.logo}") String logoAttachmentPath
    ) {
        this.mailSender = mailSender;
        this.fromEmail = fromEmail;
        this.logoAttachmentPath = logoAttachmentPath;
    }

    public void sendEmailWelcome(String to, String name) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(
                message,
                true,
                "UTF-8"
            );

            helper.setFrom(this.fromEmail);
            helper.setTo(to);
            helper.setSubject("Welcome to Noban");

            String htmlBody = String.format("""
                <html>
                    <body>
                        <h1>Welcome to Noban, %s!</h1>
                        <p>We are thrilled to have you on board.</p>
                        <img src="cid:logoImage" alt="Noban Logo"
                            style="border-radius: 8px; width: 450px;"/>
                    </body>
                </html>
                """, name);

            helper.setText(htmlBody, true);

            FileSystemResource file =
                new FileSystemResource(new File(logoAttachmentPath));

            helper.addInline("logoImage", file, "image/png");

            mailSender.send(message);

        } catch (MessagingException e) {
            logger.error("Failed to send welcome email to {}: {}", to, e.getMessage());
            throw new RuntimeException("Failed to send welcome email", e);
        }
    }
}
