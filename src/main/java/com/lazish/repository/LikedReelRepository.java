package com.lazish.repository;

import com.lazish.entity.LikedReel;
import com.lazish.utils.key.UserReelId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LikedReelRepository extends JpaRepository<LikedReel, UserReelId> {
}
