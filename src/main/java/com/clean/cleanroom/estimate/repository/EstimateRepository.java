package com.clean.cleanroom.estimate.repository;

import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.estimate.entity.Estimate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EstimateRepository extends JpaRepository<Estimate, Long> {
    List<Estimate> findByPartnerId(Long id);

    List<Estimate> findByPartnerIdAndStatusIn (Long id, List<StatusType> sendAndFinish );
}
