package com.lazish.reel;

import com.lazish.common.dto.PaginatedResponseDTO;
import com.lazish.user.User;

import java.util.UUID;

public interface ReelService {
    PaginatedResponseDTO<ReelDTO> getAllReels(int page, int limit);
    ReelDTO getReelById(UUID id);
    void likeReel(UUID userId, UUID reelId);
    void unlikeReel(UUID userId, UUID reelId);
    void saveReel(UUID userId, UUID reelId);
    void unsaveReel(UUID userId, UUID reelId);
    void deleteReel(UUID id);
    PaginatedResponseDTO<ReelDTO> getMySavedReels(UUID userId, int page, int limit);
    ReelDTO createReel(User user, ReelDTO reelDTO);
    ReelDTO updateReel(UUID id, ReelDTO reelDTO);
}
