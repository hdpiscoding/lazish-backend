package com.lazish.repository;

import com.lazish.entity.Translate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TranslateRepository extends JpaRepository<Translate, UUID> {
}
