package com.lazish.repository;

import com.lazish.entity.SavedReel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SavedReelRepository extends JpaRepository<SavedReel, UUID> {
}
