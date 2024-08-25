package com.clean.cleanroom.business.repository;

import com.clean.cleanroom.business.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {

}