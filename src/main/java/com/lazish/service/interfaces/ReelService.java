package com.lazish.service.interfaces;

import com.lazish.dto.ReelDTO;
import com.lazish.entity.User;

import java.util.List;
import java.util.UUID;

public interface ReelService {
    List<ReelDTO> getAllReels();
    ReelDTO getReelById(UUID id);
    void likeReel(UUID userId, UUID reelId);
    void unlikeReel(UUID userId, UUID reelId);
    void saveReel(UUID userId, UUID reelId);
    void unsaveReel(UUID userId, UUID reelId);
    void deleteReel(UUID id);
    List<ReelDTO> getMySavedReels(UUID userId);
    ReelDTO createReel(User user, ReelDTO reelDTO);
    ReelDTO updateReel(UUID id, ReelDTO reelDTO);
}
