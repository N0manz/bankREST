package com.example.bankcards.controller;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.model.Card;
import com.example.bankcards.model.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static com.example.bankcards.util.SecurityUtil.getCurrentUser;

@RestController
@RequestMapping("/cards")
public class CardController {
    private final CardRepository cardRepository;
    @Autowired
    public CardController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public Page<CardDto> getAllCards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String status
    ) {
        User currentUser = SecurityUtil.getCurrentUser();
        Pageable pageable = PageRequest.of(page, size);

        Page<Card> cards;

        if (status != null && !status.isBlank()) {
            cards = cardRepository.findByUserAndStatus(currentUser, status, pageable);
        } else {
            cards = cardRepository.findByUser(currentUser, pageable);
        }

        return cards.map(CardMapper::toDto);
    }

}
