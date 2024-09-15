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

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.partner.entity.VerificationCode;
import com.clean.cleanroom.partner.repository.VerificationCodeRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.Optional;

@Service
public class EmailSenderService {

    private final JavaMailSender mailSender;
    private final VerificationCodeRepository verificationCodeRepository;

    public EmailSenderService(JavaMailSender mailSender, VerificationCodeRepository verificationCodeRepository) {
        this.mailSender = mailSender;
        this.verificationCodeRepository = verificationCodeRepository;
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


    // 포스트맨 테스트용 인증코드 조회 API
//    public String getEmailCode(String email) {
//        // Optional로 반환된 값을 처리
//        Optional<VerificationCode> optionalVerificationCode = verificationCodeRepository.findByEmail(email);
//
//        // 값이 존재하면 인증 코드를 반환하고, 값이 없으면 적절한 예외 처리나 기본값 반환
//        return optionalVerificationCode
//                .map(VerificationCode::getCode)  // VerificationCode 객체가 있으면 getCode() 호출
//                .orElseThrow(() -> new IllegalArgumentException("인증 코드를 찾을 수 없습니다."));  // 값이 없을 경우 예외 처리
//    }
    public String getEmailCode(String email) {
        // 데이터베이스에서 이메일로 인증 코드를 조회
        Optional<VerificationCode> optionalVerificationCode = verificationCodeRepository.findByEmail(email);

        // 인증 코드가 존재하는지 확인하고, 없으면 예외 발생
        VerificationCode verificationCode = optionalVerificationCode
                .orElseThrow(() -> new IllegalArgumentException("인증 코드를 찾을 수 없습니다."));

        return verificationCode.getCode();  // 인증 코드 반환
    }
}