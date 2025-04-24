package com.lazish.repository;

import com.lazish.entity.Reel;
import com.lazish.entity.SavedReel;
import com.lazish.utils.key.UserReelId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SavedReelRepository extends JpaRepository<SavedReel, UserReelId> {
    @Query("SELECT sr.reel FROM SavedReel sr WHERE sr.user.id = :userId")
    Page<Reel> getAllSavedReels(UUID userId, Pageable pageable);
}
