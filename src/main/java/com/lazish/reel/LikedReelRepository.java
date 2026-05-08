package com.lazish.reel;

import com.lazish.common.utils.key.UserReelId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LikedReelRepository extends JpaRepository<LikedReel, UserReelId> {
}
