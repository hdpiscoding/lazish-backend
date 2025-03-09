package com.lazish.repository;

import com.lazish.entity.LikedReel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LikedReelRepository extends JpaRepository<LikedReel, UUID> {
}
