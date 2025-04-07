package com.lazish.service.implementations;

import com.lazish.dto.ReelDTO;
import com.lazish.entity.LikedReel;
import com.lazish.entity.Reel;
import com.lazish.entity.SavedReel;
import com.lazish.entity.User;
import com.lazish.mapper.ReelMapper;
import com.lazish.repository.LikedReelRepository;
import com.lazish.repository.ReelRepository;
import com.lazish.repository.SavedReelRepository;
import com.lazish.service.interfaces.ReelService;
import com.lazish.utils.key.UserReelId;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ReelServiceImpl implements ReelService {
    private final ReelRepository reelRepository;
    private final ReelMapper reelMapper;
    private final SavedReelRepository savedReelRepository;
    private final LikedReelRepository likedReelRepository;
    @Override
    public List<ReelDTO> getAllReels() {
        return reelMapper.toDtoList(reelRepository.findAll());
    }

    @Override
    public ReelDTO getReelById(UUID id) {
        return reelMapper.toDto(reelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reel not found!")));
    }

    @Override
    @Transactional
    public void likeReel(UUID userId, UUID reelId) {
        LikedReel likedReel = LikedReel
                .builder()
                .id(new UserReelId(userId, reelId))
                .build();
        likedReelRepository.save(likedReel);
    }

    @Override
    @Transactional
    public void unlikeReel(UUID userId, UUID reelId) {
        likedReelRepository.deleteById(new UserReelId(userId, reelId));
    }

    @Override
    @Transactional
    public void saveReel(UUID userId, UUID reelId) {
        SavedReel savedReel = SavedReel
                .builder()
                .id(new UserReelId(userId, reelId))
                .build();
        savedReelRepository.save(savedReel);
    }

    @Override
    @Transactional
    public void unsaveReel(UUID userId, UUID reelId) {
        savedReelRepository.deleteById(new UserReelId(userId, reelId));
    }

    @Override
    @Transactional
    public void deleteReel(UUID id) {
        Reel reel = reelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reel not found!"));
        reelRepository.delete(reel);
    }

    @Override
    public List<ReelDTO> getMySavedReels(UUID userId) {
        return reelMapper.toDtoList(savedReelRepository.getAllSavedReels(userId));
    }

    @Override
    @Transactional
    public ReelDTO createReel(User user, ReelDTO reelDTO) {
        Reel reel = Reel
                .builder()
                .caption(reelDTO.getCaption())
                .video(reelDTO.getVideo())
                .user(user)
                .build();
        return reelMapper.toDto(reelRepository.save(reel));
    }

    @Override
    @Transactional
    public ReelDTO updateReel(UUID id, ReelDTO reelDTO) {
        Reel reel = reelRepository.findById(id).orElseThrow(() -> new RuntimeException("Reel not found"));
        if (reelDTO.getCaption() != null) {
            reel.setCaption(reelDTO.getCaption());
        }
        if (reelDTO.getVideo() != null) {
            reel.setVideo(reelDTO.getVideo());
        }
        return reelMapper.toDto(reelRepository.save(reel));
    }
}
