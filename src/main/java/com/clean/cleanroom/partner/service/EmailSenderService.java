//package com.clean.cleanroom.partner.service;
//
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailSenderService {
//
//    private final JavaMailSender mailSender;
//
//    public EmailSenderService(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//    public void sendVerificationEmail(String email, String code) {
//        String subject = "Email Verification Code";
//        String message = "Your verification code is: " + code;
//
//        SimpleMailMessage emailMessage = new SimpleMailMessage();
//        emailMessage.setTo(email);
//        emailMessage.setSubject(subject);
//        emailMessage.setText(message);
//
//        mailSender.send(emailMessage);
//    }
//}
package com.clean.cleanroom.partner.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String email, String code) {
        String fromAddress = "choiyj220220@gmail.com"; // 여기에는 발신자 이메일 주소를 넣으세요
        String subject = "Email Verification Code";
        String message = "Your verification code is: " + code;

        try {
            // MimeMessage 객체 생성
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            // 이메일 속성 설정
            helper.setFrom(fromAddress); // 발신자 이메일 주소 설정
            helper.setTo(email);         // 수신자 이메일 주소 설정
            helper.setSubject(subject);  // 이메일 제목 설정
            helper.setText(message, true); // 이메일 본문 설정 (true는 HTML 사용 가능 여부)

            // 이메일 전송
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace(); // 실제 코드에서는 예외 처리를 적절히 해야 합니다
        }
    }
}