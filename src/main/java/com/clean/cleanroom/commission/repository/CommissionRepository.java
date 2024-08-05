package com.clean.cleanroom.commission.repository;

import com.clean.cleanroom.commission.entity.Commission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommissionRepository extends JpaRepository<Commission, Long> {
}
