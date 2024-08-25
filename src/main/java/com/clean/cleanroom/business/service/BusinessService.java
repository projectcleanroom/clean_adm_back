package com.clean.cleanroom.business.service;


import com.clean.cleanroom.business.dto.BusinessValidateRequestDto;
import com.clean.cleanroom.business.dto.BusinessValidateResponseDto;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BusinessService {


    private final RestTemplate restTemplate;
    private final String apiUrl = "https://api.odcloud.kr/api/nts-businessman/v1/validate";

    public BusinessService (RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    // 사업자 인증
    public BusinessValidateResponseDto validatebusiness(BusinessValidateRequestDto businessValidateRequestDto) {

        // URL 설정
        String urlWithKey = apiUrl + "?serviceKey=" + businessValidateRequestDto.getServiceKey();
        System.out.println("Request URL: " + urlWithKey);

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Infuser " + businessValidateRequestDto.getServiceKey());

        // 요청 객체 생성
        HttpEntity<BusinessValidateRequestDto> entity = new HttpEntity<>(businessValidateRequestDto, headers);
        System.out.println("Request Headers: " + headers.toString());

        // rest template으로 변환
        ResponseEntity<BusinessValidateResponseDto> businessValidateResponseDto = restTemplate.exchange(
                urlWithKey,
                HttpMethod.POST,
                entity,
                BusinessValidateResponseDto.class
        );
        System.out.println("Request Body: " + businessValidateRequestDto.toString());

        BusinessValidateResponseDto responseBody = businessValidateResponseDto.getBody();
        System.out.println("Response Status Code: " + businessValidateResponseDto.getStatusCode().toString());


        HttpStatus statusCode = HttpStatus.valueOf(businessValidateResponseDto.getStatusCode().value());
        System.out.println("Response Body: " + businessValidateResponseDto.getBody());

        // 요청 오류
        if (statusCode.is4xxClientError()) {
            if (statusCode == HttpStatus.BAD_REQUEST) {
                throw new CustomException(ErrorMsg.BAD_JSON_REQUEST); //JSON 포맷에 적합하지 않은 요청
            } else if (statusCode == HttpStatus.NOT_FOUND) {
                throw new CustomException(ErrorMsg.REQUEST_DATA_MALFORMED); // 필수 요청 파라미터 누락
            } else if (statusCode == HttpStatus.PAYLOAD_TOO_LARGE) {
                throw new CustomException(ErrorMsg.TOO_LARGE_REQUEST); // 요청 사업자 정보 100개 초과
            } else {
                throw new CustomException(ErrorMsg.BAD_REQUEST);
            }
        } else if (statusCode.is5xxServerError()) {
            throw new CustomException(ErrorMsg.INTERNAL_SERVER_ERROR); // 인터넷 서버 오류
        }

        // 응답 본문에 따른 예외 처리
        if (responseBody == null || responseBody.getData().isEmpty()) {
            throw new CustomException(ErrorMsg.BUSINESS_INFO_NOT_FOUND); // 찾을 수 없는 사업자
        }

        BusinessValidateResponseDto.BusinessResponse firstBusiness = responseBody.getData().get(0);
        if (!"01".equals(firstBusiness.getValid())) {
            throw new CustomException(ErrorMsg.BAD_REQUEST); // 잘못된 요청
        }
        return responseBody;
    }

}
