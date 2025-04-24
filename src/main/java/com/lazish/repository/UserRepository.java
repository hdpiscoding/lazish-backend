package com.lazish.repository;

import com.lazish.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("SELECT u.diamond FROM User u WHERE u.id = :userId")
    long getUserDiamonds(@PathVariable UUID userId);
    @Query("SELECT u FROM User u ORDER BY u.diamond DESC")
    Page<User> findAllUsersRank(Pageable pageable);
}
