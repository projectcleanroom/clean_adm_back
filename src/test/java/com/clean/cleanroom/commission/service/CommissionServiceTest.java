//package com.clean.cleanroom.commission.service;
//
//import com.clean.cleanroom.commission.dto.*;
//import com.clean.cleanroom.commission.entity.Commission;
//import com.clean.cleanroom.commission.repository.CommissionRepository;
//import com.clean.cleanroom.enums.CleanType;
//import com.clean.cleanroom.enums.HouseType;
//import com.clean.cleanroom.members.entity.Address;
//import com.clean.cleanroom.members.entity.Members;
//import com.clean.cleanroom.members.repository.AddressRepository;
//import com.clean.cleanroom.members.repository.MembersRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class CommissionServiceTest {
//
//    @InjectMocks
//    private CommissionService commissionService;
//
//    @Mock
//    private CommissionRepository commissionRepository;
//
//    @Mock
//    private MembersRepository membersRepository;
//
//    @Mock
//    private AddressRepository addressRepository;
//
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//
//    //청소의뢰 생성 서비스 테스트
//    @Test
//    void testCreateCommission() {
//
//        //given
//        //requestDto 생성
//        CommissionCreateRequestDto requestDto = new CommissionCreateRequestDto(1L, "image.png", 100, HouseType.A, CleanType.일반, 1L, LocalDateTime.now(), "Urgent");
//        //멤버 객체 생성
//        Members member = new Members(1L, "user@example.com", "password", "nick", "1234567890");
//        //주소 생성
//        Address address = new Address(1L, member, "123 Main St");
//        //청소의뢰 객체 생성
//        Commission commission = new Commission(member, address, requestDto);
//
//        when(membersRepository.findById(any(Long.class))).thenReturn(Optional.of(member));
//        when(addressRepository.findById(any(Long.class))).thenReturn(Optional.of(address));
//        when(commissionRepository.save(any(Commission.class))).thenReturn(commission);
//        when(commissionRepository.findByMembersId(any(Long.class))).thenReturn(Optional.of(List.of(commission)));
//
//        //when
//        List<CommissionCreateResponseDto> response = commissionService.createCommission(requestDto);
//
//
//        //then
//        assertNotNull(response);
//        assertEquals(1, response.size());
//        verify(commissionRepository, times(1)).save(any(Commission.class));
//
//    }
//
//    //청소의뢰 수정 서비스 테스트
//    @Test
//    void testUpdateCommission() {
//        //given
//        //requestDto 생성
//        CommissionUpdateRequestDto requestDto = new CommissionUpdateRequestDto("updated_image.png", 200, HouseType.H, CleanType.특수, LocalDateTime.now(), "Updated Significant");
//        //멤버 객체 생성
//        Members member = new Members(1L, "user@example.com", "password", "nick", "1234567890");
//        //주소 객체 생성
//        Address address = new Address(1L, member, "123 Main St");
//        //청소의뢰 객체 생성
//        Commission commission = new Commission(member, address, new CommissionCreateRequestDto(1L, "image.png", 100, HouseType.A, CleanType.일반, 1L, LocalDateTime.now(), "Urgent"));
//
//        when(commissionRepository.findById(any(Long.class))).thenReturn(Optional.of(commission));
//        when(addressRepository.findById(any(Long.class))).thenReturn(Optional.of(address));
//        when(commissionRepository.findByMembersId(any(Long.class))).thenReturn(Optional.of(List.of(commission)));
//
//        //when
//        List<CommissionUpdateResponseDto> response = commissionService.updateCommission(member.getId(), address.getId(), commission.getId(), requestDto);
//
//        //then
//        assertNotNull(response);
//        assertEquals(1, response.size());
//    }
//
//    //청소의뢰 취소 서비스 테스트
//    @Test
//    void testCancelCommission() {
//        //given
//        //멤버 객체 생성
//        Members member = new Members(1L, "user@example.com", "password", "nick", "1234567890");
//        //주소 객체 생성
//        Address address = new Address(1L, member, "123 Main St");
//        //청소의뢰 객체 생성
//        Commission commission = new Commission(member, address, new CommissionCreateRequestDto(1L, "image.png", 100, HouseType.A, CleanType.일반, 1L, LocalDateTime.now(), "Urgent"));
//
//        when(commissionRepository.findByIdAndMembersId(any(Long.class), any(Long.class))).thenReturn(Optional.of(commission));
//        when(commissionRepository.findByMembersId(any(Long.class))).thenReturn(Optional.of(List.of(commission)));
//
//        //when
//        List<CommissionCancelResponseDto> response = commissionService.cancelCommission(1L, 1L);
//
//
//        //then
//        assertNotNull(response);
//        assertEquals(1, response.size());
//        verify(commissionRepository, times(1)).delete(any(Commission.class));
//    }
//
//    //특정 회원의 청소의뢰 내역 전체조회 테스트
//    @Test
//    void testGetMemberCommissions() {
//        //given
//        Members member = new Members(1L, "user@example.com", "password", "nick", "1234567890");
//        Address address = new Address(1L, member, "123 Main St");
//        Commission commission = new Commission(member, address, new CommissionCreateRequestDto(1L, "image.png", 100, HouseType.A, CleanType.일반, 1L, LocalDateTime.now(), "Urgent"));
//
//        when(commissionRepository.findByMembersId(any(Long.class))).thenReturn(Optional.of(List.of(commission)));
//
//        //when
//        List<MyCommissionResponseDto> response = commissionService.getMemberCommissions(1L, MyCommissionResponseDto.class);
//
//        //then
//        assertNotNull(response);
//        assertEquals(1, response.size());
//        verify(commissionRepository, times(1)).findByMembersId(any(Long.class));
//    }
//
//    //전체 청소의뢰 조회 테스트
//    @Test
//    void testGetAllCommissions() {
//        //given
//        Members member = new Members(1L, "user@example.com", "password", "nick", "1234567890");
//        Address address = new Address(1L, member, "123 Main St");
//        Commission commission = new Commission(member, address, new CommissionCreateRequestDto(1L, "image.png", 100, HouseType.A, CleanType.일반, 1L, LocalDateTime.now(), "Urgent"));
//
//        when(commissionRepository.findAll()).thenReturn(List.of(commission));
//
//        //when
//        List<CommissionCreateResponseDto> response = commissionService.getAllCommissions();
//
//        //then
//        assertNotNull(response);
//        assertEquals(1, response.size());
//        verify(commissionRepository, times(1)).findAll();
//    }
//
//}
