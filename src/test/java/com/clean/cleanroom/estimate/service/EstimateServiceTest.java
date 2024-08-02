package com.clean.cleanroom.estimate.service;

public class EstimateServiceTest {



//    @Mock
//    private EstimateRepository estimateRepository;
//
//    @InjectMocks
//    private EstimateService estimateService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    private Estimate createEstimate(Long estimateId, Long commissionId) {
//        Commission commission = new Commission();
//        try {
//            Field commissionIdField = Commission.class.getDeclaredField("id");
//            commissionIdField.setAccessible(true);
//            commissionIdField.set(commission, commissionId);
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//        return new Estimate(estimateId, commission, null, 0, null, null);
//    }
//
//
//
//    @Test
//    void givenValidId_whenApproveEstimate_thenReturnSuccessMessage() {
//        // Given
//        Long estimateId = 1L;
//        Estimate estimate = new Estimate(estimateId, null, null, 0, null, null);
//
//        when(estimateRepository.findById(estimateId)).thenReturn(Optional.of(estimate));
//
//        // When
//        String result = estimateService.approveEstimate(estimateId);
//
//        // Then
//        assertEquals("견적이 승인 되었습니다.", result);
//        verify(estimateRepository, times(1)).save(estimate);
//    }
//
//
//    @Test
//    void givenInvalidId_whenApproveEstimate_thenThrowException() {
//        // Given
//        Long estimateId = 1L;
//        when(estimateRepository.findById(estimateId)).thenReturn(Optional.empty());
//
//        // When & Then
//        assertThrows(EntityNotFoundException.class, () -> estimateService.approveEstimate(estimateId));
//    }
//
//
//
//    @Test
//    void givenValidId_whenGetAllEstimates_thenReturnEstimateResponseDtoList() {
//        // Given
//        Long commissionId = 1L;
//        Estimate estimate1 = createEstimate(1L, commissionId);
//        Estimate estimate2 = createEstimate(2L, commissionId);
//
//        List<Estimate> estimates = Arrays.asList(estimate1, estimate2);
//        when(estimateRepository.findByCommissionId(commissionId)).thenReturn(estimates);
//
//        // When
//        List<EstimateResponseDto> result = estimateService.getAllEstimates(commissionId);
//
//        // Then
//        assertEquals(2, result.size());
//        assertEquals(1L, result.get(0).getId());
//        assertEquals(2L, result.get(1).getId());
//    }

}