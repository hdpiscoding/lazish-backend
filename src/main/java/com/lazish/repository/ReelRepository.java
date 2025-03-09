package com.lazish.repository;

import com.lazish.entity.Reel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReelRepository extends JpaRepository<Reel, UUID> {
}
