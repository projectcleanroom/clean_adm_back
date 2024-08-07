package com.clean.cleanroom.partner.repository;

import com.clean.cleanroom.partner.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerRepository extends JpaRepository<Partner, Long> {

    Optional<Partner> findByEmail(String email);

}
