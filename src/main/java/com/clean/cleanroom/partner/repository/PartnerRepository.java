package com.clean.cleanroom.partner.repository;

import com.clean.cleanroom.partner.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PartnerRepository extends JpaRepository<Partner, Long> {

    Optional<Partner> findByEmail(String email);

    // 소프트 딜리트된 파트너도 포함한 조회 메서드
    @Query("SELECT p FROM Partner p WHERE p.email = :email")
    Optional<Partner> findByEmailIncludingDeleted(@Param("email") String email);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByCompanyName(String companyName);

}
