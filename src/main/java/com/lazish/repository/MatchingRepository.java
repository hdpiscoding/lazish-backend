package com.lazish.repository;

import com.lazish.entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, UUID> {
}
