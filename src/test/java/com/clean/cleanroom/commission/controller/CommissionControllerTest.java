//package com.clean.cleanroom.commission.controller;
//
//import com.clean.cleanroom.commission.dto.*;
//import com.clean.cleanroom.commission.entity.Commission;
//import com.clean.cleanroom.commission.service.CommissionService;
//import com.clean.cleanroom.enums.CleanType;
//import com.clean.cleanroom.enums.HouseType;
//import com.clean.cleanroom.members.entity.Address;
//import com.clean.cleanroom.members.entity.Members;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class CommissionControllerTest {
//
//
//
//    @InjectMocks
//    private CommissionController commissionController;
//
//    @Mock
//    private CommissionService commissionService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    //청소의뢰 생성 테스트
//    @Test
//    void testCreateCommission() {
//        //given
//        //멤버 객체 생성
//        Members member = new Members(1L, "user@example.com", "password", "nick", "1234567890");
//        //주소 객체 생성
//        Address address = new Address(1L, member, "123 Main St");
//        //청소의뢰 객체 생성
//        Commission commission = new Commission(member, address, new CommissionCreateRequestDto(1L, "image.png", 100, HouseType.A, CleanType.일반, 1L, LocalDateTime.now(), "Urgent"));
//
//        //위의 정보들을 활용한 requestDto 생성
//        CommissionCreateRequestDto requestDto = new CommissionCreateRequestDto(1L, "image.png", 100, HouseType.A, CleanType.일반, 1L, LocalDateTime.now(), "Urgent");
//        //반환 DTO 리스트 생성
//        List<CommissionCreateResponseDto> responseDtoList = List.of(new CommissionCreateResponseDto(commission));
//
//        when(commissionService.createCommission(any(CommissionCreateRequestDto.class))).thenReturn(responseDtoList);
//
//        //when
//        ResponseEntity<List<CommissionCreateResponseDto>> response = commissionController.createCommission(requestDto);
//
//        //then
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(1, response.getBody().size());
//        verify(commissionService, times(1)).createCommission(any(CommissionCreateRequestDto.class));
//    }
//
//    //청소의뢰 수정 테스트
//    @Test
//    void testUpdateCommission() {
//        //given
//        Members member = new Members(1L, "user@example.com", "password", "nick", "1234567890");
//        Address address = new Address(1L, member, "123 Main St");
//        Commission commission = new Commission(member, address, new CommissionCreateRequestDto(1L, "image.png", 100, HouseType.A, CleanType.일반, 1L, LocalDateTime.now(), "Urgent"));
//
//        //위의 정보들을 활용한 requestDto 생성
////        CommissionUpdateRequestDto requestDto = new CommissionUpdateRequestDto(1L, 1L, 1L, "updated_image.png", 200, HouseType.H, CleanType.특수, LocalDateTime.now(), "Updated Significant");
//        //반환 DTO 리스트 생성
//        List<CommissionUpdateResponseDto> responseDtoList = List.of(new CommissionUpdateResponseDto(commission));
//
////        when(commissionService.updateCommission(any(CommissionUpdateRequestDto.class))).thenReturn(responseDtoList);
//
//        //when
////        ResponseEntity<List<CommissionUpdateResponseDto>> response = commissionController.updateCommission(requestDto);
//
//        //then
////        assertNotNull(response);
////        assertEquals(HttpStatus.OK, response.getStatusCode());
////        assertEquals(1, response.getBody().size());
////        verify(commissionService, times(1)).updateCommission(any(CommissionUpdateRequestDto.class));
//    }
//
//    //청소의뢰 취소 테스트
//    @Test
//    void testCancelCommission() {
//        //given
//        Members member = new Members(1L, "user@example.com", "password", "nick", "1234567890");
//        Address address = new Address(1L, member, "123 Main St");
//        Commission commission = new Commission(member, address, new CommissionCreateRequestDto(1L, "image.png", 100, HouseType.A, CleanType.일반, 1L, LocalDateTime.now(), "Urgent"));
//
//        //반환 DTO 리스트 생성
//        List<CommissionCancelResponseDto> responseDtoList = List.of(new CommissionCancelResponseDto(commission));
//
//        when(commissionService.cancelCommission(any(Long.class), any(Long.class))).thenReturn(responseDtoList);
//
//        //when
//        ResponseEntity<List<CommissionCancelResponseDto>> response = commissionController.cancelCommission(1L, 1L);
//
//        //then
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(1, response.getBody().size());
//        verify(commissionService, times(1)).cancelCommission(any(Long.class), any(Long.class));
//    }
//
//    //내 청소의뢰내역 조회
//    @Test
//    void testGetMyCommissions() {
//        //given
//        Members member = new Members(1L, "user@example.com", "password", "nick", "1234567890");
//        Address address = new Address(1L, member, "123 Main St");
//        Commission commission = new Commission(member, address, new CommissionCreateRequestDto(1L, "image.png", 100, HouseType.A, CleanType.일반, 1L, LocalDateTime.now(), "Urgent"));
//
//        //반환 DTO 리스트 생성
//        List<MyCommissionResponseDto> responseDtoList = List.of(new MyCommissionResponseDto(commission));
//
//        when(commissionService.getMemberCommissions(any(Long.class), eq(MyCommissionResponseDto.class))).thenReturn(responseDtoList);
//
//        //when
//        ResponseEntity<List<MyCommissionResponseDto>> response = commissionController.getMyCommission(1L);
//
//        //then
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(1, response.getBody().size());
//        verify(commissionService, times(1)).getMemberCommissions(any(Long.class), eq(MyCommissionResponseDto.class));
//    }
//}