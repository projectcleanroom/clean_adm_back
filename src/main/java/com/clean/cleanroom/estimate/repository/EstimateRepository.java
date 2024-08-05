package com.clean.cleanroom.estimate.repository;

import com.clean.cleanroom.estimate.entity.Estimate;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EstimateRepository extends JpaRepository<Estimate, Long> {
}
