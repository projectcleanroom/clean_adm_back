package com.clean.cleanroom.partner.repository;

import com.clean.cleanroom.partner.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PartnerRepository extends JpaRepository<Partner, Long> {

    Optional<Partner> findByEmail(String email);

    // 활성 상태의 파트너만 조회
    @Query("SELECT p FROM Partner p WHERE p.email = :email AND p.isDeleted = false")
    Optional<Partner> findActiveByEmail(@Param("email") String email);

    // 한 달 이상된 소프트 딜리트 파트너 조회
    @Query("SELECT p FROM Partner p WHERE p.isDeleted = true AND p.deletedAt < :dateTime")
    List<Partner> findAllByIsDeletedTrueAndDeletedAtBefore(LocalDateTime dateTime);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByCompanyName(String companyName);

}
