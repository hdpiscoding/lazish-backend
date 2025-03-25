package com.lazish.entity;

import com.lazish.base.BaseEntity;
import com.lazish.utils.converter.ListMapToJsonConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "Matching")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Matching extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lesson lesson;

    @Convert(converter = ListMapToJsonConverter.class)
    @Column(name = "question", nullable = false)
    private List<Map<String, String>> question;

    @Convert(converter = ListMapToJsonConverter.class)
    @Column(name = "answer", nullable = false)
    private List<Map<String, String>> answer;
}
