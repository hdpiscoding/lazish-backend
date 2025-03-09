package com.lazish.repository;

import com.lazish.entity.Listening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ListeningRepository extends JpaRepository<Listening, UUID> {

}
