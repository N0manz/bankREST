package com.example.bankcards.mapper;

import com.example.bankcards.dto.CardCreateRequest;
import com.example.bankcards.dto.CardDto;
import com.example.bankcards.model.Card;
import com.example.bankcards.model.User;

public class CardMapper {

    public static CardDto toDto(Card card) {
        CardDto dto = new CardDto();
        dto.setId(card.getId());
        dto.setNumber(card.getNumber());
        dto.setStatus(card.getStatus());
        dto.setExpirationDate(card.getExpirationDate());
        dto.setBalance(card.getBalance());
        return dto;
    }

    public static Card toEntity(CardCreateRequest request, User user) {
        Card card = new Card();
        card.setNumber(request.getNumber());
        try {
            card.setStatus(request.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid card status: " + request.getStatus());
        }
        card.setExpirationDate(request.getExpirationDate());
        card.setBalance(request.getBalance());
        card.setUser(user);
        return card;
    }

}
