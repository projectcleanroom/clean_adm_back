//package com.clean.cleanroom.members.controller;
//
//import com.clean.cleanroom.members.dto.MembersLoginRequestDto;
//import com.clean.cleanroom.members.dto.MembersLoginResponseDto;
//import com.clean.cleanroom.members.dto.MembersLogoutResponseDto;
//import com.clean.cleanroom.members.service.MembersLoginService;
//import com.clean.cleanroom.util.JwtUtil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.*;
//
//class MembersLoginAndLogoutControllerTest {
//
//    @Mock
//    private MembersLoginService membersService;
//
//    @Mock
//    private JwtUtil jwtUtil;
//
//    @InjectMocks
//    private MembersLoginAndLogoutController membersLoginAndLogoutController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

//    @Test
//    void testLogin_Success() {
//        // given
//        MembersLoginRequestDto requestDto = mock(MembersLoginRequestDto.class);
//        MembersLoginResponseDto responseDto = mock(MembersLoginResponseDto.class);
//
//        given(requestDto.getEmail()).willReturn("test@example.com");
//        given(membersService.login(requestDto)).willReturn(responseDto);
//        given(jwtUtil.generateToken(anyString())).willReturn("fake-jwt-token");
//        given(responseDto.getEmail()).willReturn("test@example.com");
//
//        // when
//        ResponseEntity<MembersLoginResponseDto> responseEntity = membersLoginAndLogoutController.login(requestDto);
//
//        // then
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(responseDto, responseEntity.getBody());
//        assertEquals("Bearer fake-jwt-token", responseEntity.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
//
//        verify(membersService, times(1)).login(requestDto);
//        verify(jwtUtil, times(1)).generateToken(responseDto.getEmail());
//    }
//
//    @Test
//    void testLogin_Failure() {
//        // given
//        MembersLoginRequestDto requestDto = mock(MembersLoginRequestDto.class);
//
//        given(requestDto.getEmail()).willReturn("test@example.com");
//        given(membersService.login(requestDto)).willThrow(new RuntimeException("Login failed"));
//
//        // when
//        ResponseEntity<MembersLoginResponseDto> responseEntity = membersLoginAndLogoutController.login(requestDto);
//
//        // then
//        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
//        assertEquals("Login failed", responseEntity.getBody().getMessage());
//
//        verify(membersService, times(1)).login(requestDto);
//        verify(jwtUtil, never()).generateToken(anyString());
//    }
//
//    @Test
//    void testLogout_Success() {
//        // given
//        String token = "Bearer fake-jwt-token";
//
//        given(jwtUtil.validateToken("fake-jwt-token")).willReturn(true);
//
//        // when
//        ResponseEntity<MembersLogoutResponseDto> responseEntity = membersLoginAndLogoutController.logout(token);
//
//        // then
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals("로그아웃 되었습니다.", responseEntity.getBody().getMessage());
//
//        verify(jwtUtil, times(1)).validateToken("fake-jwt-token");
//    }
//
//    @Test
//    void testLogout_Failure() {
//        // given
//        String token = "Bearer fake-jwt-token";
//
//        given(jwtUtil.validateToken("fake-jwt-token")).willReturn(false);
//
//        // when
//        ResponseEntity<MembersLogoutResponseDto> responseEntity = membersLoginAndLogoutController.logout(token);
//
//        // then
//        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
//        assertEquals("로그인 후 가능합니다.", responseEntity.getBody().getMessage());
//
//        verify(jwtUtil, times(1)).validateToken("fake-jwt-token");
//    }
//
//    @Test
//    void testLogout_MissingToken() {
//        // given
//        String token = null;
//
//        // when
//        ResponseEntity<MembersLogoutResponseDto> responseEntity = membersLoginAndLogoutController.logout(token);
//
//        // then
//        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
//        assertEquals("로그인 후 가능합니다.", responseEntity.getBody().getMessage());
//
//        verify(jwtUtil, never()).validateToken(anyString());
//    }
//}
