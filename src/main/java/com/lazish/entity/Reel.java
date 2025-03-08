package com.lazish.entity;

import com.lazish.base.BaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Reel")
public class Reel extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "caption", nullable = false)
    private String caption;

    @Column(name = "video", nullable = false)
    private String video;

    @Column(name = "likes", nullable = false)
    private long likes = 0;

    @OneToMany(mappedBy = "reel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<LikedReel> likedReels = new HashSet<>();

    @OneToMany(mappedBy = "reel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<SavedReel> savedReels = new HashSet<>();
}
