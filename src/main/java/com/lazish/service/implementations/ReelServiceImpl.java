package com.lazish.service.implementations;

import com.lazish.dto.PaginatedResponseDTO;
import com.lazish.dto.ReelDTO;
import com.lazish.entity.LikedReel;
import com.lazish.entity.Reel;
import com.lazish.entity.SavedReel;
import com.lazish.entity.User;
import com.lazish.mapper.ReelMapper;
import com.lazish.repository.LikedReelRepository;
import com.lazish.repository.ReelRepository;
import com.lazish.repository.SavedReelRepository;
import com.lazish.repository.UserRepository;
import com.lazish.service.interfaces.ReelService;
import com.lazish.utils.key.UserReelId;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final UserRepository userRepository;

    @Override
    public PaginatedResponseDTO<ReelDTO> getAllReels(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page - 1, limit);
        Page<Reel> reels = reelRepository.findAll(pageRequest);
        List<ReelDTO> reelDTOs = reelMapper.toDtoList(reels.getContent());
        return new PaginatedResponseDTO<>(
                reelDTOs,
                reels.getNumber() + 1,
                reels.getTotalPages(),
                reels.getTotalElements(),
                reels.getSize(),
                reels.isLast()
        );
    }

    @Override
    public ReelDTO getReelById(UUID id) {
        return reelMapper.toDto(reelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reel not found!")));
    }

    @Override
    @Transactional
    public void likeReel(UUID userId, UUID reelId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        Reel reel = reelRepository.findById(reelId).orElseThrow(() -> new EntityNotFoundException("Reel not found!"));
        if (!likedReelRepository.existsById(new UserReelId(userId, reelId))) {
            LikedReel likedReel = LikedReel
                    .builder()
                    .id(new UserReelId(userId, reelId))
                    .user(user)
                    .reel(reel)
                    .build();
            likedReelRepository.save(likedReel);
            reel.setLikes(reel.getLikes() + 1);
            reelRepository.save(reel);
        }
    }

    @Override
    @Transactional
    public void unlikeReel(UUID userId, UUID reelId) {
        likedReelRepository.deleteById(new UserReelId(userId, reelId));
        Reel reel = reelRepository.findById(reelId).orElseThrow(() -> new EntityNotFoundException("Reel not found!"));
        if (reel.getLikes() > 0) {
            reel.setLikes(reel.getLikes() - 1);
            reelRepository.save(reel);
        }
    }

    @Override
    @Transactional
    public void saveReel(UUID userId, UUID reelId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        Reel reel = reelRepository.findById(reelId).orElseThrow(() -> new EntityNotFoundException("Reel not found!"));
        if (!savedReelRepository.existsById(new UserReelId(userId, reelId))) {
            SavedReel savedReel = SavedReel
                    .builder()
                    .id(new UserReelId(userId, reelId))
                    .reel(reel)
                    .user(user)
                    .build();
            savedReelRepository.save(savedReel);
        }
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
    public PaginatedResponseDTO<ReelDTO> getMySavedReels(UUID userId, int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page - 1, limit);
        Page<Reel> savedReels = savedReelRepository.getAllSavedReels(userId, pageRequest);
        List<ReelDTO> reelDTOs = reelMapper.toDtoList(savedReels.getContent());
        return new PaginatedResponseDTO<>(
                reelDTOs,
                savedReels.getNumber() + 1,
                savedReels.getTotalPages(),
                savedReels.getTotalElements(),
                savedReels.getSize(),
                savedReels.isLast()
        );
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
