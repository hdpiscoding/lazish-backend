package com.lazish.auth;

import com.lazish.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByTokenHash(String tokenHash);
    List<RefreshToken> findAllByUserAndIsRevokedFalse(User user);
    @Modifying
    @Transactional
    @Query("""
        DELETE FROM RefreshToken rt
        WHERE rt.isRevoked = true
           OR rt.expiresAt < :now
    """)
    int deleteInvalidTokens(LocalDateTime now);
}

