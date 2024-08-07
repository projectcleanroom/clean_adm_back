package com.clean.cleanroom.partner.repository;

import com.clean.cleanroom.partner.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerRepository extends JpaRepository<Partner, Long> {

    Optional<Partner> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByManagerName(String managerName);

    boolean existsByCompanyName(String companyName);
}
