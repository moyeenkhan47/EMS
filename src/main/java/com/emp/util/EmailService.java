package com.emp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendMimeMessage(String email, String subject, String text, byte[] pdfData, String pdfFileName) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(text, true); // true to send HTML

        // Attach PDF
        if (pdfData != null) {
            ByteArrayDataSource dataSource = new ByteArrayDataSource(pdfData, "application/pdf");
            helper.addAttachment(pdfFileName, dataSource);
        }

        emailSender.send(message);
    }

	
}
