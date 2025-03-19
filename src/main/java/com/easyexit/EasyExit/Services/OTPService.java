package com.easyexit.EasyExit.Services;

import com.easyexit.EasyExit.Entity.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OTPService {
    @Autowired
    private JavaMailSender mailSender;


    public void sendOtpByEmail(String toEmail, Form form, String otp) {
        if (mailSender == null) {
            throw new IllegalStateException("Mail sender is not configured properly.");
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("EasyExit - Outpass Approved");
        String emailBody = String.format(
                "Dear Student,\n\n" +
                        "Your outpass request for the date %s has been approved by your Warden.\n\n" +
                        "Use the OTP below to verify your outpass at the gate:\n\n" +
                        "OTP: %s\n\n" +
                        "Please do not share this OTP with anyone.\n\n" +
                        "Best Regards,\nEasyExit Team",
                form.getDate(), otp
        );
        message.setText(emailBody);
        mailSender.send(message);
    }

    public void sendReasonByEmail(String toEmail, Form form, String reason) {
        if (mailSender == null) {
            throw new IllegalStateException("Mail sender is not configured properly.");
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("EasyExit - Outpass Rejected");
        String emailBody = String.format(
                "Dear Student,\n\n" +
                        "Your outpass request for the date %s has been rejected by your Warden.\n\n" +
                        "Below is the reason for rejection of your outpass:\n\n" +
                        "Reason: %s\n\n" +
                        "Best Regards,\nEasyExit Team",
                form.getDate(), reason
        );
        message.setText(emailBody);
        mailSender.send(message);
    }
}
