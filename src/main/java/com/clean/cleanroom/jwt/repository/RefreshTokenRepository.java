package com.clean.cleanroom.jwt.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.clean.cleanroom.jwt.entity.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    List<RefreshToken> findAllValidTokenByEmail(String userId);
    Optional<RefreshToken> findByToken(String token);

}
