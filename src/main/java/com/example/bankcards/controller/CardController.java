package com.example.bankcards.controller;

import com.example.bankcards.model.Card;
import com.example.bankcards.model.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Autowired
    public CardController(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public Page<Card> getCards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String status
    ) {
        User currentUser = getCurrentUser();
        Pageable pageable = PageRequest.of(page, size, Sort.by("expirationDate").descending());

        if(status != null){
            return cardRepository.findByUserAndStatus(currentUser, status, pageable);
        }else{
            return cardRepository.findByUser(currentUser, pageable);
        }
    }

}
