package com.clean.cleanroom.business.repository;

import com.clean.cleanroom.business.entity.BusinessInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessInfoRepository extends JpaRepository<BusinessInfo, Long> {

    Optional<BusinessInfo> findByIdAndMembersEmail(Long id, String email);
}
