package com.lazish.controller;

import com.lazish.base.BaseController;
import com.lazish.dto.ReelDTO;
import com.lazish.entity.User;
import com.lazish.repository.UserRepository;
import com.lazish.service.interfaces.JwtService;
import com.lazish.service.interfaces.ReelService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reels")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ReelController extends BaseController {
    private final ReelService reelService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("")
    public ResponseEntity<Object> saveReel(@RequestHeader("Authorization") String authHeader, @RequestBody ReelDTO reelDTO) {
        UUID userId = jwtService.extractUserId(authHeader.substring(7));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return buildResponse(reelService.createReel(user, reelDTO), HttpStatus.CREATED, "Reel created successfully");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReel(@PathVariable UUID id) {
        reelService.deleteReel(id);
        return buildResponse(null , HttpStatus.OK, "Delete reel successfully");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateReel(@PathVariable UUID id, @RequestBody ReelDTO reelDTO) {
        return buildResponse(reelService.updateReel(id, reelDTO), HttpStatus.OK, "Get all reels successfully");
    }

    @GetMapping("")
    public ResponseEntity<Object> getAllReels(@RequestParam int page, @RequestParam int limit) {
        return buildResponse(reelService.getAllReels(page, limit), HttpStatus.OK, "Get all reels successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getReelById(@PathVariable UUID id) {
        return buildResponse(reelService.getReelById(id), HttpStatus.OK, "Get reel successfully");
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/me/saved")
    public ResponseEntity<Object> getMySavedReels(@RequestHeader("Authorization") String authHeader, @RequestParam int page, @RequestParam int limit) {
        UUID userId = jwtService.extractUserId(authHeader.substring(7));
        return buildResponse(reelService.getMySavedReels(userId, page, limit), HttpStatus.OK, "Get my saved reels successfully");
    }

    @PostMapping("/{id}/likes")
    public ResponseEntity<Object> likeReel(@RequestHeader("Authorization") String authHeader, @PathVariable UUID id) {
        UUID userId = jwtService.extractUserId(authHeader.substring(7));
        reelService.likeReel(userId, id);
        return buildResponse(null, HttpStatus.OK, "Like reel successfully");
    }

    @DeleteMapping("/{id}/likes")
    public ResponseEntity<Object> unlikeReel(@RequestHeader("Authorization") String authHeader, @PathVariable UUID id) {
        UUID userId = jwtService.extractUserId(authHeader.substring(7));
        reelService.unlikeReel(userId, id);
        return buildResponse(null, HttpStatus.OK, "Unlike reel successfully");
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{id}/save")
    public ResponseEntity<Object> saveReel(@RequestHeader("Authorization") String authHeader, @PathVariable UUID id) {
        UUID userId = jwtService.extractUserId(authHeader.substring(7));
        reelService.saveReel(userId, id);
        return buildResponse(null, HttpStatus.OK, "Save reel successfully");
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{id}/save")
    public ResponseEntity<Object> unsaveReel(@RequestHeader("Authorization") String authHeader, @PathVariable UUID id) {
        UUID userId = jwtService.extractUserId(authHeader.substring(7));
        reelService.unsaveReel(userId, id);
        return buildResponse(null, HttpStatus.OK, "Unsave reel successfully");
    }
}
