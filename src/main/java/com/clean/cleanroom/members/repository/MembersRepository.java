package com.clean.cleanroom.members.repository;

import com.clean.cleanroom.members.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MembersRepository extends JpaRepository<Members, Long> {
    Optional<Members> findByEmail(String email);
}
