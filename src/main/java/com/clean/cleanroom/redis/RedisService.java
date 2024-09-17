package com.clean.cleanroom.redis;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;  // ObjectMapper 주입

    // 인증 코드 저장
    public void setCode(String email, String code) {
        ValueOperations<String, Object> valOperations = redisTemplate.opsForValue();
        valOperations.set(email, code, 300, TimeUnit.SECONDS);
    }

    // 인증 코드 조회
    public String getCode(String email) {
        ValueOperations<String, Object> valOperations = redisTemplate.opsForValue();
        Object code = valOperations.get(email);
        if (code == null) {
            throw new CustomException(ErrorMsg.INVALID_VERIFICATION_CODE);
        }
        return code.toString();
    }

    // 객체 저장
    public void setObject(String key, Object object, long timeout, TimeUnit timeUnit) {
        ValueOperations<String, Object> valOperations = redisTemplate.opsForValue();
        valOperations.set(key, object, timeout, timeUnit);
    }

    // 객체 조회 (수정된 부분)
    public <T> T getObject(String key, Class<T> clazz) {
        ValueOperations<String, Object> valOperations = redisTemplate.opsForValue();
        Object object = valOperations.get(key);
        if (object == null) {
            return null;  // 데이터가 없으면 null 반환
        }
        // ObjectMapper로 변환
        return objectMapper.convertValue(object, clazz);
    }

    // 인증 완료 플래그 설정
    public void setVerifiedFlag(String email) {
        ValueOperations<String, Object> valOperations = redisTemplate.opsForValue();
        // 인증이 완료되면 인증 플래그를 Redis에 영구적으로 저장
        valOperations.set(email + "_verified", "true", 10, TimeUnit.MINUTES); // 만료 시간을 원하면 설정 가능
    }

    // 인증 완료 여부 확인
    public boolean isVerified(String email) {
        ValueOperations<String, Object> valOperations = redisTemplate.opsForValue();
        Object verifiedFlag = valOperations.get(email + "_verified");
        return verifiedFlag != null && verifiedFlag.equals("true");
    }

    public void deleteObject(String key) {
        redisTemplate.delete(key);
    }
}
