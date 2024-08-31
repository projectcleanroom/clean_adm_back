package com.clean.cleanroom.commission.repository;

import com.clean.cleanroom.commission.dto.CommissionEstimateDetailsDto;
import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CommissionRepository extends JpaRepository<Commission, Long> {

    @Query("SELECT c.id AS commissionId, c.houseType AS houseType, c.cleanType AS cleanType, " +
            "c.desiredDate AS desiredDate, m.nick AS nick, a.address AS address " +
            "FROM Commission c " +
            "JOIN c.members m " +
            "JOIN c.address a " +
            "WHERE c.id = :commissionId")
    Optional<CommissionProjection> findCommissionDetailsById(@Param("commissionId") Long commissionId);


    interface CommissionProjection {
        Long getCommissionId();
        HouseType getHouseType();
        CleanType getCleanType();
        LocalDateTime getDesiredDate();
        String getNick();
        String getAddress();

        // 프로젝션에서 DTO로 변환
        default CommissionEstimateDetailsDto toDto() {
            return new CommissionEstimateDetailsDto(
                    getCommissionId(),
                    getHouseType(),
                    getCleanType(),
                    getDesiredDate(),
                    getNick(),
                    getAddress()
            );
        }
    }
}
