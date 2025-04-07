package com.lazish.repository;

import com.lazish.entity.Reel;
import com.lazish.entity.SavedReel;
import com.lazish.utils.key.UserReelId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SavedReelRepository extends JpaRepository<SavedReel, UserReelId> {
    @Query("SELECT sr FROM SavedReel sr JOIN Reel WHERE sr.user.id = :userId")
    List<Reel> getAllSavedReels(UUID userId);
}
