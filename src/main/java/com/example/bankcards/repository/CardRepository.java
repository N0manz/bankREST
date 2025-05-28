package com.example.bankcards.repository;

import com.example.bankcards.model.Card;
import com.example.bankcards.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {
    Page<Card> findByUser(User user, Pageable pageable);
    Page<Card> findByUserAndStatus(User user, String status, Pageable pageable);
}
