package com.lazish.repository;

import com.lazish.entity.Speaking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpeakingRepository extends JpaRepository<Speaking, UUID> {
}
