package com.clean.cleanroom.members.service;

import com.clean.cleanroom.members.dto.MembersLoginRequestDto;
import com.clean.cleanroom.members.dto.MembersLoginResponseDto;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MembersLoginServiceTest {

    @Mock
    private MembersRepository membersRepository;

    @InjectMocks
    private MembersLoginService membersService;

//    @Test
//    void testLoginSuccess() {
//        // given
//        MembersLoginRequestDto requestDto = mock(MembersLoginRequestDto.class);
//        when(requestDto.getEmail()).thenReturn("test@example.com");
//        when(requestDto.getPassword()).thenReturn("password");
//
//        Members member = mock(Members.class);
//        when(member.getEmail()).thenReturn("test@example.com");
//        when(member.getPassword()).thenReturn("password");
//        when(member.getNick()).thenReturn("testNick");
//
//        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
//
//        // when
//        MembersLoginResponseDto responseDto = membersService.login(requestDto);
//
//        // then
//        assertNotNull(responseDto);
//        assertEquals("test@example.com", responseDto.getEmail());
//        assertEquals("testNick", responseDto.getNick());
//    }

    @Test
    void testLoginFailure() {
        // given
        MembersLoginRequestDto requestDto = mock(MembersLoginRequestDto.class);
        when(requestDto.getEmail()).thenReturn("test@example.com");
        when(requestDto.getPassword()).thenReturn("wrongpassword");

        Members member = mock(Members.class);
        when(member.getPassword()).thenReturn("password");

        // 이메일로 멤버를 찾지만, 비밀번호가 일치하지 않으므로 로그인 실패
        when(membersRepository.findByEmail("test@example.com")).thenReturn(Optional.of(member));

        // when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> membersService.login(requestDto));
        assertEquals("잘못된 비밀번호입니다.", exception.getMessage());
    }


    @Test
    void testLoginUserNotFound() {
        // given
        MembersLoginRequestDto requestDto = mock(MembersLoginRequestDto.class);
        when(requestDto.getEmail()).thenReturn("test@example.com");

        when(membersRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        // when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> membersService.login(requestDto));
        assertEquals("존재하지 않는 아이디입니다.", exception.getMessage());
    }
}
