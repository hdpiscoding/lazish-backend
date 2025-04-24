package com.lazish.controller;

import com.lazish.base.BaseController;
import com.lazish.service.interfaces.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/media")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MediaController extends BaseController {
    private final CloudinaryService cloudinaryService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile file, @RequestParam("type") String type) throws IOException {
        if (file.isEmpty() || file.getSize() == 0) {
            throw new IllegalArgumentException("File is empty");
        }
        String resourceType = switch (type.toLowerCase()) {
            case "image" -> "image";
            case "video" -> "video";
            case "audio" -> "raw";
            default -> throw new IllegalArgumentException("Invalid type. Must be one of: image, video, audio");
        };
        return buildResponse(cloudinaryService.uploadFile(file, resourceType), HttpStatus.CREATED,"File uploaded successfully");
    }
}
